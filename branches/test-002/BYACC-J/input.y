/* Imports */
%{
import compilador.analizadorLexicografico.Automata;
import compilador.beans.TablaDeSimbolos;
import compilador.util.ArchivoReader;
%}

/* Definiciones */
%token ID CTE_NUM CTE_STR OP_SUMA OP_RESTA OP_MUL OP_DIV OP_ASIG AND OR OP_NEGACION OP_IGUAL OP_DISTINTO OP_MAYOR OP_MENOR	OP_MAYOR_IGUAL OP_MENOR_IGUAL PAR_ABRE PAR_CIERRA COR_ABRE COR_CIERRA COMA DOS_PUNTOS PUNTO_Y_COMA BEGIN END STRING FLOAT POINTER DEFVAR ENDDEF IF ELSE ENDIF REPEAT UNTIL TYPE AS DISPLAY AVG

/* Gramatica */
%%
start: programa {System.out.println("Compila OK!");}
;
programa: def_tipos def_var ejecucion
        | def_var ejecucion
        | ejecucion
;                
def_tipos: def_tipo {System.out.println($$.sval);}
         | def_tipos def_tipo {System.out.println($$.sval);}
;
def_tipo: TYPE ID AS lista PUNTO_Y_COMA {$$.sval = "TYPE " + "XXXXXXXXXX" + " AS " + $1.sval + ";"; }
;
lista: lista_num {$$.sval = $1.sval;}
     | lista_str {$$.sval = $1.sval;}
;                
lista_num: COR_ABRE lis_num_c COR_CIERRA {$$.sval = "[" + $1.sval + "]";}
;
lis_num_c: CTE_NUM {$$.sval = TablaDeSimbolos.getInstance().getPos(yylval.ival);}
         | lis_num_c COMA CTE_NUM {$$.sval = $1.sval + "," + TablaDeSimbolos.getInstance().getPos(yylval.ival);}
;                
lista_str: COR_ABRE lis_str_c COR_CIERRA {$$.sval = "[" + $1.sval + "]";}
;
lis_str_c: cte_str {$$.sval = $1.sval;}
         | lis_str_c COMA cte_str  {$$.sval = $1.sval + "," + $2.sval;}
;                
def_var: DEFVAR lista_var ENDDEF
;
lista_var: lista_ids DOS_PUNTOS tipo PUNTO_Y_COMA {$$.sval = $1.sval + ":" + $2.sval + ";";}
         | lista_var lista_ids DOS_PUNTOS tipo PUNTO_Y_COMA {$$.sval = $1.sval + "\n" + $2.sval + ":" + $3.sval + ";";}
;
lista_ids: id {$$.sval = $1.sval;}
         | lista_ids COMA id {$$.sval = $1.sval + "," + $2.sval;}
;                
tipo: FLOAT {$$.sval = "FLOAT";}
    | STRING {$$.sval = "STRING";}
    | POINTER {$$.sval = "POINTER";}
    | id {$$.sval = $1.sval;}
;                
ejecucion: BEGIN sentencias END {$$.sval = "BEGIN " + $1.sval + " END";}
         | BEGIN END {$$.sval = "BEGIN " + " END";}
;                
sentencias: sentencia 
          | sentencias sentencia
;                
sentencia: asignacion {$$.sval = $1.sval;}							
         | condicional							
         | bucle								
         | display_command						
;                
asignacion: id OP_ASIG expresion PUNTO_Y_COMA {$$.sval = $1.sval + " = " + $2.sval + ";";}
;
expresion: termino
         | expresion OP_SUMA termino {$$.sval = $1.sval + " + " + $2.sval;}
         | expresion OP_RESTA termino {$$.sval = $1.sval + " - " + $2.sval;}
;                
termino: factor
       | termino OP_MUL factor {$$.sval = $1.sval + " * " + $2.sval;}
       | termino OP_DIV factor {$$.sval = $1.sval + " / " + $2.sval;}
;                
factor: id {$$.sval = $1.sval;}
      | cte_num {$$.sval = $1.sval;}
      | PAR_ABRE expresion PAR_CIERRA {$$.sval = "(" + $1.sval + ")";}
      | average {$$.sval = $1.sval;}
;                
condicional: IF PAR_ABRE condicion PAR_CIERRA sentencias ENDIF 
           | IF PAR_ABRE condicion PAR_CIERRA sentencias ELSE sentencias ENDIF
;                 
condicion: comparacion
         | OP_NEGACION comparacion {$1.sval = "!";}
         | comparacion AND comparacion {$2.sval = "&&";}
         | comparacion OR comparacion {$2.sval = "||";}
;                
comparacion: expresion OP_IGUAL expresion {$2.sval = "==";}
           | expresion OP_DISTINTO expresion {$2.sval = "!=";}
           | expresion OP_MAYOR expresion {$2.sval = ">";}
           | expresion OP_MENOR expresion {$2.sval = "<";}
           | expresion OP_MAYOR_IGUAL expresion {$2.sval = ">=";}
           | expresion OP_MENOR_IGUAL expresion  {$2.sval = "<=";}
;
bucle: REPEAT sentencias UNTIL PAR_ABRE condicion PAR_CIERRA PUNTO_Y_COMA
;
display_command: DISPLAY PAR_ABRE CTE_STR PAR_CIERRA PUNTO_Y_COMA
;
average: AVG PAR_ABRE lista_num PAR_CIERRA
;
id: ID ID {$$.sval = TablaDeSimbolos.getInstance().getPos(yylval.ival);}
;
cte_num: CTE_NUM ID {$$.sval = TablaDeSimbolos.getInstance().getPos(yylval.ival);}
;
cte_str: CTE_STR {$$.sval = "\"" + TablaDeSimbolos.getInstance().getPos(yylval.ival) + "\"";}
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