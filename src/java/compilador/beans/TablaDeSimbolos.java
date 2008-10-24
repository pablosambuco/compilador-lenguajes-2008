package compilador.beans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class TablaDeSimbolos {

	//TODO implementar un mecanismo que registre cuando se produce un error y no deje seguir con la generacion de Assembler
	
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
	 * S�lo agrega una entrada si la misma no exist�a.
	 * Sino simplemente devuelve la posici�n. 
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
	
	public void setValor(String clave, String valor) {
		simbolos.get(this.getPosicion(clave)).setValor(valor);
	}
	
	public void setTypedef(String clave, String typedef) {
		simbolos.get(this.getPosicion(clave)).setTypedef(typedef);
	}
	
	public void crearNuevoTipo(String clave) {
		EntradaTS entrada = simbolos.get(this.getPosicion(clave));
		if(entrada.getTipo() != null) {
			System.err.println("Tipo Duplicado: " + entrada.getNombre());
		} else {
			entrada.setTipo(TIPO_TYPE);
		}
	}

	/**
	 * Toma una lista de constantes (String o Num�ricas) y le setea en el campo TYPEDEF
	 * de la tabla el valor recibido en el par�metro 'tipo'. Verifica antes que no hubiera
	 * sido seteado ya.
	 */
	public void setTypedefs(Collection<String> listaDeConstantes, String tipo) {
		
		Iterator<String> iter = listaDeConstantes.iterator();
		while(iter.hasNext()) {
			EntradaTS entrada = simbolos.get(this.getPosicion(iter.next()));
			if(entrada.getTypedef() != null) {
				//FIXME Esto es feo, ya que s� podrian repetirse dentro de distintos tipos creados por el usuario, pero la TS solo admite una entrada con el mismo nombre :-s
				System.err.println("La constante " + entrada.getValor() + " ya fue utilizada en una declaraci�n de tipos.");
			} else {
				entrada.setTypedef(tipo);
			}
		}
	}
	
	/**
	 * Toma una lista de IDs y le setea en el campo TIPO
	 * de la TS el valor recibido en el par�metro 'tipo'
	 * Verifica antes que no hubiera sido seteado ya.
	 */
	public void setTipos(Collection<String> listaDeIDs, String tipo) {
		Iterator<String> iter = listaDeIDs.iterator();
		while(iter.hasNext()) {
			EntradaTS entrada = simbolos.get(this.getPosicion(iter.next()));
			if(entrada.getTipo() != null) {
				System.err.println("Variable Duplicada: " + entrada.getNombre());
			} else {
				entrada.setTipo(tipo);
			}
		}
	}
	
	/**
	 * Verifica si el tipo que se le quiere asignar a una variable fue definido
	 * con anterioridad usando TYPE.
	 */
	public void verificarTipoValido(String clave) {
		EntradaTS entrada = this.getEntrada(clave); 
		if( entrada.getTipo() == null || !entrada.getTipo().equals(TIPO_TYPE)) {
			System.err.println("Tipo Inv�lido: " + clave);
		}
	}
	
	/**
	 * Verifica si una variable fue declarada antes de su primer uso
	 */
	public void verificarDeclaracion(String clave) {
		EntradaTS entrada = this.getEntrada(clave); 
		if( entrada.getTipo() == null ) {
			System.err.println("La variable " + clave + " no fue declarada");
		}
	}
	
	
	/**
	 * Indica si una variable es del tipo FLOAT (el �nico
	 * admitido para realizar operaciones aritm�ticas y de comparaci�n)
	 */
	public void verificarTipoDatoReal(String clave) {
		EntradaTS entrada = this.getEntrada(clave); 
		if( entrada.getTipo() == null || !entrada.getTipo().equals(TIPO_CTE_NUM)) {
			System.err.println("Incompatibilidad de tipos. La variable " + clave + " debe ser num�rica");
		}
	}
	
	@Override
	public String toString() {
		String out = new String();
		int posicion = 2;
		for (posicion = 0; posicion < simbolos.size(); posicion++) {
			EntradaTS actual = simbolos.get(posicion);
			
			out = out + "Posicion: " + posicion + "\tNombre: "
					+ actual.getNombre() + "\n";
		}
		return out;
	}
	
	public void verificarAsignacion(String idLadoIzquierdo, ArrayList<String> valoresLadoDerecho) {
		//TODO aca va la magia!
	}
	
}
