package compilador.beans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class TablaDeSimbolos {

	private java.util.ArrayList<EntradaTS> simbolos;
	
	public static final String TIPO_CTE_NUM = "Real";
	public static final String TIPO_CTE_STRING = "CteString";
	public static final String TIPO_POINTER = "Pointer";
	public static final String TIPO_TYPE = "TYPE";

	private static TablaDeSimbolos instance;

	public static TablaDeSimbolos getInstance() {
		if (instance == null)
			instance = new TablaDeSimbolos();
		return instance;
	}

	private TablaDeSimbolos() {
		simbolos = new ArrayList<EntradaTS>();
	}

	/**
	 * Sólo agrega una entrada si la misma no existía.
	 * Sino simplemente devuelve la posición. 
	 */
	
	public int agregar(EntradaTS entrada) {
		
		int posicion = simbolos.size();
		
		if (!simbolos.contains(entrada)) { //Entrada TS tiene sobreescrito el equals y compara por nombre solamente
			simbolos.add(entrada);
		} else {
			posicion = simbolos.indexOf(entrada);
		}

		return posicion;
	}
	
	public int agregar(String token) {
		EntradaTS aux = new EntradaTS(token.toString());
		return this.agregar(aux);
	}
	
	public int agregar(StringBuffer token) {
		return this.agregar(token.toString());
	}
	
	public String getNombre(int posicion) {
		EntradaTS actual = simbolos.get(posicion);
		return actual.getNombre();
	}
	
	public EntradaTS getEntrada(int posicion) {
		return simbolos.get(posicion);
	}
	
	public EntradaTS getEntrada(String clave) {
		return simbolos.get(this.getPosicion(clave));
	}
	
	public int getPosicion(String nombre) {
		return simbolos.indexOf(new EntradaTS(nombre));
	}
	
	public int getPosicion(EntradaTS entrada) {
		return simbolos.indexOf(entrada);
	}
	
	public void setTipo(String clave, String tipo) {
		simbolos.get(this.getPosicion(clave)).setTipo(tipo);
	}
	
	public void setValor(String clave, String valor) {
		simbolos.get(this.getPosicion(clave)).setValor(valor);
	}
	
	public void setTypedef(String clave, String typedef) {
		simbolos.get(this.getPosicion(clave)).setTypedef(typedef);
	}

	/**
	 * Toma una lista de constantes (String o Numéricas) y le setea en el campo TYPEDEF
	 * de la tabla el valor recibido en el parámetro 'tipo'
	 */
	public void setTypedefs(Collection<String> listaDeConstantes, String tipo) {
		
		Iterator<String> iter = listaDeConstantes.iterator();
		while(iter.hasNext()) {
			simbolos.get(this.getPosicion(iter.next())).setTypedef(tipo);
		}
	}
	
	/**
	 * Toma una lista de IDs y le setea en el campo TIPO
	 * de la tabla el valor recibido en el parámetro 'tipo'
	 */
	public void setTipos(Collection<String> listaDeIDs, String tipo) {
		
		Iterator<String> iter = listaDeIDs.iterator();
		while(iter.hasNext()) {
			simbolos.get(this.getPosicion(iter.next())).setTipo(tipo);
		}
	}
	
	public void verificarTipoValido(String clave) {
		EntradaTS entrada = this.getEntrada(clave); 
		if( entrada.getTipo() == null || !entrada.getTipo().equals(TIPO_TYPE)) {
			System.err.println("Tipo Inválido: " + clave);
		}
	}
	
	public void verificarDeclaracion(String clave) {
		EntradaTS entrada = this.getEntrada(clave); 
		if( entrada.getTipo() == null) {
			System.err.println("La variable " + clave + " no fue declarada");
		}
	}
	
	@Override
	public String toString() {
		String out = new String();
		int posicion;
		for (posicion = 0; posicion < simbolos.size(); posicion++) {
			EntradaTS actual = simbolos.get(posicion);
			
			out = out + "Posicion: " + posicion + "\tNombre: "
					+ actual.getNombre() + "\n";
		}
		return out;
	}
	
}
