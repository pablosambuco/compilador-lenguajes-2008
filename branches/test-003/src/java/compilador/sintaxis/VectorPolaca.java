package compilador.sintaxis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import compilador.beans.TablaDeSimbolos;

public class VectorPolaca {

	private java.util.Vector<EntradaVectorPolaca> vector;
	
	//Constantes para condiciones
	public static String AND = "_AND";
	public static String OR = "_OR";
	public static String NEGACION = "_NEGACION";
	public static String SIMPLE = "_SIMPLE";
	
	//Constantes para expresiones
	public static String DISTINTO = "JNE";
	public static String IGUAL = "JE";
	public static String MENOR = "JB";
	public static String MAYOR = "JA";
	public static String MENOR_O_IGUAL = "JBE";
	public static String MAYOR_O_IGUAL = "JAE";
	public static String SIEMPRE = "JMP";

	private static VectorPolaca instance;
	public static VectorPolaca getInstance() {
		if (instance == null)
			instance = new VectorPolaca();
		return instance;
	}
	
	private VectorPolaca() {
		vector = new Vector<EntradaVectorPolaca>();
	}
	
	public void agregar(EntradaVectorPolaca entrada) {
		vector.add(entrada); //agrega al final del último elemento del vector
	}
	
	public void agregar(EntradaVectorPolaca entrada, int posicion) {
		/* Si se esta intentando insertar un elemento dejando huecos vacíos en el vector,
		 * el mismo arrojará un error; por eso debemos llenar los espacios intermedios con "null"
		 * (incluso si se lo quiere insertar en el primer hueco vacìo, ese también debe contener "null"
		 * ya que el metodo "setElement" solo inserta en un casillero donde ya había algo */
		 
		if(posicion >= vector.size()) {
			for(int x = posicion-vector.size(); x>=0; x--) {
				vector.add(null);
			}
		} 
		
		vector.setElementAt(entrada, posicion); //agrega en el indice especificado reemplazando el objeto existente
	}
	
	public void agregarLista(ArrayList<EntradaVectorPolaca> lista) {
		vector.addAll(lista);
	}
	
	//Toma una collection y le setea al vector todos sus elementos eliminándolos de la misma
	public void moverLista(ArrayList<EntradaVectorPolaca> lista) {
		while(lista.size() > 0) {
			vector.add(lista.remove(0)); //los va eliminando en el orden que entraron
		}
	}
	
	public void moverCondicionIF(ArrayList<EntradaVectorPolaca> lista) {
		if(!lista.isEmpty()) {
			//Tomamos el ultimo elemento de la lista que nos dice que tipo de condicion es 
			String tipoCondicion = lista.remove(lista.size() -1).getNombre();
			//aca vamos a apilar las posiciones (dentro del vector polaca) de los casilleros a completar en la condicion
			StackAuxiliarPolaca stack = StackAuxiliarPolaca.getInstance();
			//indica si se encuentra en la primer condicion o en la segunda (para condiciones compuestas)
			boolean primerCondicion = true;
			for(int x=0; x<lista.size();x++) {
				if(lista.get(x).getNombre().equals("_CMP")) {

					//avanzamos la x una posicion para obtener el campo donde se encuentra la comparacion
					x++;
					EntradaVectorPolaca comparacion = lista.get(x);
					
					//avanzamos la x una posicion más para obtener el casillero para direcciones
					x++;
					//Apilamos la dirección de dicho casillero (ya guardamos su posición futura dentro del vector,
					// y NO su posición dentro de la lista en la que se encuentra, la cual es temporal)
					stack.push(vector.size()+x);
					
					//El OR (en su primer condicion) y el operador != requieren que se niegue la condicion
					if(tipoCondicion.equals(NEGACION)) {
						comparacion.setNombre(negarCondicion(comparacion.getNombre()));
					}
					if(tipoCondicion.equals(OR) && primerCondicion) {
						comparacion.setNombre(negarCondicion(comparacion.getNombre()));
						/*
						 * El OR en su primer condicion es el unico que salta al THEN (y no al ELSE) en caso
						 * de que evalúe por verdad. Por eso completamos la direccion de salto directamente acá.
						 * Esto es así porque aca mismo ya contamos con dicha direccion (viene inmediatamente despues
						 * de la condicion); mientras que la direccion del comienzo del ELSE solo la sabemos en
						 * YACC y debemos completarla ahí.
						 */
						 EntradaVectorPolaca direccionSalto = lista.get(x);
						 direccionSalto.setNombre(String.valueOf(vector.size() + lista.size()));
						 //desapilamos el valor anterior ya que fue completado aca y no lo necesitamos 
						 stack.pop();
					}
					
					primerCondicion = false;

				}
			}
			
			/*
			 * Ponemos en el stack la cantidad de casilleros que se deben completar en el paso siguiente.
			 * Hacemos esto para que llegado al final del THEN, Yacc tome este valor y sepa si tiene que
			 * decrementar la pila 1 o 2 posiciones más (esta es la única forma de saberlo, ya que la pila
			 * puede contener otros valores que no pertenecen a la condicion del IF).
			 */
			
			if(tipoCondicion.equals(AND)) {
				stack.push(2);
			} else {
				/*
				 * Todas las otras tienen pendiente de resolucion 1 sola direccion (incluso el OR,
				 * porque si bien es compuesta, uno de los saltos ya fue resuelto aca mismo)
				 */	
				stack.push(1);
			}
		}
		//pasamos la lista al vector y la vaciamos
		moverLista(lista);
	}
	
	public void moverCondicionREPEAT(ArrayList<EntradaVectorPolaca> lista) {
		if(!lista.isEmpty()) {
			
			//Tomamos el ultimo elemento de la lista que nos dice que tipo de condicion es 
			String tipoCondicion = lista.remove(lista.size() -1).getNombre();
			//indica si se encuentra en la primer condicion o en la segunda (para condiciones compuestas)
			boolean primerCondicion = true;
			//tomamos la direccion de inicio de las sentencias colocada en la pila anteriormente por YACC
			int posicionComienzoSentencias = StackAuxiliarPolaca.getInstance().pop();

			for(int x=0; x<lista.size();x++) {
				if(lista.get(x).getNombre().equals("_CMP")) {

					//avanzamos la x una posicion para obtener el campo donde se encuentra la comparacion
					x++;
					EntradaVectorPolaca comparacion = lista.get(x);
					
					//avanzamos la x una posicion más para obtener el casillero para direcciones
					x++;
					EntradaVectorPolaca direccionSalto = lista.get(x);

					//En todos estos casos negamos la condicion (en el caso del OR la 1era y la 2da) y saltamos al inicio
					if(tipoCondicion.equals(SIMPLE) || tipoCondicion.equals(OR) || (tipoCondicion.equals(AND) && !primerCondicion)) {
						comparacion.setNombre(negarCondicion(comparacion.getNombre()));
						direccionSalto.setNombre(String.valueOf(posicionComienzoSentencias));
					}
					
					//Aca no negamos, solo saltamos al inicio
					if(tipoCondicion.equals(NEGACION)) {
						direccionSalto.setNombre(String.valueOf(posicionComienzoSentencias));
					}
					
					//este es el unico caso que salta al final cuando evalúa por verdadero
					if(tipoCondicion.equals(AND) && primerCondicion) {
						direccionSalto.setNombre(String.valueOf(vector.size() + lista.size()));
					}
					
					primerCondicion = false;

				}
			}
		}
		//pasamos la lista al vector y la vaciamos
		moverLista(lista);
	}
	
	/*
	 * Toma del stack tantos elementos como indique 'cantCasilleros',
	 * y a cada una de las posiciones del vector indicada por esos elementos,
	 * le setea 'posicionASaltar'
	 */
	public void resolverSaltos(int posicionASaltar, int cantCasilleros) {
		StackAuxiliarPolaca stack = StackAuxiliarPolaca.getInstance();
		while(cantCasilleros > 0) {
			agregar(new EntradaVectorPolaca(String.valueOf(posicionASaltar)), stack.pop());
			cantCasilleros--;
		}
	}
	
	public static String negarCondicion(String condicionActual) {
		
		if(condicionActual.equals(IGUAL)) {
			return DISTINTO;
		}
		
		if(condicionActual.equals(DISTINTO)) {
			return IGUAL;
		}
		
		if(condicionActual.equals(MAYOR)) {
			return MENOR_O_IGUAL;
		}
		
		if(condicionActual.equals(MENOR)) {
			return MAYOR_O_IGUAL;
		}
		
		if(condicionActual.equals(MAYOR_O_IGUAL)) {
			return MENOR;
		}
		
		if(condicionActual.equals(MENOR_O_IGUAL)) {
			return MAYOR;
		}
		
		return condicionActual; //nunca debería salir por aca
	}
	
	public void imprimirVector() {
		Iterator<EntradaVectorPolaca> iterator = getInstance().vector.iterator();
		while(iterator.hasNext()) {
			System.out.print("|" + iterator.next());
		}
	}
	
	public String toString() {
		String out = new String();
		for (int posicion = 0; posicion < vector.size(); posicion++) {
			EntradaVectorPolaca actual = vector.get(posicion);
			out = out + "Posicion: " + posicion +
				"\tNombre: " + actual.getNombre() + "                   ".substring(actual.getNombre() != null ? actual.getNombre().length() : 4) +
				"Tipo: " + actual.getTipo()       + "                   ".substring(actual.getTipo() != null ? actual.getTipo().length() : 4) +
				"\n";
		}
		return out;
	}
	
	
	public int getPosicionActual() {
		return vector.size();
	}
	
	public String toASM() {
		String out = new String();
		
		out = TablaDeSimbolos.getInstance().toASM();
		
		out = out + "Aca empieza a ejecutar!\n";
		
		for (int posicion = 0; posicion < vector.size(); posicion++) {
			EntradaVectorPolaca actual = vector.get(posicion);
			String tipo = actual.getTipo();
			String nombre = actual.getNombre();
			/* Variables y Constantes */
			if(tipo == TablaDeSimbolos.TIPO_CTE_REAL)
				out = out + "    Apilo en PilaPolaca CTE: " + nombre + "\n";
			else if (tipo == TablaDeSimbolos.TIPO_FLOAT)
				out = out + "    Apilo en PilaPolaca VAR: " + nombre + "\n";
			else if (tipo == TablaDeSimbolos.TIPO_CTE_STRING)
				out = out + "    Apilo en PilaPolaca CTE STRING: " + nombre + "\n";
			else if (tipo == TablaDeSimbolos.TIPO_STRING)
				out = out + "    Apilo en PilaPolaca VAR STRING: " + nombre + "\n";
			else
				/* Operaciones */
				if(nombre == "+")
					out = out + "    Desapilo 2 de PilaPolaca, apilo auxiliar y genero ASM de suma (si son 2 CTE, podemos optimizar y apilar el resultado)\n";
				else if(nombre == "-")
					out = out + "    Desapilo 2 de PilaPolaca, apilo auxiliar y genero ASM de resta (si son 2 CTE, podemos optimizar y apilar el resultado)\n";
				else if(nombre == "/")
					out = out + "    Desapilo 2 de PilaPolaca, apilo auxiliar y genero ASM de division (si son 2 CTE, podemos optimizar y apilar el resultado)\n";
				else if(nombre == "*")
					out = out + "    Desapilo 2 de PilaPolaca, apilo auxiliar y genero ASM de multiplicacion (si son 2 CTE, podemos optimizar y apilar el resultado)\n";
			
				/* Asignacion */
				else if(nombre == "=")
					out = out + "    Desapilo 2 de PilaPolaca y genero ASM de asignacion (depende del tipo del 1er desapilado)\n";
						
			    /* Comparaciones */
				else if(nombre == "_CMP")
				{	
					posicion++; //Condicion
					String condicion = vector.get(posicion).getNombre();
					
					posicion++; //Salto
					String salto = vector.get(posicion).getNombre();
					
					out = out + "    Desapilo 2 de PilaPolaca y genero ASM de comparacion por ";
					if(condicion == DISTINTO)
						out = out + "DISTINTO";
					else if(condicion == IGUAL)
						out = out + "IGUAL";
					else if(condicion == MAYOR)
						out = out + "MAYOR";
					else if(condicion == MAYOR_O_IGUAL)
						out = out + "MAYOR_O_IGUAL";
					else if(condicion == MENOR)
						out = out + "MENOR";
					else if(condicion == MENOR_O_IGUAL)
						out = out + "MENOR_O_IGUAL";
					out = out + " con un salto a \"_etiqueta_" + salto + "\"\n";
				}
				else if(nombre == SIEMPRE)
				{	posicion++; //Salto
					String salto = vector.get(posicion).getNombre();
					out = out + "    Salto sin condicion a \"_etiqueta_" + salto + "\"\n";
				}

				else if(nombre == "@IF")
					{} //No hace falta etiquetar el IF
				else if(nombre == "@THEN") 
					out = out + "_etiqueta_" + posicion + ": (THEN)\n"; 
				else if(nombre == "@ELSE")
					out = out + "_etiqueta_" + posicion + ": (ELSE)\n"; 
				else if(nombre == "@ENDIF")
					out = out + "_etiqueta_" + posicion + ": (ENDIF)\n";
				else if(nombre == "@REPEAT")
					out = out + "_etiqueta_" + posicion + ": (REPEAT)\n";
				else if(nombre == "@UNTIL")
					{} //No hace falta etiquetar el UNTIL
				else if(nombre == "@END REPEAT-UNTIL") 
					{} //No hace falta etiquetar el final del REPEAT
				
				/* Otros */
				else if(nombre == "@DISPLAY")
					out = out + "    ***Aca hay un display() y no se como se trata***\n";
		}
		out = out + "Aca termina :P\n";
		return out;
	}
	
}
