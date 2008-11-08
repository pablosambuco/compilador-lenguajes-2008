package compilador.sintaxis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;
import java.util.Vector;

import sun.misc.FpUtils;

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
	
	//Este objeto se utiliza al pasar a assembler. Cuando el resultado esta en FPU, ponemos este objeto en nuestra pila polaca 
	public static String RESULTADO_EN_FPU = "RESULTADO_EN_FPU";
	private static EntradaVectorPolaca resultadoEnFPU = new EntradaVectorPolaca("Resultado en FPU",RESULTADO_EN_FPU);  

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
		
		StringBuffer out = new StringBuffer();
		Stack<EntradaVectorPolaca> pilaPolaca = new Stack<EntradaVectorPolaca>();
				
		if (!TablaDeSimbolos.abortarCompilacion) {

			out.append(".MODEL LARGE\n");
			out.append(".386\n");
			out.append(".STACK 200h\n\n");
			
			out.append(TablaDeSimbolos.getInstance().toASM());
			
			out.append("\n\n");
			
			out.append(".CODE\n");
			
			out.append("\t mov\t AX,@DATA\t ;Comienzo de la zona de Código\n");
			out.append("\t mov\t DS,AX\n");
		
			for (int posicion = 0; posicion < vector.size(); posicion++) {
				EntradaVectorPolaca actual = vector.get(posicion);
				String tipo = actual.getTipo();
				String nombre = actual.getNombre();
				/* Variables y Constantes */
				if( tipo == TablaDeSimbolos.TIPO_CTE_REAL ||
					tipo == TablaDeSimbolos.TIPO_FLOAT ||
					tipo == TablaDeSimbolos.TIPO_CTE_STRING ||
					tipo == TablaDeSimbolos.TIPO_STRING) {
						pilaPolaca.push(actual);
				}
				else
					/* Operaciones */
					if( nombre == "+" ||
						nombre == "-" ||
						nombre == "/" ||
						nombre == "*") {
						EntradaVectorPolaca operandoLadoDerecho = pilaPolaca.pop();
						EntradaVectorPolaca operandoLadoIzquierdo = pilaPolaca.pop();
						
						out.append(operacionFPU(operandoLadoDerecho, operandoLadoIzquierdo,nombre.charAt(0)));
						//despues de operar, el resultado queda en FPU y apilamos este "PlaceHolder"
						pilaPolaca.push(resultadoEnFPU);
						
						//FIXME NOS esta trayendo problemas la optimizacion Si son constantes, optimizo
						/*if(aux1.getTipo() == TablaDeSimbolos.TIPO_CTE_REAL && aux2.getTipo() == TablaDeSimbolos.TIPO_CTE_REAL) {
							pilaPolaca.push(optimizar(aux1, aux2, nombre.charAt(0)));
						} else {
							out.append(operacionFPU(aux1,aux2,nombre.charAt(0)));
							//despues de operar, el resultado queda en FPU y apilamos este "PlaceHolder"
							pilaPolaca.push(resultadoEnFPU);
						}*/	
					}
				
				/* Asignacion */
					else if(nombre == "=") {
						EntradaVectorPolaca aux1 = pilaPolaca.pop();
						EntradaVectorPolaca aux2 = pilaPolaca.pop();
						out.append(asignar(aux1,aux2));
					}
				
				
				/* Comparaciones */
					else if(nombre == "_CMP")
					{	
						posicion++; //Condicion
						String condicion = vector.get(posicion).getNombre();
						
						posicion++; //Salto
						String salto = vector.get(posicion).getNombre();
						
						out.append("    Desapilo 2 de PilaPolaca y genero ASM de comparacion por ");
						if(condicion == DISTINTO)
							out.append("DISTINTO");
						else if(condicion == IGUAL)
							out.append("IGUAL");
						else if(condicion == MAYOR)
							out.append("MAYOR");
						else if(condicion == MAYOR_O_IGUAL)
							out.append("MAYOR_O_IGUAL");
						else if(condicion == MENOR)
							out.append("MENOR");
						else if(condicion == MENOR_O_IGUAL)
							out.append("MENOR_O_IGUAL");
						out.append(" con un salto a \"_etiqueta_" + salto + "\"\n");
					}
					else if(nombre == SIEMPRE)
					{	posicion++; //Salto
					String salto = vector.get(posicion).getNombre();
					out.append("    Salto sin condicion a \"_etiqueta_" + salto + "\"\n");
					}
				
					else if(nombre == "@IF")
					{} //No hace falta etiquetar el IF
					else if(nombre == "@THEN") 
						out.append("etiqueta_" + posicion + ": (THEN)\n"); 
					else if(nombre == "@ELSE")
						out.append("etiqueta_" + posicion + ": (ELSE)\n"); 
					else if(nombre == "@ENDIF")
						out.append("etiqueta_" + posicion + ": (ENDIF)\n");
					else if(nombre == "@REPEAT")
						out.append("etiqueta_" + posicion + ": (REPEAT)\n");
					else if(nombre == "@UNTIL")
					{} //No hace falta etiquetar el UNTIL
					else if(nombre == "@END REPEAT-UNTIL") 
					{} //No hace falta etiquetar el final del REPEAT
				
				/* Otros */
					else if(nombre == "@DISPLAY")
						out.append("    ***Aca hay un display() y no se como se trata***\n");
			}
			out.append("\t mov\t ax,4C00h\t ;Termina la ejecución\n");
			out.append("\t int\t 21h\n");
			out.append("END\n");
		} else {
			out.append("Verificar salida de error\n");
		}
		return out.toString();
	}

	private String operacionFPU(EntradaVectorPolaca operandoLadoDerecho, EntradaVectorPolaca operandoLadoIzquierdo, char operador) {
		
		String out = new String();
		//si operando izquierdo no esta en FPU lo cargo
		if(operandoLadoIzquierdo != resultadoEnFPU) {
			out = out + "\t fld \t " + "__" + (operandoLadoIzquierdo.getTipo() == TablaDeSimbolos.TIPO_FLOAT ? operandoLadoIzquierdo.getNombre() : operandoLadoIzquierdo.getNombre().replace(".", "_")) + "\n";
		}
		//si operando derecho no esta en FPU lo cargo
		if(operandoLadoDerecho != resultadoEnFPU) {
			out = out + "\t fld \t " + "__" + (operandoLadoDerecho.getTipo() == TablaDeSimbolos.TIPO_FLOAT ? operandoLadoDerecho.getNombre() : operandoLadoDerecho.getNombre().replace(".", "_")) + "\n";
		}

		//Opero
		switch (operador) {
		case '+':
			out = out + "\t faddp \n";
			break;
		case '-':
			out = out + "\t fsubp \n";
			break;
		case '*':
			out = out + "\t fmulp \n";
			break;
		case '/':
			out = out + "\t fdivp \n";
			break;			
		}
		return out;
	}
	
	private EntradaVectorPolaca optimizar(EntradaVectorPolaca operandoLadoDerecho, EntradaVectorPolaca operandoLadoIzquierdo, char operador) {
	
		/*
		 * FIXME El problema al optimizar es que nos esta quedando un valor que no esta en el .DATA, y no se puede
		 * cargar como valor inmediato a la pila del FPU
		 */
		
		float resultado = 0;
		float operando1 = Float.parseFloat(operandoLadoIzquierdo.getNombre());
		float operando2 = Float.parseFloat(operandoLadoDerecho.getNombre());
		
		switch (operador) {
		case '+':
			resultado = operando1 + operando2;
			break;
		case '-':
			resultado = operando1 - operando2;
			break;
		case '*':
			resultado = operando1 * operando2;
			break;
		case '/':
			resultado = operando1 / operando2;
			break;			
		}	
		return new EntradaVectorPolaca(String.valueOf(resultado),TablaDeSimbolos.TIPO_CTE_REAL);
	}
	

	private String asignar(EntradaVectorPolaca operandoLadoDerecho, EntradaVectorPolaca operandoLadoIzquierdo){
		StringBuffer out = new StringBuffer();
		String tipoLadoDer = operandoLadoDerecho.getTipo(); 
		
		if(tipoLadoDer == TablaDeSimbolos.TIPO_FLOAT){
			out.append("\t mov \t eax, __" + operandoLadoDerecho.getNombre() + "\n" +
					   "\t mov \t __" + operandoLadoIzquierdo.getNombre() + ", eax \n");
		} else if(tipoLadoDer == TablaDeSimbolos.TIPO_CTE_REAL){
			out.append("\t mov \t eax," + "__" + operandoLadoDerecho.getNombre().replace(".","_") + "\n" +
					   "\t mov \t __" + operandoLadoIzquierdo.getNombre() + ", eax \n");
		} else if(tipoLadoDer == TablaDeSimbolos.TIPO_STRING){
			out.append("\t xxx" + "\n");
		} else if(tipoLadoDer == TablaDeSimbolos.TIPO_CTE_STRING){
			out.append("\t xxx" + "\n");
		} else if(tipoLadoDer == TablaDeSimbolos.TIPO_POINTER){
			//TODO arreglar por todos lados
		} else if(tipoLadoDer == RESULTADO_EN_FPU){
			out.append("\t fstp \t __" + operandoLadoIzquierdo.getNombre() + "\n");
		}
		
		return out.toString();
	}
}
