package compilador.beans;

import java.util.ArrayList;

public class TablaDeSimbolos {

	//private java.util.Collection<EntradaTS> simbolos2;
	private java.util.ArrayList<EntradaTS> simbolos;

	private static int cantidad = 0;
	private static TablaDeSimbolos instance;

	public static TablaDeSimbolos getInstance() {
		if (instance == null)
			instance = new TablaDeSimbolos();
		return instance;
	}

	private TablaDeSimbolos() {
		simbolos = new ArrayList<EntradaTS>();
	}

	public int agregar(StringBuffer token) {

		int posicion = cantidad;
		EntradaTS aux = new EntradaTS(token.toString());

		// TODO Aca habria que ver bien si es el mismo, en teoria el hash se
		// puede repetir
		if (!simbolos.contains(aux)) {
			simbolos.add(posicion, aux);
			cantidad++;
		} else {
			posicion = simbolos.indexOf(aux);
		}

		return posicion;
	}

	@Override
	public String toString() {
		String out = new String();
		int posicion;
		for (posicion = 0; posicion < cantidad; posicion++) {
			EntradaTS actual = simbolos.get(posicion);
			
			out = out + "Posicion: " + posicion + "\tNombre: "
					+ actual.getNombre() + "\n";
		}
		return out;
	}
}
