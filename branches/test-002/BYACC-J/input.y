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
def_tipos: def_tipo 
         | def_tipos def_tipo					
;
def_tipo: TYPE ID AS lista PUNTO_Y_COMA
;
lista: lista_num
     | lista_str
;                
lista_num: COR_ABRE lis_num_c COR_CIERRA
;
lis_num_c: CTE_NUM
         | lis_num_c COMA CTE_NUM
;                
lista_str: COR_ABRE lis_str_c COR_CIERRA
;
lis_str_c: CTE_STR
         | lis_str_c COMA CTE_STR
;                
def_var: DEFVAR lista_var ENDDEF
;
lista_var: lista_ids DOS_PUNTOS tipo PUNTO_Y_COMA
         | lista_var lista_ids DOS_PUNTOS tipo PUNTO_Y_COMA 
;
lista_ids: ID
         | lista_ids COMA ID
;                
tipo: FLOAT
    | STRING
    | POINTER
    | ID
;                
ejecucion: BEGIN sentencias END
         | BEGIN END
;                
sentencias: sentencia
          | sentencias sentencia
;                
sentencia: asignacion {System.out.println("=");}							
         | condicional							
         | bucle								
         | display_command						
;                
asignacion: ID OP_ASIG expresion PUNTO_Y_COMA
;
expresion: termino
         | expresion OP_SUMA termino {System.out.println("+");}
         | expresion OP_RESTA termino {System.out.println("-");}
;                
termino: factor
       | termino OP_MUL factor {System.out.println("*");}
       | termino OP_DIV factor {System.out.println("/");}
;                
factor: ID {System.out.println(TablaDeSimbolos.getInstance().getPos(yylval.ival));}
      | CTE_NUM {System.out.println(TablaDeSimbolos.getInstance().getPos(yylval.ival));}
      | PAR_ABRE expresion PAR_CIERRA
      | average {System.out.println("AVG()");}
;                
condicional: IF PAR_ABRE condicion PAR_CIERRA sentencias ENDIF 
           | IF PAR_ABRE condicion PAR_CIERRA sentencias ELSE sentencias ENDIF
;                 
condicion: comparacion
         | OP_NEGACION comparacion {System.out.println("!");}
         | comparacion AND comparacion {System.out.println("&&");}
         | comparacion OR comparacion {System.out.println("||");}
;                
comparacion: expresion OP_IGUAL expresion {System.out.println("==");}
           | expresion OP_DISTINTO expresion {System.out.println("!=");}
           | expresion OP_MAYOR expresion {System.out.println(">");}
           | expresion OP_MENOR expresion {System.out.println("<");}
           | expresion OP_MAYOR_IGUAL expresion {System.out.println(">=");}
           | expresion OP_MENOR_IGUAL expresion  {System.out.println("<=");}
;
bucle: REPEAT sentencias UNTIL PAR_ABRE condicion PAR_CIERRA PUNTO_Y_COMA
;
display_command: DISPLAY PAR_ABRE CTE_STR PAR_CIERRA PUNTO_Y_COMA
;
average: AVG PAR_ABRE lista_num PAR_CIERRA
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