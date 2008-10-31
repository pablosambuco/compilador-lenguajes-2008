package compilador.sintaxis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

public class VectorPolaca {

	private java.util.Vector<EntradaVectorPolaca> vector;
	
	//Constantes para condiciones
	public static String AND = "_AND";
	public static String OR = "_OR";
	public static String NEGACION = "_NEGACION";
	public static String SIMPLE = "_SIMPLE";
	
	//Constantes para expresiones
	public static String DISTINTO = "BNE";
	public static String IGUAL = "BEQ";
	public static String MENOR = "BLT";
	public static String MAYOR = "BGT";
	public static String MENOR_O_IGUAL = "BLE";
	public static String MAYOR_O_IGUAL = "BGE";

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
			//aca vamos a apilar las posiciones (dentro del vecto polaca) de los casilleros a completar en la condicion
			StackAuxiliarPolaca stack = StackAuxiliarPolaca.getInstance();
			//indica si se encuentra en la primer condicion o en la segunda (para condiciones compuestas)
			boolean primerCondicion = true;
			for(int x=0; x<lista.size();x++) {
				if(lista.get(x).getNombre().equals("_CMP")) {
					 /* Apilamos la dirección del proximo elemento que es el casillero para direcciones.
					 * Ya guardamos su posición futura dentro del vector, y NO su posicion dentro de la
					 * lista en la que se encuentra (la cual es temporal)*/
					stack.push(vector.size()+x+1);
					x = x + 2; //avanzamos dos (uno es el casillero de direcciones y el otro es el de la comparacion en sí)
					EntradaVectorPolaca comparacion = lista.get(x);
					//El OR (en su primer condicion) y el operador != requieren que se niegue la condicion
					if(tipoCondicion.equals(NEGACION) || (tipoCondicion.equals(OR) && primerCondicion)) {
						comparacion.setNombre(negarCondicion(comparacion.getNombre()));
					}
					primerCondicion = false;
				}
			}
		}
		//pasamos la lista al vector y la vaciamos
		moverLista(lista);
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
	
}
