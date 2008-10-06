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
start: programa {$$ = new ParserVal($1.sval); System.out.println("Regla 01 (Programa Completo)\n" + $$.sval + "\nCompila OK!");}
;
programa: def_tipos def_var ejecucion  {$$ = new ParserVal($1.sval + "\n" + $2.sval +  "\n" + $3.sval); System.out.println("Regla 02\n" + $$.sval + "\n");}
        | def_var ejecucion {$$ = new ParserVal($1.sval + "\n" + $2.sval); System.out.println("Regla 03\n" + $$.sval + "\n");}
        | ejecucion {$$ = new ParserVal($1.sval); System.out.println("Regla 04\n" + $$.sval + "\n");}
;
def_tipos: def_tipo {$$ = new ParserVal($1.sval); System.out.println("Regla 05\n" + $$.sval + "\n");}
         | def_tipos def_tipo {$$ = new ParserVal($1.sval + "\n" + $2.sval); System.out.println("Regla 06\n" + $$.sval + "\n");}
;
def_tipo: TYPE id AS lista PUNTO_Y_COMA {$$ = new ParserVal("TYPE " + $2.sval + " AS " + $4.sval + ";");  System.out.println("Regla 07\n" + $$.sval + "\n");}
;
lista: lista_num {$$ = new ParserVal($1.sval); System.out.println("Regla 08\n" + $$.sval + "\n");}
     | lista_str {$$ = new ParserVal($1.sval); System.out.println("Regla 09\n" + $$.sval + "\n");}
;
lista_num: COR_ABRE lis_num_c COR_CIERRA {$$ = new ParserVal("[" + $2.sval + "]"); System.out.println("Regla 10\n" + $$.sval + "\n");}
;
lis_num_c: cte_num {$$ = new ParserVal($1.sval); System.out.println("Regla 11\n" + $$.sval + "\n");}
         | lis_num_c COMA cte_num {$$ = new ParserVal($1.sval + "," + $3.sval); System.out.println("Regla 12\n" + $$.sval + "\n");}
;
lista_str: COR_ABRE lis_str_c COR_CIERRA {$$ = new ParserVal("[" + $2.sval + "]"); System.out.println("Regla 13\n" + $$.sval + "\n");}
;
lis_str_c: cte_str {$$ = new ParserVal($1.sval); System.out.println("Regla 14\n" + $$.sval + "\n");}
         | lis_str_c COMA cte_str {$$ = new ParserVal($1.sval + "," + $3.sval); System.out.println("Regla 15\n" + $$.sval + "\n");}
;
def_var: DEFVAR lista_var ENDDEF {$$ = new ParserVal("DEFVAR\n" + $2.sval + "\nENDDEF"); System.out.println("Regla 16\n" + $$.sval + "\n");}
;
lista_var: lista_ids DOS_PUNTOS tipo PUNTO_Y_COMA {$$ = new ParserVal($1.sval + ":" + $3.sval + ";"); System.out.println("Regla 17\n" + $$.sval + "\n");}
         | lista_var lista_ids DOS_PUNTOS tipo PUNTO_Y_COMA {$$ = new ParserVal($1.sval + "\n" + $2.sval + ":" + $4.sval + ";"); System.out.println("Regla 18\n" + $$.sval + "\n");}
;
lista_ids: id {$$ = new ParserVal($1.sval); System.out.println("Regla 19\n" + $$.sval + "\n");}
         | lista_ids COMA id {$$ = new ParserVal($1.sval + "," + $3.sval); System.out.println("Regla 20\n" + $$.sval + "\n");}
;
tipo: FLOAT {$$ = new ParserVal("FLOAT"); System.out.println("Regla 21\n" + $$.sval + "\n");}
    | STRING {$$ = new ParserVal("STRING"); System.out.println("Regla 22\n" + $$.sval + "\n");}
    | POINTER {$$ = new ParserVal("POINTER"); System.out.println("Regla 23\n" + $$.sval + "\n");}
    | id {$$ = new ParserVal($1.sval); System.out.println("Regla 24\n" + $$.sval + "\n");}
;
ejecucion: BEGIN sentencias END {$$ = new ParserVal("BEGIN\n" + $2.sval + "\nEND"); System.out.println("Regla 25\n" + $$.sval + "\n");}
         | BEGIN END {$$ = new ParserVal("BEGIN\nEND"); System.out.println("Regla 26\n" + $$.sval + "\n");}
;
sentencias: sentencia {$$ = new ParserVal($1.sval); System.out.println("Regla 27\n" + $$.sval + "\n");}
          | sentencias sentencia {$$ = new ParserVal($1.sval + "\n" + $2.sval); System.out.println("Regla 28\n" + $$.sval + "\n");}
;
sentencia: asignacion {$$ = new ParserVal($1.sval); System.out.println("Regla 29\n" + $$.sval + "\n");}
         | condicional {$$ = new ParserVal($1.sval); System.out.println("Regla 30\n" + $$.sval + "\n");}
         | bucle {$$ = new ParserVal($1.sval); System.out.println("Regla 31\n" + $$.sval + "\n");}
         | display_command {$$ = new ParserVal($1.sval); System.out.println("Regla 32\n" + $$.sval + "\n");}
;
asignacion: id OP_ASIG expresion PUNTO_Y_COMA {$$ = new ParserVal($1.sval + " = " + $3.sval + ";"); System.out.println("Regla 33\n" + $$.sval + "\n");}
;
expresion: termino {$$ = new ParserVal($1.sval);}
         | expresion OP_SUMA termino {$$ = new ParserVal($1.sval + " + " + $3.sval); System.out.println("Regla 34\n" + $$.sval + "\n");}
         | expresion OP_RESTA termino {$$ = new ParserVal($1.sval + " - " + $3.sval); System.out.println("Regla 35\n" + $$.sval + "\n");}
;
termino: factor {$$ = new ParserVal($1.sval);}
       | termino OP_MUL factor {$$ = new ParserVal($1.sval + " * " + $3.sval); System.out.println("Regla 36\n" + $$.sval + "\n");}
       | termino OP_DIV factor {$$ = new ParserVal($1.sval + " / " + $3.sval); System.out.println("Regla 37\n" + $$.sval + "\n");}
;
factor: id {$$ = new ParserVal($1.sval); System.out.println("Regla 38\n" + $$.sval + "\n");}
      | cte_num {$$ = new ParserVal($1.sval); System.out.println("Regla 39\n" + $$.sval + "\n");}
      | PAR_ABRE expresion PAR_CIERRA {$$ = new ParserVal("(" + $2.sval + ")"); System.out.println("Regla 40\n" + $$.sval + "\n");}
      | average {$$ = new ParserVal($1.sval); System.out.println("Regla 41\n" + $$.sval + "\n");}
;
condicional: IF PAR_ABRE condicion PAR_CIERRA sentencias ENDIF {$$ = new ParserVal("IF(" + $3.sval + ")\n" + $4.sval + "\nENDIF"); System.out.println("Regla 42\n" + $$.sval + "\n");}
           | IF PAR_ABRE condicion PAR_CIERRA sentencias ELSE sentencias ENDIF {$$ = new ParserVal("IF(" + $3.sval + ")\n" + $5.sval + "\nELSE\n" + $7.sval + "\nENDIF"); System.out.println("Regla 43\n" + $$.sval + "\n");}
;
condicion: comparacion {$$ = new ParserVal($1.sval); System.out.println("Regla 44\n" + $$.sval + "\n");}
         | OP_NEGACION PAR_ABRE comparacion PAR_CIERRA {$$ = new ParserVal("!(" + $3.sval + ")"); System.out.println("Regla 45\n" + $$.sval + "\n");}
         | comparacion AND comparacion {$$ = new ParserVal($1.sval + "&&" + $3.sval); System.out.println("Regla 46\n" + $$.sval + "\n");}
         | comparacion OR comparacion {$$ = new ParserVal($1.sval + "||" + $3.sval); System.out.println("Regla 47\n" + $$.sval + "\n");}
;
comparacion: expresion OP_IGUAL expresion {$$ = new ParserVal($1.sval + "==" + $3.sval); System.out.println("Regla 48\n" + $$.sval + "\n");}
           | expresion OP_DISTINTO expresion {$$ = new ParserVal($1.sval + "!=" + $3.sval); System.out.println("Regla 49\n" + $$.sval + "\n");}
           | expresion OP_MAYOR expresion {$$ = new ParserVal($1.sval + ">" + $3.sval); System.out.println("Regla 50\n" + $$.sval + "\n");}
           | expresion OP_MENOR expresion {$$ = new ParserVal($1.sval + "<" + $3.sval); System.out.println("Regla 51\n" + $$.sval + "\n");}
           | expresion OP_MAYOR_IGUAL expresion {$$ = new ParserVal($1.sval + ">=" + $3.sval); System.out.println("Regla 52\n" + $$.sval + "\n");}
           | expresion OP_MENOR_IGUAL expresion  {$$ = new ParserVal($1.sval + "<=" + $3.sval); System.out.println("Regla 53\n" + $$.sval + "\n");}
;
bucle: REPEAT sentencias UNTIL PAR_ABRE condicion PAR_CIERRA PUNTO_Y_COMA {$$ = new ParserVal("REPEAT\n" + $2.sval + "\nUNTIL(" + $5.sval + ");"); System.out.println("Regla 54\n" + $$.sval + "\n");}
;
display_command: DISPLAY PAR_ABRE cte_str PAR_CIERRA PUNTO_Y_COMA {$$ = new ParserVal("DISPLAY(" + $3.sval + ");"); System.out.println("Regla 55\n" + $$.sval + "\n");}
;
average: AVG PAR_ABRE lista_num PAR_CIERRA {$$ = new ParserVal("AVG(" + $3.sval + ")"); System.out.println("Regla 56\n" + $$.sval + "\n");}
;
id: ID {$$ = new ParserVal(TablaDeSimbolos.getInstance().getPos(yylval.ival)); System.out.println("Regla 57\n" + $$.sval + "\n");}
;
cte_num: CTE_NUM {$$ = new ParserVal(TablaDeSimbolos.getInstance().getPos(yylval.ival)); System.out.println("Regla 58\n" + $$.sval + "\n");}
;
cte_str: CTE_STR {$$ = new ParserVal("\"" + TablaDeSimbolos.getInstance().getPos(yylval.ival) + "\""); System.out.println("Regla 59\n" + $$.sval + "\n");}
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