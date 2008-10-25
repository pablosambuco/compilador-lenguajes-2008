package compilador.beans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class TablaDeSimbolos {

	//TODO implementar un mecanismo que registre cuando se produce un error y no deje seguir con la generacion de Assembler
	
	private java.util.ArrayList<EntradaTS> simbolos;
	
	
	/*
	 * Los valores que van en el campo tipo deben contener alguna caracter inválido así
	 * no pueden ser definidos como un nuevo tipo usando TYPE y así evitar problemas
	 */
	public static final String TIPO_FLOAT = "_Real";
	public static final String TIPO_POINTER = "_Pointer";
	public static final String TIPO_STRING = "_String";
	public static final String TIPO_CTE_REAL = "_CteReal";
	public static final String TIPO_CTE_STRING = "_CteString";
	public static final String TIPO_TYPE = "_Type";
	public static final String TIPO_AVG = "_AVG"; //Tipo ficticio que nunca se guarda en TS pero se utiliza para comparaciones

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
	
	public int getPosicion(String clave) {
		return simbolos.indexOf(new EntradaTS(clave));
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
			System.err.println("Tipo Duplicado: \"" + entrada.getNombre() + "\"");
		} else {
			entrada.setTipo(TIPO_TYPE);
		}
	}

	/**
	 * Toma una lista de constantes (String o Numéricas) y le setea en el campo TYPEDEF
	 * de la tabla el valor recibido en el parámetro 'tipo'. Verifica antes que no hubiera
	 * sido seteado ya.
	 */
	public void setTypedefs(Collection<String> listaDeConstantes, String tipo) {
		
		Iterator<String> iter = listaDeConstantes.iterator();
		while(iter.hasNext()) {
			EntradaTS entrada = simbolos.get(this.getPosicion(iter.next()));
			if(entrada.getTypedef() != null) {
				//FIXME Esto es feo, ya que sí podrian repetirse dentro de distintos tipos creados por el usuario, pero la TS solo admite una entrada con el mismo nombre :-s
				System.err.println("La constante \"" + entrada.getValor() + "\" ya fue utilizada en una declaración de tipos.");
			} else {
				entrada.setTypedef(tipo);
			}
		}
	}
	
	/**
	 * Toma una lista de IDs y le setea en el campo TIPO
	 * de la TS el valor recibido en el parámetro 'tipo'
	 * Verifica antes que no hubiera sido seteado ya.
	 */
	public void setTipos(Collection<String> listaDeIDs, String tipo) {
		Iterator<String> iter = listaDeIDs.iterator();
		while(iter.hasNext()) {
			EntradaTS entrada = simbolos.get(this.getPosicion(iter.next()));
			if(entrada.getTipo() != null) {
				if(entrada.getTipo().equals(TIPO_TYPE)) {
					System.err.println("La variable \"" + entrada.getNombre() + "\" no puede ser declarada ya que fue definida como un tipo de datos");
				} else {
					System.err.println("Variable Duplicada: \"" + entrada.getNombre() + "\"");
				}
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
			System.err.println("Tipo Inválido: \"" + clave + "\"");
		}
	}
	
	/**
	 * Verifica si una variable fue declarada antes de su primer uso
	 */
	public void verificarDeclaracion(String clave) {
		EntradaTS entrada = this.getEntrada(clave); 
		if( entrada.getTipo() == null) {
			System.err.println("La variable \"" + clave + "\" no fue declarada");
		} else {
			if(entrada.getTipo().equals(TIPO_TYPE)) {
				System.err.println("La variable \"" + clave + "\" se corresponde con un tipo de dato y no puede ser utilizada");
			}
		}
	}
	
	public void verificarAsignacion(String idLadoIzquierdo, ArrayList<String> valoresLadoDerecho) {
		
		/*
		 * Cuando del lado derecho tengo un solo valor, debo verificar que
		 *  sea de un tipo compatible con el id del lado izquierdo
		 */
		if(valoresLadoDerecho.size() == 1) {
			EntradaTS entradaLadoIzquierdo = getEntrada(idLadoIzquierdo);
			String valorLadoDerecho = valoresLadoDerecho.get(0);
			//Antes que nada verificamos si el valor a la derecha es un AVG, ya que al no estar en tabla de símbolos nos traería problemas al buscar la compatibilidad de tipos
			if(valorLadoDerecho.equals(TIPO_AVG)) {
				if(!entradaLadoIzquierdo.getTipo().equals(TIPO_FLOAT)) {
					System.err.println("Error en asignación a la variable \"" + idLadoIzquierdo + "\": incompatibilidad de tipos");
				}
			} else {
				//una vez que sabemos que no es AVG, nos fijamos la compatibilidad mirando la TS
				if(!sonTiposCompatibles(entradaLadoIzquierdo, getEntrada(valorLadoDerecho))) {
					System.err.println("Error en asignación a la variable \"" + idLadoIzquierdo + "\": incompatibilidad de tipos");
				}
			}
			
		} else {
		/*
		 * Cuando en el lado derecho tengo más de un elemento, significa que es una expresión
		 * y las expresiones sólo permiten operaciones entre variables FLOAT, Ctes. Numéricas y
		 * AVG. Por eso no sólo debo verificar que todas se adecuén a alguno de esos tipos, sino
		 * que también debo validar que el id del lado izquierdo también sea FLOAT.
		 */
					
			if(!getEntrada(idLadoIzquierdo).getTipo().equals(TIPO_FLOAT)) {
				System.err.println("Error en asignación a la variable \"" + idLadoIzquierdo + "\": incompatibilidad de tipos");
			}
			
			Iterator<String> iter = valoresLadoDerecho.iterator();
			while (iter.hasNext()) {
				String aux = iter.next(); //contiene un ID, Cte Numerica o la palabra AVG (esta ultima no se encuentra en TS)
				String tipoValorLadoDerecho = aux.equals(TIPO_AVG) ? TIPO_AVG : getEntrada(aux).getTipo();
				if(!tipoValorLadoDerecho.equals(TIPO_FLOAT) && !tipoValorLadoDerecho.equals(TIPO_CTE_REAL) && !tipoValorLadoDerecho.equals(TIPO_AVG)) {
					System.err.println("Error en expresión: sólo se permite utilizar tipos numéricos");
					break; //cortamos porque sino tira siempre el mismo error
				}
			}
		}		
	}
	
	/**
	 * Indica si el tipo del lado izquierdo de una asignacion es compatible con el del lado derecho.
	 */
	public boolean sonTiposCompatibles(EntradaTS entradaLadoIzquierdo, EntradaTS entradaLadoDerecho) {
		
		String tipoIdLadoIzquierdo = entradaLadoIzquierdo.getTipo();
		String tipoLadoDerecho = entradaLadoDerecho.getTipo();
		
		if(tipoIdLadoIzquierdo.equals(TIPO_STRING)) {
			if(tipoLadoDerecho.equals(TIPO_STRING))
				return true;
			if(tipoLadoDerecho.equals(TIPO_CTE_STRING))
				return true;
			//default (no es lo mismo que ponerlo abajo de todo, porque de esta forma nos evitamos comprobaciones innecesarias)
			return false;
		}
		
		if(tipoIdLadoIzquierdo.equals(TIPO_POINTER)) {
			if(tipoLadoDerecho.equals(TIPO_POINTER))
				return true;
			return false;
		}
		
		if(tipoIdLadoIzquierdo.equals(TIPO_FLOAT)) {
			if(tipoLadoDerecho.equals(TIPO_FLOAT))
				return true;
			if(tipoLadoDerecho.equals(TIPO_CTE_REAL))
				return true;
			return false;
		}
		
		//Si no es ninguno de los tipos listados arriba, puede ser de un tipo definido por TYPE
		if(entradaLadoDerecho.getTypedef() != null && entradaLadoDerecho.getTypedef().equals(tipoIdLadoIzquierdo))
			return true;
		return false;
	}
	
	public String toString() {
		String out = new String();
		for (int posicion = 0; posicion < simbolos.size(); posicion++) {
			EntradaTS actual = simbolos.get(posicion);
			out = out + "Posicion: " + posicion +
				"\tNombre: " + actual.getNombre() + "                   ".substring(actual.getNombre() != null ? actual.getNombre().length() : 4) +
				"Tipo: " + actual.getTipo()       + "                   ".substring(actual.getTipo() != null ? actual.getTipo().length() : 4) +
				"Typedef: " + actual.getTypedef() + "                   ".substring(actual.getTypedef() != null ? actual.getTypedef().length() : 4) +
				"Valor: " + actual.getValor()     + "                   ".substring(actual.getValor() != null ? actual.getValor().length() : 4) +
				"\n";
		}
		return out;
	}
	
}
