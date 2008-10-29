package compilador.sintaxis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

public class VectorPolaca {

	private java.util.Vector<EntradaVectorPolaca> vector;

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
