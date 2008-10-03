/* Imports */
%{
import compilador.analizadorLexicografico.Automata;
import compilador.beans.TablaDeSimbolos;
import compilador.util.ArchivoReader;
%}

/* Definiciones */
%token ID CTE_NUM CTE_STR OP_SUMA OP_RESTA OP_MUL OP_DIV OP_ASIG AND OR OP_NEGACION OP_IGUAL OP_DISTINTO OP_MAYOR OP_MENOR  OP_MAYOR_IGUAL OP_MENOR_IGUAL PAR_ABRE PAR_CIERRA COR_ABRE COR_CIERRA COMA DOS_PUNTOS PUNTO_Y_COMA BEGIN END STRING FLOAT POINTER DEFVAR ENDDEF IF ELSE ENDIF REPEAT UNTIL TYPE AS DISPLAY AVG

/* Gramatica */
%%
start: programa {$$.sval = $1.sval; System.out.println($$.sval);}
;
programa: def_tipos def_var ejecucion  {$$.sval = $1.sval + " " + $2.sval +  " " + $3.sval; System.out.println($$.sval);}
				| def_var ejecucion {$$.sval = $1.sval + " " + $2.sval; System.out.println($$.sval);}
				| ejecucion {$$.sval = $1.sval; System.out.println($$.sval);}
;
def_tipos: def_tipo {$$.sval = $1.sval; System.out.println($$.sval);}
				 | def_tipos def_tipo {$$.sval = $1.sval + " " + $2.sval; System.out.println($$.sval);}
;
def_tipo: TYPE id AS lista PUNTO_Y_COMA {$$.sval = "TYPE " + $1.sval + " AS " + $2.sval + ";";  System.out.println($$.sval);}
;
lista: lista_num {$$.sval = $1.sval; System.out.println($$.sval);}
		 | lista_str {$$.sval = $1.sval; System.out.println($$.sval);}
;
lista_num: COR_ABRE lis_num_c COR_CIERRA {$$.sval = "[" + $1.sval + "]"; System.out.println($$.sval);}
;
lis_num_c: CTE_NUM {$$.sval = TablaDeSimbolos.getInstance().getPos(yylval.ival); System.out.println($$.sval);}
				 | lis_num_c COMA CTE_NUM {$$.sval = $1.sval + "," + TablaDeSimbolos.getInstance().getPos(yylval.ival); System.out.println($$.sval);}
;
lista_str: COR_ABRE lis_str_c COR_CIERRA {$$.sval = "[" + $1.sval + "]"; System.out.println($$.sval);}
;
lis_str_c: cte_str {$$.sval = $1.sval; System.out.println($$.sval);}
				 | lis_str_c COMA cte_str  {$$.sval = $1.sval + "," + $2.sval; System.out.println($$.sval);}
;
def_var: DEFVAR lista_var ENDDEF {$$.sval = "DEFVAR " + $1.sval + " ENDDEF"; System.out.println($$.sval);}
;
lista_var: lista_ids DOS_PUNTOS tipo PUNTO_Y_COMA {$$.sval = $1.sval + ":" + $2.sval + ";"; System.out.println($$.sval);}
				 | lista_var lista_ids DOS_PUNTOS tipo PUNTO_Y_COMA {$$.sval = $1.sval + "\n" + $2.sval + ":" + $3.sval + ";"; System.out.println($$.sval);}
;
lista_ids: id {$$.sval = $1.sval; System.out.println($$.sval);}
				 | lista_ids COMA id {$$.sval = $1.sval + "," + $2.sval; System.out.println($$.sval);}
;
tipo: FLOAT {$$.sval = "FLOAT"; System.out.println($$.sval);}
		| STRING {$$.sval = "STRING"; System.out.println($$.sval);}
		| POINTER {$$.sval = "POINTER"; System.out.println($$.sval);}
		| id {$$.sval = $1.sval; System.out.println($$.sval);}
;
ejecucion: BEGIN sentencias END {$$.sval = "BEGIN " + $1.sval + " END"; System.out.println($$.sval);}
				 | BEGIN END {$$.sval = "BEGIN " + " END"; System.out.println($$.sval);}
;
sentencias: sentencia {$$.sval = $1.sval; System.out.println($$.sval);}
					| sentencias sentencia {$$.sval = $1.sval + " " + $2.sval; System.out.println($$.sval);}
;
sentencia: asignacion {$$.sval = $1.sval; System.out.println($$.sval);}
				 | condicional {$$.sval = $1.sval; System.out.println($$.sval);}
				 | bucle {$$.sval = $1.sval; System.out.println($$.sval);}
				 | display_command {$$.sval = $1.sval; System.out.println($$.sval);}
;
asignacion: id OP_ASIG expresion PUNTO_Y_COMA {$$.sval = $1.sval + " = " + $2.sval + ";"; System.out.println($$.sval);}
;
expresion: termino
				 | expresion OP_SUMA termino {$$.sval = $1.sval + " + " + $2.sval; System.out.println($$.sval);}
				 | expresion OP_RESTA termino {$$.sval = $1.sval + " - " + $2.sval; System.out.println($$.sval);}
;
termino: factor
			 | termino OP_MUL factor {$$.sval = $1.sval + " * " + $2.sval; System.out.println($$.sval);}
			 | termino OP_DIV factor {$$.sval = $1.sval + " / " + $2.sval; System.out.println($$.sval);}
;
factor: id {$$.sval = $1.sval; System.out.println($$.sval);}
			| cte_num {$$.sval = $1.sval; System.out.println($$.sval);}
			| PAR_ABRE expresion PAR_CIERRA {$$.sval = "(" + $1.sval + ")"; System.out.println($$.sval);}
			| average {$$.sval = $1.sval; System.out.println($$.sval);}
;
condicional: IF PAR_ABRE condicion PAR_CIERRA sentencias ENDIF {$$.sval = "IF(" + $1.sval + ") " + $2.sval + " ENDIF"; System.out.println($$.sval);}
					 | IF PAR_ABRE condicion PAR_CIERRA sentencias ELSE sentencias ENDIF {$$.sval = "IF(" + $1.sval + ") " + $2.sval + " ELSE " + $3.sval + " ENDIF"; System.out.println($$.sval);}
;
condicion: comparacion {$$.sval = $1.sval; System.out.println($$.sval);}
				 | OP_NEGACION comparacion {$$.sval = "!" + $1.sval; System.out.println($$.sval);}
				 | comparacion AND comparacion {$$.sval = $1.sval + "&&" + $2.sval; System.out.println($$.sval);}
				 | comparacion OR comparacion {$$.sval = $1.sval + "||" + $2.sval; System.out.println($$.sval);}
;
comparacion: expresion OP_IGUAL expresion {$$.sval = $1.sval + "==" + $2.sval; System.out.println($$.sval);}
					 | expresion OP_DISTINTO expresion {$$.sval = $1.sval + "!=" + $2.sval; System.out.println($$.sval);}
					 | expresion OP_MAYOR expresion {$$.sval = $1.sval + ">" + $2.sval; System.out.println($$.sval);}
					 | expresion OP_MENOR expresion {$$.sval = $1.sval + "<" + $2.sval; System.out.println($$.sval);}
					 | expresion OP_MAYOR_IGUAL expresion {$$.sval = $1.sval + ">=" + $2.sval; System.out.println($$.sval);}
					 | expresion OP_MENOR_IGUAL expresion  {$$.sval = $1.sval + "<=" + $2.sval; System.out.println($$.sval);}
;
bucle: REPEAT sentencias UNTIL PAR_ABRE condicion PAR_CIERRA PUNTO_Y_COMA {$$.sval = "REPEAT " + $1.sval + " UNTIL(" + $2.sval + ");"; System.out.println($$.sval);}
;
display_command: DISPLAY PAR_ABRE cte_str PAR_CIERRA PUNTO_Y_COMA {$$.sval = "DISPLAY(" + $1.sval + ");"; System.out.println($$.sval);}
;
average: AVG PAR_ABRE lista_num PAR_CIERRA {$$.sval = "AVG(" + $1.sval + ")"; System.out.println($$.sval);}
;
id: ID {$$.sval = new String(TablaDeSimbolos.getInstance().getPos(yylval.ival)); System.out.println($$.sval);}
;
cte_num: CTE_NUM {$$.sval = new String(TablaDeSimbolos.getInstance().getPos(yylval.ival)); System.out.println($$.sval);}
;
cte_str: CTE_STR {$$.sval = new String("\"" + TablaDeSimbolos.getInstance().getPos(yylval.ival) + "\""); System.out.println($$.sval);}
;

%%
	void yyerror(String mensaje) {
		System.out.println("Error: " + mensaje);
	}
	public static final int ESTADO_INICIAL = 0;
	public static final int ESTADO_FINAL = 36;

		//Manejo de errores lexicos
		public static int INCOMPLETO=0;
		public static int ERROR_LEXICO=-1;

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
		//System.out.println(TablaDeSimbolos.getInstance().toString());
	}