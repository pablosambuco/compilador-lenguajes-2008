package compilador.beans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import compilador.parser.Parser;
import compilador.sintaxis.EntradaVectorPolaca;

public class TablaDeSimbolos {

	public static boolean abortarCompilacion = false;
	
	private java.util.ArrayList<EntradaTS> simbolos;

	public static final int TAMANIO_MAXIMO_CTE_STRING = 30;
	public static final int TAMANIO_MAXIMO_TOKEN = 15;
	
	public static final String VARIABLE_INICIALIZADA = "inicializada";
	
	
	/*
	 * Los valores que van en el campo tipo deben contener alguna caracter invalido asi
	 * no pueden ser definidos como un nuevo tipo usando TYPE y asi evitar problemas
	 */
	public static final String TIPO_FLOAT = "_Real";
	public static final String TIPO_POINTER = "_Pointer";
	public static final String TIPO_STRING = "_String";
	public static final String TIPO_CTE_REAL = "_CteReal";
	public static final String TIPO_CTE_STRING = "_CteString";
	public static final String TIPO_TYPE = "_Type";

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
	 * Solo agrega una entrada si la misma no existia.
	 * Sino simplemente devuelve la posicion. 
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

	/**
	 * Se agrega una cadena a la tabla de simbolos verificando que no existiera ya una con el mismo valor.
	 * A diferencia de los metodos anteriores, no se compara por nombre, ya que el mismo no se corresponde
	 * con el valor de la cadena
	 */
	public int agregarCadena(String valorCadena) {
		
		int posicionActual = 0;
		Iterator<EntradaTS> iter = simbolos.iterator();
		while(iter.hasNext()) {
			EntradaTS entradaAux = iter.next();
			if(entradaAux.getTipo() == TIPO_CTE_STRING && entradaAux.getValor().equals(valorCadena)){
				return posicionActual;
			}
			posicionActual++;
		}
		
		//creamos una entrada en la tabla de simbolos y le seteamos ciertos atributos (ya sabemos que es un STRING)
		EntradaTS entradaNueva = new EntradaTS("@cadena"+posicionActual);
		entradaNueva.setTipo(TablaDeSimbolos.TIPO_CTE_STRING);
		entradaNueva.setValor(valorCadena);
		entradaNueva.setLongitud(String.valueOf(valorCadena.length()));
		
		simbolos.add(entradaNueva);
		return posicionActual;

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
	
	public void crearNuevoTipo(String clave, int tipo) {
		EntradaTS entrada = simbolos.get(this.getPosicion(clave));
		if(entrada.getTipo() != null) {
			imprimirError("Tipo Duplicado: \"" + entrada.getNombre() + "\"");
		} else {
			entrada.setTipo(TIPO_TYPE);
			entrada.setValor(tipo == Parser.CTE_NUM ? TIPO_FLOAT : TIPO_STRING);
		}
	}

	/**
	 * Toma una lista de constantes (String o Numericas) y le setea en el campo TYPEDEF
	 * de la tabla el valor recibido en el parametro 'tipo'. Verifica antes que no hubiera
	 * sido seteado ya.
	 */
	public void setTypedefs(Collection<String> listaDeConstantes, String tipo) {
		
		Iterator<String> iter = listaDeConstantes.iterator();
		while(iter.hasNext()) {
			EntradaTS entrada = simbolos.get(this.getPosicion(iter.next()));
			if(entrada.getTypedef() != null) {
				//FIXME Esto es feo, ya que si podrian repetirse dentro de distintos tipos creados por el usuario, pero la TS solo admite una entrada con el mismo nombre :-s
				imprimirError("La constante \"" + entrada.getValor() + "\" ya fue utilizada en una declaracion de tipos.");
			} else {
				entrada.setTypedef(tipo);
			}
		}
	}
	
	/**
	 * Toma una lista de IDs y le setea en el campo TIPO
	 * de la TS el valor recibido en el parametro 'tipo'
	 * Verifica antes que no hubiera sido seteado ya.
	 */
	public void setTipos(Collection<String> listaDeIDs, String tipo) {
		Iterator<String> iter = listaDeIDs.iterator();
		while(iter.hasNext()) {
			EntradaTS entrada = simbolos.get(this.getPosicion(iter.next()));
			if(entrada.getTipo() != null) {
				if(entrada.getTipo().equals(TIPO_TYPE)) {
					imprimirError("La variable \"" + entrada.getNombre() + "\" no puede ser declarada ya que fue definida como un tipo de datos");
				} else {
					imprimirError("Variable Duplicada: \"" + entrada.getNombre() + "\"");
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
			imprimirError("Tipo Invalido: \"" + clave + "\"");
		}
	}
	
	/**
	 * Verifica si una variable fue declarada antes de su primer uso
	 */
	public void verificarDeclaracion(String clave) {
		EntradaTS entrada = this.getEntrada(clave); 
		if( entrada.getTipo() == null) {
			imprimirError("La variable \"" + clave + "\" no fue declarada");
		} else {
			if(entrada.getTipo().equals(TIPO_TYPE)) {
				imprimirError("La variable \"" + clave + "\" se corresponde con un tipo de dato y no puede ser utilizada");
			}
		}
	}
	
	public void verificarAsignacion(String idLadoIzquierdo, ArrayList<String> valoresLadoDerecho) {
		
		EntradaTS entradaLadoIzquierdo = getEntrada(idLadoIzquierdo);

		/*
		 * Cuando del lado derecho tengo un solo valor, debo verificar que
		 *  sea de un tipo compatible con el id del lado izquierdo
		 */
		if(valoresLadoDerecho.size() == 1) {
			String valorLadoDerecho = valoresLadoDerecho.get(0);
			//Nos fijamos la compatibilidad mirando la TS
			if(!sonTiposCompatibles(entradaLadoIzquierdo, getEntrada(valorLadoDerecho))) {
				imprimirError("Error en asignacion a la variable \"" + idLadoIzquierdo + "\": incompatibilidad de tipos");
			}
		} else {
		/*
		 * Cuando en el lado derecho tengo mas de un elemento, significa que es una expresion
		 * y las expresiones solo permiten operaciones entre variables FLOAT y Ctes Numericas.
		 * Por eso no solo debo verificar que todas se adecuen a alguno de esos tipos, sino
		 * que tambien debo validar que el id del lado izquierdo tambien sea FLOAT.
		 */
					
			if(entradaLadoIzquierdo.getTipo() == null || !entradaLadoIzquierdo.getTipo().equals(TIPO_FLOAT)) {
				imprimirError("Error en asignacion a la variable \"" + idLadoIzquierdo + "\": incompatibilidad de tipos");
			}
			
			if(!esExpresionValida(valoresLadoDerecho)){
				imprimirError("Error en asignacion de expresión: sólo se permite utilizar tipos numericos");
			}
		}		
	}
	
	public void verificarComparacion(ArrayList<String> valoresLadoIzquierdo, ArrayList<String> valoresLadoDerecho) {
		if(!esExpresionValida(valoresLadoIzquierdo) || !esExpresionValida(valoresLadoDerecho)) {
			imprimirError("Error en comparacion de expresiones: sólo se permite utilizar tipos numericos");
		}
	}
	
	public boolean esExpresionValida(ArrayList<String> valoresExpresion) {
		Iterator<String> iter = valoresExpresion.iterator();
		while (iter.hasNext()) {
			String aux = iter.next(); //contiene un ID o una Cte Numerica
			String tipoNativoLadoDerecho = getTipoNativo(getEntrada(aux).getTipo());
			if(tipoNativoLadoDerecho == null || (!tipoNativoLadoDerecho.equals(TIPO_FLOAT) && !tipoNativoLadoDerecho.equals(TIPO_CTE_REAL))) {
				return false;
			}
			//Si es un ID se verifica que este inicializado
			if(tipoNativoLadoDerecho.equals(TIPO_FLOAT)) {
				verificarInicializacionVariable(aux);
			}
		}
		return true;
	}
	
	public ArrayList<EntradaVectorPolaca> convertirAverageEnPolaca(ArrayList<String> listaDeNombres){
		ArrayList<EntradaVectorPolaca> nuevaLista = new ArrayList<EntradaVectorPolaca>();
		Iterator<String> iter = listaDeNombres.iterator();
		
		//metemos el primer elemento en la nueva lista
		if(iter.hasNext()) {
			EntradaTS entradaTS = this.getEntrada(iter.next()); 
			nuevaLista.add(new EntradaVectorPolaca(entradaTS.getValor(), entradaTS.getTipo()));
		}
		
		boolean agregarDivision = false;
		//si hay mas elementos, los vamos agregando con un '+' al final
		while(iter.hasNext()) {
			EntradaTS entradaTS = this.getEntrada(iter.next()); 
			nuevaLista.add(new EntradaVectorPolaca(entradaTS.getValor(), entradaTS.getTipo()));
			
			nuevaLista.add(new EntradaVectorPolaca("+"));
			agregarDivision = true;
		}
		
		//solo agregamos la division al final si la lista tenia mas de un elemento
		if(agregarDivision) {

			//el valor por el cual vamos a dividir, debe estar en tabla de simbolos porque es una constante mas
			EntradaTS entrada = new EntradaTS(String.valueOf(listaDeNombres.size()));
			entrada.setTipo(TablaDeSimbolos.TIPO_CTE_REAL);
			entrada.setValor(String.valueOf(listaDeNombres.size()));
			TablaDeSimbolos.getInstance().agregar(entrada); //si la cte ya existia, este metodo simplemente no la agrega
			
			nuevaLista.add(new EntradaVectorPolaca(entrada.getValor(), entrada.getTipo()));
			nuevaLista.add(new EntradaVectorPolaca("/"));
		}
		
		return nuevaLista;
	}
	
	/**
	 * Indica si el tipo del lado izquierdo de una asignacion es compatible con el del lado derecho.
	 */
	public boolean sonTiposCompatibles(EntradaTS entradaLadoIzquierdo, EntradaTS entradaLadoDerecho) {
		
		String tipoIdLadoIzquierdo = entradaLadoIzquierdo.getTipo();
		String tipoLadoDerecho = entradaLadoDerecho.getTipo();
		
		if(tipoIdLadoIzquierdo == null || tipoLadoDerecho == null)
			return false;
		
		//cualquier caso en que los tipos sean iguales, es valido. Tambien es valido cuando el tipo nativo del lado derecho coincide con el izquierdo (NUNCA al reves sino se permitirian asignaciones erroneas)
		if(tipoIdLadoIzquierdo.equals(tipoLadoDerecho) || tipoIdLadoIzquierdo.equals(getTipoNativo(tipoLadoDerecho))) {
			return true;
		}
		
		//Hay casos donde los tipos no son iguales, pero la asignacion es valida
		if(tipoIdLadoIzquierdo.equals(TIPO_STRING)) {
			if(tipoLadoDerecho.equals(TIPO_CTE_STRING))
				return true;
			//default (no es lo mismo que ponerlo abajo de todo, porque de esta forma nos evitamos comprobaciones innecesarias)
			return false;
		}
		
		if(tipoIdLadoIzquierdo.equals(TIPO_FLOAT)) {
			if(tipoLadoDerecho.equals(TIPO_CTE_REAL))
				return true;
			return false;
		}
		
		//los POINTERS permiten apuntar a Variables String, Float o a un tipo definido por el usuario (TYPE)
		if(tipoIdLadoIzquierdo.equals(TIPO_POINTER)) {
			if(getTipoNativo(tipoLadoDerecho).equals(TIPO_FLOAT) || getTipoNativo(tipoLadoDerecho).equals(TIPO_STRING))
				return true;
			return false;
		}
		
		//Si no es ninguno de los tipos listados arriba, puede ser de un tipo definido por TYPE
		if(entradaLadoDerecho.getTypedef() != null && entradaLadoDerecho.getTypedef().equals(tipoIdLadoIzquierdo))
			return true;
		
		return false;
	}
	
	/**
	 * Si el tipo de dato recibido fue definido con TYPE, se devuelve su
	 * tipo real (Cte. String o numerica). Si no se encuentra en tabla de simbolos
	 * se devuelve el mismo tipo recibido.
	 */
	public String getTipoNativo(String tipo) {
		int posicion = getPosicion(tipo); 
		if(posicion == -1) {
			return tipo;
		} else {
			//si la entrada existia y era tipo TYPE, en el campo valor tendremos el tipo nativo
			return getEntrada(posicion).getValor(); 
		}
	}
	

	public void inicializarVariable(String id) {
		getEntrada(id).setValor(VARIABLE_INICIALIZADA);
		
	}

	public void verificarInicializacionVariable(String id) {
		if(getEntrada(id).getValor() != VARIABLE_INICIALIZADA){
			imprimirError("Variable \"" + id +"\" no inicializada.");
		}
	}
	
	public String toString() {
		StringBuffer out = new StringBuffer();
		for (int posicion = 0; posicion < simbolos.size(); posicion++) {
			EntradaTS actual = simbolos.get(posicion);
			out.append(
				"Posicion: " + posicion           + 		  "         ".substring(String.valueOf(posicion).length()) +
				"Nombre: " + actual.getNombre()   + "                   ".substring(actual.getNombre() != null ? actual.getNombre().length() : 4) +
				"Tipo: " + actual.getTipo()       + "                   ".substring(actual.getTipo() != null ? actual.getTipo().length() : 4) +
				"Typedef: " + actual.getTypedef() + "                   ".substring(actual.getTypedef() != null ? actual.getTypedef().length() : 4) +
				"Valor: " + actual.getValor()     + "                   ".substring(actual.getValor() != null ? actual.getValor().length() : 4) +
				"Longitud: "+actual.getLongitud() + "                   ".substring(actual.getLongitud() != null ? actual.getLongitud().length() : 4) +
				"\n");
		}
		return out.toString();
	}
	
	private void imprimirError(String error) {
		System.err.println(error);
		abortarCompilacion = true; //seteamos en true el flag para que no se continue con el paso siguiente (pasaje a Assembler)
	}

	public String toASM() {
		StringBuffer out = new StringBuffer();
		out.append(".DATA\n\n");
		out.append("MAXTEXTSIZE\t equ \t " + TAMANIO_MAXIMO_CTE_STRING + "\n");
		out.append("CARRIAGE_RETURN\t equ \t 13\n");
		out.append("LINE_FEED\t equ \t 10\n");
		out.append("SALTO_LINEA db\t CARRIAGE_RETURN, LINE_FEED, \'$\' \n");
		for(int x = 0; x < simbolos.size(); x++) {
			EntradaTS entrada = simbolos.get(x);
			if(entrada.getTipo().equals(TIPO_CTE_REAL)) // No hacemos nada!
				out.append("__" + entrada.getNombre().replace(".","_") + "\t dd \t " + Float.parseFloat(entrada.getValor()) + " ;Constante Real\n");
			else if(getTipoNativo(entrada.getTipo()).equals(TIPO_FLOAT))
				out.append("__" + entrada.getNombre() + "\t dd \t ?" + " ;Variable Real\n");
			else if(entrada.getTipo().equals(TIPO_CTE_STRING))
				out.append("_" + entrada.getNombre() + "\t db \t " + "\"" + entrada.getValor() + "\", \'$\', " +(TAMANIO_MAXIMO_CTE_STRING - Integer.parseInt(entrada.getLongitud())) + " dup (?) ;Constante String\n" );
			else if(getTipoNativo(entrada.getTipo()).equals(TIPO_STRING))
				out.append("__" + entrada.getNombre() + "\t db \t MAXTEXTSIZE dup (?),\'$\'" + " ;Variable String\n");
			else if(getTipoNativo(entrada.getTipo()).equals(TIPO_POINTER))
			out.append("__" + entrada.getNombre() + "\t dw \t ?" + " ;Variable Pointer (16 bits)\n");
		}
		
		return out.toString();
	}

	
}
