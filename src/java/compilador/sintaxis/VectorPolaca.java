package compilador.sintaxis;

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
		vector.add(vector.size(), entrada);
	}
	
	public void imprimirVector() {
		Iterator<EntradaVectorPolaca> iterator = getInstance().vector.iterator();
		while(iterator.hasNext()) {
			System.out.println(iterator.next().toString() + " ");
		}
	}
	
}
