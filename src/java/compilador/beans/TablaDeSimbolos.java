package compilador.beans;

import java.util.Hashtable;

public class TablaDeSimbolos {

	private java.util.Hashtable<Integer, EntradaTS> simbolos;

	private static TablaDeSimbolos instance;

	public static TablaDeSimbolos getInstance() {
		if (instance == null)
			instance = new TablaDeSimbolos();
		return instance;
	}

	private TablaDeSimbolos() {
		simbolos = new Hashtable<Integer, EntradaTS>();
	}

	public EntradaTS agregar(StringBuffer token) {

		EntradaTS aux = new EntradaTS(token.toString());
		Integer hash = aux.hashCode();

		if (!simbolos.containsKey(hash))
			simbolos.put(hash, aux);
		else
			// Por si la queremos usar para algo, recupero la entrada
			aux = (EntradaTS) simbolos.get(hash); 
		return aux;
	}

}
