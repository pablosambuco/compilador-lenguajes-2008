/* Imports */
%{
import compilador.analizadorLexicografico.Automata;
import compilador.beans.TablaDeSimbolos;
import compilador.util.ArchivoReader;
import compilador.sintaxis.VectorPolaca;
import compilador.sintaxis.EntradaVectorPolaca;
import java.util.ArrayList;
import java.util.Collection;
import java.lang.String;
%}

/* Definiciones */
%token ID CTE_NUM CTE_STR OP_SUMA OP_RESTA OP_MUL OP_DIV OP_ASIG AND OR OP_NEGACION OP_IGUAL OP_DISTINTO OP_MAYOR OP_MENOR  OP_MAYOR_IGUAL OP_MENOR_IGUAL PAR_ABRE PAR_CIERRA COR_ABRE COR_CIERRA COMA DOS_PUNTOS PUNTO_Y_COMA BEGIN END STRING FLOAT POINTER DEFVAR ENDDEF IF ELSE ENDIF REPEAT UNTIL TYPE AS DISPLAY AVG

/* Gramatica */
%%
start: programa {$$ = new ParserVal($1.sval); imprimir("Regla 01 (Programa Completo)\n" + $$.sval);}
;
programa: def_tipos def_var ejecucion  {$$ = new ParserVal($1.sval + "\n" + $2.sval +  "\n" + $3.sval); imprimir("Regla 02\n" + $$.sval + "\n");}
        | def_var ejecucion {$$ = new ParserVal($1.sval + "\n" + $2.sval); imprimir("Regla 03\n" + $$.sval + "\n");}
        | ejecucion {$$ = new ParserVal($1.sval); imprimir("Regla 04\n" + $$.sval + "\n");}
;
def_tipos: def_tipo {$$ = new ParserVal($1.sval); imprimir("Regla 05\n" + $$.sval + "\n");}
         | def_tipos def_tipo {$$ = new ParserVal($1.sval + "\n" + $2.sval); imprimir("Regla 06\n" + $$.sval + "\n");}
;
def_tipo: TYPE id {TablaDeSimbolos.getInstance().crearNuevoTipo($2.sval);} AS lista PUNTO_Y_COMA {$$ = new ParserVal("TYPE " + $2.sval + " AS " + $5.sval + ";"); TablaDeSimbolos.getInstance().setTypedefs(listaAux,$2.sval) /* Tomamos la Tabla de s�mbolos y en el campo TYPEDEF de las variables recibidas en la lista, le seteamos el tipo creado (verificando que no exista ya) */; imprimir("Regla 07\n" + $$.sval + "\n");}
;
lista: lista_num {$$ = new ParserVal($1.sval); imprimir("Regla 08\n" + $$.sval + "\n");}
     | lista_str {$$ = new ParserVal($1.sval); imprimir("Regla 09\n" + $$.sval + "\n");}
;
lista_num: COR_ABRE lis_num_c COR_CIERRA {$$ = new ParserVal("[" + $2.sval + "]"); imprimir("Regla 10\n" + $$.sval + "\n");}
;
lis_num_c: cte_num {$$ = new ParserVal($1.sval); listaAux = new ArrayList<String>(); listaAux.add($1.sval) /* Vamos agregando los valores en una lista para usarlos mas arriba */; imprimir("Regla 11\n" + $$.sval + "\n");}
         | lis_num_c COMA cte_num {$$ = new ParserVal($1.sval + "," + $3.sval); listaAux.add($3.sval) /*Esta regla siempre se ejecuta despues que la de arriba, por eso el ArrayList ya fue instanciado con new */; imprimir("Regla 12\n" + $$.sval + "\n");}
;
lista_str: COR_ABRE lis_str_c COR_CIERRA {$$ = new ParserVal("[" + $2.sval + "]"); imprimir("Regla 13\n" + $$.sval + "\n");}
;
lis_str_c: cte_str {$$ = new ParserVal($1.sval); listaAux = new ArrayList<String>(); listaAux.add($1.sval) /* Vamos agregando los valores en una lista para usarlos mas arriba */; imprimir("Regla 14\n" + $$.sval + "\n");}
         | lis_str_c COMA cte_str {$$ = new ParserVal($1.sval + "," + $3.sval); listaAux.add($3.sval) /*Esta regla siempre se ejecuta despues que la de arriba, por eso el ArrayList ya fue instanciado con new */; imprimir("Regla 15\n" + $$.sval + "\n");}
;
def_var: DEFVAR lista_var ENDDEF {$$ = new ParserVal("DEFVAR\n" + $2.sval + "\nENDDEF"); imprimir("Regla 16\n" + $$.sval + "\n");}
;
lista_var: lista_ids DOS_PUNTOS tipo PUNTO_Y_COMA {$$ = new ParserVal($1.sval + ":" + $3.sval + ";"); TablaDeSimbolos.getInstance().setTipos(listaAux,$3.sval) /* Tomamos la Tabla de s�mbolos y en el campo Tipo de los IDs recibidos en la lista, le seteamos el tipo */; imprimir("Regla 17\n" + $$.sval + "\n");}
         | lista_var lista_ids DOS_PUNTOS tipo PUNTO_Y_COMA {$$ = new ParserVal($1.sval + "\n" + $2.sval + ":" + $4.sval + ";"); TablaDeSimbolos.getInstance().setTipos(listaAux,$4.sval) /* Tomamos la Tabla de s�mbolos y en el campo Tipo de los IDs recibidos en la lista, le seteamos el tipo */; imprimir("Regla 18\n" + $$.sval + "\n");}
;
lista_ids: id {$$ = new ParserVal($1.sval); listaAux = new ArrayList<String>(); listaAux.add($1.sval) /* Vamos agregando los IDs en una lista para usarlos mas arriba */; imprimir("Regla 19\n" + $$.sval + "\n");}
         | lista_ids COMA id {$$ = new ParserVal($1.sval + "," + $3.sval); listaAux.add($3.sval) /*Esta regla siempre se ejecuta despues que la de arriba, por eso el ArrayList ya fue instanciado con new */; imprimir("Regla 20\n" + $$.sval + "\n");}
;

/**
En esta seccion se verifica que para instanciar variables s�lo se puedan utilizar tipos que fueron previamente
definidos haciendo uso de TYPE (adem�s de los ya soportados por el lenguaje, pero eso es checkeado autom�ticamente
por la gram�tica).
*/
tipo: FLOAT {$$ = new ParserVal(TablaDeSimbolos.TIPO_FLOAT); imprimir("Regla 21\n" + $$.sval + "\n");}
    | STRING {$$ = new ParserVal(TablaDeSimbolos.TIPO_STRING); imprimir("Regla 22\n" + $$.sval + "\n");}
    | POINTER {$$ = new ParserVal(TablaDeSimbolos.TIPO_POINTER); imprimir("Regla 23\n" + $$.sval + "\n");}
    | id {$$ = new ParserVal($1.sval); TablaDeSimbolos.getInstance().verificarTipoValido($1.sval); imprimir("Regla 24\n" + $$.sval + "\n");}
;
ejecucion: BEGIN sentencias END {$$ = new ParserVal("BEGIN\n" + $2.sval + "\nEND"); imprimir("Regla 25\n" + $$.sval + "\n");}
         | BEGIN END {$$ = new ParserVal("BEGIN\nEND"); imprimir("Regla 26\n" + $$.sval + "\n");}
;
sentencias: sentencia {$$ = new ParserVal($1.sval); imprimir("Regla 27\n" + $$.sval + "\n");}
          | sentencias sentencia {$$ = new ParserVal($1.sval + "\n" + $2.sval); imprimir("Regla 28\n" + $$.sval + "\n");}
;
sentencia: asignacion {$$ = new ParserVal($1.sval); imprimir("Regla 29\n" + $$.sval + "\n");}
         | condicional {$$ = new ParserVal($1.sval); imprimir("Regla 30\n" + $$.sval + "\n");}
         | bucle {$$ = new ParserVal($1.sval); imprimir("Regla 31\n" + $$.sval + "\n");}
         | display_command {$$ = new ParserVal($1.sval); imprimir("Regla 32\n" + $$.sval + "\n");}
;

/**
En esta secci�n se verifica que el ID del lado izquierdo haya sido declarado previamente y que los tipos en la asignacion coincidan.
Para realizar esto �ltimo, se recibe una lista de Tokens que intervienen en la operacion (IDs, Ctes. Numericas y la sentencia AVG).
Si la lista contiene solamente ID, entonces se verificar� que el tipo izquierdo coincida con el derecho. Si hay m�s de un Token
en la lista, debemos verificar que todos los IDs que aparezcan sean constantes FLOAT, ya que solo se permiten expresiones num�ricas
en el lenguaje.
*/
asignacion: id OP_ASIG expresion PUNTO_Y_COMA {$$ = new ParserVal($1.sval + " " + $3.sval + " = " + ";"); TablaDeSimbolos.getInstance().verificarDeclaracion($1.sval); TablaDeSimbolos.getInstance().verificarAsignacion($1.sval, (ArrayList<String>)$3.obj); imprimir("Regla 33\n" + $$.sval + "\n"); VectorPolaca.getInstance().agregar(new EntradaVectorPolaca($1.sval, TablaDeSimbolos.getInstance().getEntrada($1.sval).getTipo())); VectorPolaca.getInstance().moverLista(listaAuxPolaca);VectorPolaca.getInstance().agregar(new EntradaVectorPolaca("="));VectorPolaca.getInstance().agregar(new EntradaVectorPolaca(";"));}
;
/**
Este tipo de asignaci�n a una Constante String existe para que se puedan realizar asignaciones a tipos definidos con TYPE. Ej: var1 = "EUR"; (donde var1 es del tipo "moneda" el cual tiene entre su lista de valores posibles la palabra "EUR")
*/
asignacion: id OP_ASIG cte_str PUNTO_Y_COMA {$$ = new ParserVal($1.sval + " " + $3.sval + " = " + ";"); TablaDeSimbolos.getInstance().verificarDeclaracion($1.sval); listaAux = new ArrayList<String>(); listaAux.add($3.sval); TablaDeSimbolos.getInstance().verificarAsignacion($1.sval, listaAux); imprimir("Regla 33\n" + $$.sval + "\n"); VectorPolaca.getInstance().agregar(new EntradaVectorPolaca($1.sval, TablaDeSimbolos.getInstance().getEntrada($1.sval).getTipo())); VectorPolaca.getInstance().agregar(new EntradaVectorPolaca($3.sval, TablaDeSimbolos.getInstance().getEntrada($3.sval).getTipo())); VectorPolaca.getInstance().agregar(new EntradaVectorPolaca("="));VectorPolaca.getInstance().agregar(new EntradaVectorPolaca(";"));}
;
expresion: termino {$$ = new ParserVal($1.sval); $$.obj = $1.obj; imprimir("Regla 34\n" + $$.sval + "\n");}
         | expresion OP_SUMA termino {$$ = new ParserVal($1.sval + " " + $3.sval + " +"); $$.obj = $1.obj; ((ArrayList<String>)$$.obj).addAll((Collection)$3.obj); imprimir("Regla 35\n" + $$.sval + "\n"); listaAuxPolaca.add(new EntradaVectorPolaca("+"));}
         | expresion OP_RESTA termino {$$ = new ParserVal($1.sval + " " + $3.sval + " -"); $$.obj = $1.obj; ((ArrayList<String>)$$.obj).addAll((Collection)$3.obj); imprimir("Regla 36\n" + $$.sval + "\n");listaAuxPolaca.add(new EntradaVectorPolaca("-"));}
;
termino: factor {$$ = new ParserVal($1.sval); $$.obj = new ArrayList<String>(); ((ArrayList<String>)$$.obj).addAll((Collection)$1.obj); imprimir("Regla 37\n" + $$.sval + "\n");}
       | termino OP_MUL factor {$$ = new ParserVal($1.sval + " " + $3.sval + " *"); $$.obj = $1.obj; ((ArrayList<String>)$$.obj).addAll((Collection)$3.obj); imprimir("Regla 38\n" + $$.sval + "\n"); listaAuxPolaca.add(new EntradaVectorPolaca("*"));}
       | termino OP_DIV factor {$$ = new ParserVal($1.sval + " " + $3.sval + " /"); $$.obj = $1.obj; ((ArrayList<String>)$$.obj).addAll((Collection)$3.obj); imprimir("Regla 39\n" + $$.sval + "\n"); listaAuxPolaca.add(new EntradaVectorPolaca("/"));}
;
/**
En esta secci�n se verificar� que cualquier ID que se use en una expresion haya sido instanciado antes
Por otro lado, se setear� en el $$.obj un ArrayList de Strings con el ID o la constante utilizada, para que la regla
de m�s arriba la tome y pueda ir armando una lista de las mismas que finalmente se evaluar�n en la asignacion.
Para el caso de average, se setear� la palabra reservada AVG. Se utiliza un ArrayList y no simplemente un String,
porque la regla 42 contiene una expresion que puede ya venir con varios elementos en vez de uno solo.
*/
factor: id {$$ = new ParserVal($1.sval); TablaDeSimbolos.getInstance().verificarDeclaracion($1.sval); $$.obj = new ArrayList<String>(); ((ArrayList<String>)$$.obj).add($1.sval); imprimir("Regla 40\n" + $$.sval + "\n"); listaAuxPolaca.add(new EntradaVectorPolaca($1.sval, TablaDeSimbolos.getInstance().getEntrada($1.sval).getTipo()));}
      | cte_num {$$ = new ParserVal($1.sval); $$.obj = new ArrayList<String>(); ((ArrayList<String>)$$.obj).add($1.sval); imprimir("Regla 41\n" + $$.sval + "\n"); listaAuxPolaca.add(new EntradaVectorPolaca(TablaDeSimbolos.getInstance().getEntrada($1.sval).getValor(), TablaDeSimbolos.getInstance().getEntrada($1.sval).getTipo()));}
      | PAR_ABRE expresion PAR_CIERRA {$$ = new ParserVal($2.sval); $$.obj = $2.obj; imprimir("Regla 42\n" + $$.sval + "\n");}
      | average {$$ = new ParserVal($1.sval); $$.obj = new ArrayList<String>(); ((ArrayList<String>)$$.obj).add(new String(TablaDeSimbolos.TIPO_AVG)); imprimir("Regla 43\n" + $$.sval + "\n");}
;
condicional: IF PAR_ABRE condicion PAR_CIERRA {imprimir("ADD: IF\nAppend condicion(es) al vector y apilar posicion(es) de los casilleros donde se setearan las direcciones del salto. Evluar si conviene agregar un LABEL aqui (comienzo del THEN), ya que cuando la condicion tiene un OR hay una rama que salta ac�.");} sentenciasCondicional ENDIF {$$ = new ParserVal("IF(" + $3.sval + ")\n" + $6.sval + "\nENDIF"); imprimir("Reglas 44 y 45\n" + $$.sval + "\n");imprimir("APPEND: ENDIF\nDesapilar casillero para direcciones del final del THEN y escribirle el valor actual de Vector Polaca para que haga un salto incondicional. Usar la misma direccion que se desapilo (final del THEN + cte (osea, comienzo del ELSE)) para llenar los casilleros de la/las condiciones (los cuales se encuentran en la pila)");}
;
sentenciasCondicional: sentencias  {$$ = new ParserVal($1.sval); imprimir("If Simple\n");imprimir("Enviar sentencias al Vector Polaca y apilar la direccion final del mismo (esto se hace por compatibilidad con la regla anterior, ya que si bien no existe el ELSE, la regla de mas arriba intentar� levantar un valor de la pila y ahi le setear� un salto incondicional al ENDIF, que en este caso se encuentra pegado a estas sentencias)\n");}
	 				| sentencias {imprimir("Enviar sentencias al Vector Polaca y apilar la direccion final del mismo\n");} ELSE sentencias {$$ = new ParserVal($1.sval + "\nELSE\n" + $4.sval); imprimir("If Compuesto\n"); imprimir("Enviar sentencias al Vector Polaca\n");}
;
condicion: comparacion {$$ = new ParserVal($1.sval); imprimir("Regla 46\n" + $$.sval + "\n");}
         | OP_NEGACION PAR_ABRE comparacion PAR_CIERRA {$$ = new ParserVal($3.sval + " _NEGACION"); imprimir("Regla 47\n" + $$.sval + "\n"); listaAuxPolaca.add(new EntradaVectorPolaca("_NEGACION"));}
         | comparacion AND comparacion {$$ = new ParserVal($1.sval + " " + $3.sval + " _AND"); imprimir("Regla 48\n" + $$.sval + "\n"); listaAuxPolaca.add(new EntradaVectorPolaca("_AND"));}
         | comparacion OR comparacion {$$ = new ParserVal($1.sval + " " + $3.sval + " _OR"); imprimir("Regla 49\n" + $$.sval + "\n"); listaAuxPolaca.add(new EntradaVectorPolaca("_OR"));}
;
comparacion: expresion OP_IGUAL expresion {$$ = new ParserVal($1.sval + " " + $3.sval + " CMP DIRECCION BNE"); imprimir("Regla 50\n" + $$.sval + "\n"); listaAuxPolaca.add(new EntradaVectorPolaca("CMP"));listaAuxPolaca.add(new EntradaVectorPolaca("DIRECCION"));listaAuxPolaca.add(new EntradaVectorPolaca("BNE"));}
           | expresion OP_DISTINTO expresion {$$ = new ParserVal($1.sval + " " + $3.sval + " CMP DIRECCION BEQ"); imprimir("Regla 51\n" + $$.sval + "\n"); listaAuxPolaca.add(new EntradaVectorPolaca("CMP"));listaAuxPolaca.add(new EntradaVectorPolaca("DIRECCION"));listaAuxPolaca.add(new EntradaVectorPolaca("BEQ")); }
           | expresion OP_MAYOR expresion {$$ = new ParserVal($1.sval + " " + $3.sval + " CMP DIRECCION BLE"); imprimir("Regla 52\n" + $$.sval + "\n"); listaAuxPolaca.add(new EntradaVectorPolaca("CMP"));listaAuxPolaca.add(new EntradaVectorPolaca("DIRECCION"));listaAuxPolaca.add(new EntradaVectorPolaca("BLE")); }
           | expresion OP_MENOR expresion {$$ = new ParserVal($1.sval + " " + $3.sval + " CMP DIRECCION BGE"); imprimir("Regla 53\n" + $$.sval + "\n"); listaAuxPolaca.add(new EntradaVectorPolaca("CMP"));listaAuxPolaca.add(new EntradaVectorPolaca("DIRECCION"));listaAuxPolaca.add(new EntradaVectorPolaca("BGE")); }
           | expresion OP_MAYOR_IGUAL expresion {$$ = new ParserVal($1.sval + " " + $3.sval + " CMP DIRECCION BLT"); imprimir("Regla 54\n" + $$.sval + "\n"); listaAuxPolaca.add(new EntradaVectorPolaca("CMP"));listaAuxPolaca.add(new EntradaVectorPolaca("DIRECCION"));listaAuxPolaca.add(new EntradaVectorPolaca("BLT")); }
           | expresion OP_MENOR_IGUAL expresion {$$ = new ParserVal($1.sval + " " + $3.sval + " CMP DIRECCION BGT"); imprimir("Regla 55\n" + $$.sval + "\n"); listaAuxPolaca.add(new EntradaVectorPolaca("CMP"));listaAuxPolaca.add(new EntradaVectorPolaca("DIRECCION"));listaAuxPolaca.add(new EntradaVectorPolaca("BGT")); }
;
bucle: REPEAT {imprimir("ADD: REPEAT\nApilar Direccion Vector Actual");} sentencias UNTIL PAR_ABRE condicion PAR_CIERRA PUNTO_Y_COMA {$$ = new ParserVal("REPEAT\n" + $3.sval + "\nUNTIL(" + $6.sval + ");"); imprimir("Regla 56\n" + $$.sval + "\n"); imprimir("Tomar la(s) condicion(es) de listaAux, hacer tratamiento y apuntar a la direccion en el tope de la pila\nADD: LABEL FIN");}
;
display_command: DISPLAY PAR_ABRE cte_str PAR_CIERRA PUNTO_Y_COMA {$$ = new ParserVal("DISPLAY(" + $3.sval + ");"); imprimir("Regla 57\n" + $$.sval + "\n");}
;
average: AVG PAR_ABRE lista_num PAR_CIERRA {$$ = new ParserVal("AVG(" + $3.sval + ")"); imprimir("Regla 58\n" + $$.sval + "\n");}
;
id: ID {$$ = new ParserVal(TablaDeSimbolos.getInstance().getNombre(yylval.ival)); imprimir("Regla 59\n" + $$.sval + "\n");}
;
cte_num: CTE_NUM {$$ = new ParserVal(TablaDeSimbolos.getInstance().getNombre(yylval.ival))/* Aca s� o s� necesitamos sacar el nombre y no el valor (aunque aparezcan con un "_"), sino las reglas de mas arriba nunca las van a encontrar en TS */; imprimir("Regla 60\n" + $$.sval + "\n");}
;
cte_str: CTE_STR {$$ = new ParserVal(TablaDeSimbolos.getInstance().getNombre(yylval.ival))/* Aca s� o s� necesitamos sacar el nombre y no el valor (aunque aparezcan con un "_"), sino las reglas de mas arriba nunca las van a encontrar en TS */; imprimir("Regla 61\n" + $$.sval + "\n");}
;

%%
	//lo habilitamos o deshabilitamos para debuggear
	public void imprimir(String valor){
		System.out.println(valor);
	}
	
	void yyerror(String mensaje) {
		System.out.println("Error: " + mensaje);
	}
	public static final int ESTADO_INICIAL = 0;
	public static final int ESTADO_FINAL = 36;

	//Manejo de errores lexicos
	public static int INCOMPLETO=0;
	public static int ERROR_LEXICO=YYERRCODE;
	
	public static ArrayList<String> listaAux = null;
	
	//Esta lista ya est� instanciada y cada elemento que se agrega es luego tomado por la regla correspondiente, por ende nunca contiene basura
	public static ArrayList<EntradaVectorPolaca> listaAuxPolaca = new ArrayList<EntradaVectorPolaca>();

	int yylex() {
		Automata automata = Automata.getInstance();
		return automata.getToken(yylval);
	}

	public static void main(String args[]) {
		Parser par = new Parser();
		ArchivoReader archivo = ArchivoReader.getInstance();
		archivo.abrirArhivo(args[0]);
		par.yyparse();
		archivo.cerrarArhivo();
		System.out.println("\n\nTABLA DE SIMBOLOS\n\n" + TablaDeSimbolos.getInstance().toString());
		System.out.println("\n\nVECTOR POLACA\n\n" + VectorPolaca.getInstance().toString());
		System.out.println("VECTOR POLACA\n"); VectorPolaca.getInstance().imprimirVector();
	}