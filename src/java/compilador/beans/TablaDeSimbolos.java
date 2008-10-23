package compilador.beans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class TablaDeSimbolos {

	private java.util.ArrayList<EntradaTS> simbolos;
	
	public static final String TIPO_CTE_NUM = "Real";
	public static final String TIPO_CTE_STRING = "CteString";
	public static final String TIPO_POINTER = "Pointer";

	//TODO eliminar "cantidad" y manejarse con el sizeOf()	
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

	public int agregar(EntradaTS entrada) {
		int posicion = cantidad;
		if (!simbolos.contains(entrada)) { //Entrada TS tiene sobreescrito el equals y compara por nombre solamente
			simbolos.add(posicion, entrada);
			cantidad++;
		} else {
			posicion = simbolos.indexOf(entrada);
			simbolos.set(posicion, entrada); //si ya existía, lo reemplazamos con el que nos llegó y devolvemos la posición
			//FIXME ACA hay un error, esta pisando el valor TYPE de las variables que son de ese tipo 
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
	
	public int getPosicion(String nombre) {
		return simbolos.indexOf(new EntradaTS(nombre));
	}
	
	public int getPosicion(EntradaTS entrada) {
		return simbolos.indexOf(entrada);
	}
	
	/**
	 * Toma una lista de constantes (String o Numéricas) y le setea en el campo TYPEDEF
	 * de la tabla el valor recibido en el parámetro 'tipo'
	 */
	public void setType(Collection<String> listaDeConstantes, String tipo) {
		
		Iterator<String> iter = listaDeConstantes.iterator();
		while(iter.hasNext()) {
			String valor = iter.next();
			int posicion = this.getPosicion(valor);
			EntradaTS entrada = simbolos.get(posicion);
			entrada.setTypedef(tipo);
		}
		
	}
	
	public void setTipo(String nombre, String tipo) {
		simbolos.get(this.getPosicion(nombre)).setTipo(tipo);
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
