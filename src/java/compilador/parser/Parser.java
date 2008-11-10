//### This file created by BYACC 1.8(/Java extension  1.14)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";



package compilador.parser;



//#line 3 "input.y"
import compilador.analizadorLexicografico.Automata;
import compilador.beans.TablaDeSimbolos;
import compilador.util.ArchivoReader;
import compilador.util.ArchivoWriter;
import compilador.sintaxis.VectorPolaca;
import compilador.sintaxis.EntradaVectorPolaca;
import compilador.sintaxis.StackAuxiliarPolaca;
import java.util.ArrayList;
import java.util.Collection;
import java.lang.String;
//#line 28 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
//#### end semantic value section ####
public final static short ID=257;
public final static short CTE_NUM=258;
public final static short CTE_STR=259;
public final static short OP_SUMA=260;
public final static short OP_RESTA=261;
public final static short OP_MUL=262;
public final static short OP_DIV=263;
public final static short OP_ASIG=264;
public final static short AND=265;
public final static short OR=266;
public final static short OP_NEGACION=267;
public final static short OP_IGUAL=268;
public final static short OP_DISTINTO=269;
public final static short OP_MAYOR=270;
public final static short OP_MENOR=271;
public final static short OP_MAYOR_IGUAL=272;
public final static short OP_MENOR_IGUAL=273;
public final static short PAR_ABRE=274;
public final static short PAR_CIERRA=275;
public final static short COR_ABRE=276;
public final static short COR_CIERRA=277;
public final static short COMA=278;
public final static short DOS_PUNTOS=279;
public final static short PUNTO_Y_COMA=280;
public final static short BEGIN=281;
public final static short END=282;
public final static short STRING=283;
public final static short FLOAT=284;
public final static short POINTER=285;
public final static short DEFVAR=286;
public final static short ENDDEF=287;
public final static short IF=288;
public final static short ELSE=289;
public final static short ENDIF=290;
public final static short REPEAT=291;
public final static short UNTIL=292;
public final static short TYPE=293;
public final static short AS=294;
public final static short DISPLAY=295;
public final static short AVG=296;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    1,    2,    2,    5,    7,    7,    8,
   10,   10,    9,   12,   12,    3,   14,   14,   15,   15,
   16,   16,   16,   16,    4,    4,   17,   17,   18,   18,
   18,   18,   19,   19,   23,   23,   23,   24,   24,   24,
   25,   25,   25,   25,   29,   20,   28,   30,   28,   27,
   27,   27,   27,   31,   31,   31,   31,   31,   31,   32,
   21,   22,   26,    6,   11,   13,
};
final static short yylen[] = {                            2,
    1,    3,    2,    1,    1,    2,    5,    1,    1,    3,
    1,    3,    3,    1,    3,    3,    4,    5,    1,    3,
    1,    1,    1,    1,    3,    2,    1,    2,    1,    1,
    1,    1,    4,    4,    1,    3,    3,    1,    3,    3,
    1,    1,    3,    1,    0,    7,    1,    0,    4,    1,
    4,    3,    3,    3,    3,    3,    3,    3,    3,    0,
    8,    5,    4,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    1,    0,    0,    4,    5,   64,
   26,    0,   60,    0,    0,    0,   27,   29,   30,   31,
   32,   19,    0,    0,    0,    0,    6,    3,    0,    0,
    0,    0,   25,   28,   16,    0,    0,    0,    0,    2,
   65,    0,    0,    0,   41,   42,    0,    0,   38,   44,
    0,    0,    0,   66,    0,    0,    0,    0,   20,   22,
   21,   23,   24,    0,    0,    0,    8,    9,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   45,    0,    0,    0,    0,   34,   33,    0,   17,
    0,   11,    0,   14,    7,    0,   43,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   39,   40,    0,
   52,   53,    0,   62,   18,   10,    0,   13,    0,   51,
   63,    0,    0,    0,   12,   15,    0,   46,    0,    0,
   61,    0,
};
final static short yydgoto[] = {                          4,
    5,    6,    7,    8,    9,   45,   66,   67,   68,   91,
   46,   93,   55,   23,   24,   64,   16,   17,   18,   19,
   20,   21,   47,   48,   49,   50,   51,  123,  110,  127,
   52,   30,
};
final static short yysindex[] = {                      -255,
 -237, -229, -229,    0,    0, -272, -235,    0,    0,    0,
    0, -240,    0, -224, -211, -215,    0,    0,    0,    0,
    0,    0, -252, -184, -238, -235,    0,    0, -249, -209,
 -199, -247,    0,    0,    0, -171, -229, -213, -201,    0,
    0, -185, -241, -182,    0,    0, -156, -239,    0,    0,
 -177, -147, -214,    0, -164, -154, -192, -213,    0,    0,
    0,    0,    0, -150, -136, -139,    0,    0, -241, -176,
 -126, -241, -241, -241, -241, -241, -241, -241, -241, -241,
 -241,    0, -241, -241, -108, -123,    0,    0, -107,    0,
 -132,    0, -116,    0,    0,  -91,    0,  -72,  -80, -239,
 -239,  -78,  -78,  -78,  -78,  -78,  -78,    0,    0, -209,
    0,    0, -249,    0,    0,    0,  -72,    0, -199,    0,
    0, -209,  -94,  -77,    0,    0,  -92,    0,  -81, -209,
    0, -209,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0, -133,    0,    0,
    0,  -75,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0, -117,
 -101, -175, -169, -141,  -90,  -88,  -85,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -98,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -89,
};
final static short yygindex[] = {                         0,
    0,    0,  196,    6,  197,   -1,    0,  133,    0,    0,
  -58,    0,  -26,    0,  182,  148,  -27,  -12,    0,    0,
    0,    0,  -13,  116,  113,    0,   94,    0,    0,    0,
   18,    0,
};
final static int YYTABLESIZE=207;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         15,
   22,   25,   53,   34,   10,   56,   92,   10,   41,   10,
   41,   54,   28,    2,   15,   10,   41,   42,   57,   10,
    3,   22,   80,   81,   43,    1,   43,   10,   15,   70,
    2,   40,   43,   29,   35,   59,   63,    3,   94,   92,
   34,   10,   10,   10,   11,    1,   44,   10,   44,   31,
   12,   15,   32,   13,   44,   39,   63,   14,  125,   54,
  102,  103,  104,  105,  106,  107,   33,   72,   73,   60,
   61,   62,   12,   12,   65,   13,   13,   85,   12,   14,
   14,   13,  122,   72,   73,   14,   96,   88,   69,   54,
   54,   71,  126,   37,   38,   55,   55,   82,   97,   54,
  111,  112,  132,   72,   73,   55,   37,   58,   15,   34,
   86,   74,   75,   76,   77,   78,   79,   83,   84,   34,
   15,   41,   54,   56,   56,   87,   35,   35,   15,   90,
   15,   35,   35,   56,   35,   35,   35,   35,   35,   35,
   95,   35,   36,   36,  116,  117,   35,   36,   36,   98,
   36,   36,   36,   36,   36,   36,  114,   36,   37,   37,
  118,  119,   36,   37,   37,  113,   37,   37,   37,   37,
   37,   37,  115,   37,   57,   57,   58,   58,   37,   59,
   59,   72,   73,  120,   57,   41,   58,  100,  101,   59,
   48,   47,  108,  109,  121,  128,  130,  129,  131,   50,
   49,   26,   27,   99,   36,   89,  124,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          1,
    2,    3,   30,   16,  257,   32,   65,  257,  258,  257,
  258,  259,    7,  286,   16,  257,  258,  267,   32,  257,
  293,   23,  262,  263,  274,  281,  274,  257,   30,   43,
  286,   26,  274,  274,  287,   37,   38,  293,   65,   98,
   53,  257,  257,  257,  282,  281,  296,  257,  296,  274,
  288,   53,  264,  291,  296,  294,   58,  295,  117,  259,
   74,   75,   76,   77,   78,   79,  282,  260,  261,  283,
  284,  285,  288,  288,  276,  291,  291,  292,  288,  295,
  295,  291,  110,  260,  261,  295,   69,  280,  274,  265,
  266,  274,  119,  278,  279,  265,  266,  275,  275,  275,
   83,   84,  130,  260,  261,  275,  278,  279,  110,  122,
  275,  268,  269,  270,  271,  272,  273,  265,  266,  132,
  122,  258,  259,  265,  266,  280,  260,  261,  130,  280,
  132,  265,  266,  275,  268,  269,  270,  271,  272,  273,
  280,  275,  260,  261,  277,  278,  280,  265,  266,  276,
  268,  269,  270,  271,  272,  273,  280,  275,  260,  261,
  277,  278,  280,  265,  266,  274,  268,  269,  270,  271,
  272,  273,  280,  275,  265,  266,  265,  266,  280,  265,
  266,  260,  261,  275,  275,  258,  275,   72,   73,  275,
  289,  290,   80,   81,  275,  290,  289,  275,  280,  275,
  290,    6,    6,   71,   23,   58,  113,
};
}
final static short YYFINAL=4;
final static short YYMAXTOKEN=296;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"ID","CTE_NUM","CTE_STR","OP_SUMA","OP_RESTA","OP_MUL","OP_DIV",
"OP_ASIG","AND","OR","OP_NEGACION","OP_IGUAL","OP_DISTINTO","OP_MAYOR",
"OP_MENOR","OP_MAYOR_IGUAL","OP_MENOR_IGUAL","PAR_ABRE","PAR_CIERRA","COR_ABRE",
"COR_CIERRA","COMA","DOS_PUNTOS","PUNTO_Y_COMA","BEGIN","END","STRING","FLOAT",
"POINTER","DEFVAR","ENDDEF","IF","ELSE","ENDIF","REPEAT","UNTIL","TYPE","AS",
"DISPLAY","AVG",
};
final static String yyrule[] = {
"$accept : start",
"start : programa",
"programa : def_tipos def_var ejecucion",
"programa : def_var ejecucion",
"programa : ejecucion",
"def_tipos : def_tipo",
"def_tipos : def_tipos def_tipo",
"def_tipo : TYPE id AS lista PUNTO_Y_COMA",
"lista : lista_num",
"lista : lista_str",
"lista_num : COR_ABRE lis_num_c COR_CIERRA",
"lis_num_c : cte_num",
"lis_num_c : lis_num_c COMA cte_num",
"lista_str : COR_ABRE lis_str_c COR_CIERRA",
"lis_str_c : cte_str",
"lis_str_c : lis_str_c COMA cte_str",
"def_var : DEFVAR lista_var ENDDEF",
"lista_var : lista_ids DOS_PUNTOS tipo PUNTO_Y_COMA",
"lista_var : lista_var lista_ids DOS_PUNTOS tipo PUNTO_Y_COMA",
"lista_ids : id",
"lista_ids : lista_ids COMA id",
"tipo : FLOAT",
"tipo : STRING",
"tipo : POINTER",
"tipo : id",
"ejecucion : BEGIN sentencias END",
"ejecucion : BEGIN END",
"sentencias : sentencia",
"sentencias : sentencias sentencia",
"sentencia : asignacion",
"sentencia : condicional",
"sentencia : bucle",
"sentencia : display_command",
"asignacion : id OP_ASIG expresion PUNTO_Y_COMA",
"asignacion : id OP_ASIG cte_str PUNTO_Y_COMA",
"expresion : termino",
"expresion : expresion OP_SUMA termino",
"expresion : expresion OP_RESTA termino",
"termino : factor",
"termino : termino OP_MUL factor",
"termino : termino OP_DIV factor",
"factor : id",
"factor : cte_num",
"factor : PAR_ABRE expresion PAR_CIERRA",
"factor : average",
"$$1 :",
"condicional : IF PAR_ABRE condicion PAR_CIERRA $$1 sentenciasCondicional ENDIF",
"sentenciasCondicional : sentencias",
"$$2 :",
"sentenciasCondicional : sentencias $$2 ELSE sentencias",
"condicion : comparacion",
"condicion : OP_NEGACION PAR_ABRE comparacion PAR_CIERRA",
"condicion : comparacion AND comparacion",
"condicion : comparacion OR comparacion",
"comparacion : expresion OP_IGUAL expresion",
"comparacion : expresion OP_DISTINTO expresion",
"comparacion : expresion OP_MAYOR expresion",
"comparacion : expresion OP_MENOR expresion",
"comparacion : expresion OP_MAYOR_IGUAL expresion",
"comparacion : expresion OP_MENOR_IGUAL expresion",
"$$3 :",
"bucle : REPEAT $$3 sentencias UNTIL PAR_ABRE condicion PAR_CIERRA PUNTO_Y_COMA",
"display_command : DISPLAY PAR_ABRE cte_str PAR_CIERRA PUNTO_Y_COMA",
"average : AVG PAR_ABRE lista_num PAR_CIERRA",
"id : ID",
"cte_num : CTE_NUM",
"cte_str : CTE_STR",
};

//#line 139 "input.y"
	public static final int ESTADO_INICIAL = 0;
	public static final int ESTADO_FINAL = 36;

	//Manejo de errores lexicos
	public static int INCOMPLETO=0;
	public static int ERROR_LEXICO=YYERRCODE;

	public static TablaDeSimbolos TS = TablaDeSimbolos.getInstance();
	public static VectorPolaca vector = VectorPolaca.getInstance();
	public static StackAuxiliarPolaca stack = StackAuxiliarPolaca.getInstance();
	
	public static ArrayList<String> listaAux = null;

	//Esta lista ya est� instanciada y cada elemento que se agrega es luego tomado por la regla correspondiente, por ende nunca contiene basura
	public static ArrayList<EntradaVectorPolaca> listaAuxPolaca = new ArrayList<EntradaVectorPolaca>();

	//lo habilitamos o deshabilitamos para debuggear
	public void imprimir(String valor){
		//System.out.println(valor);
	}
	
	int yylex() {
		Automata automata = Automata.getInstance();
		return automata.getToken(yylval);
	}

	void yyerror(String mensaje) {
		System.err.println("Error en Parser: " + mensaje);
	}

	public static void main(String args[]) {
		Parser par = new Parser();
		
		ArchivoReader archivo = ArchivoReader.getInstance();
		archivo.abrirArhivo(args[0] + ".x");
		par.yyparse();
		archivo.cerrarArhivo();

		if(par.yyerrflag != 0) {
			System.err.println("Se ha producido un error en el Parser. Abortando compilaci�n...");
			System.exit(-1);
		}
		
		//Creamos un archivo con la salida de la Tabla de S�mbolos y el Vector Polaca
		ArchivoWriter archivoIntermedio = new ArchivoWriter(args[0] + ".polaca");
		archivoIntermedio.write("TABLA DE SIMBOLOS\n\n" + TS.toString());
		archivoIntermedio.write("\n\nVECTOR POLACA\n\n" + vector.toString());
		archivoIntermedio.cerrarArhivo();
		
		if(!TablaDeSimbolos.abortarCompilacion) {
			//Creamos un archivo con la salida en assembler
			ArchivoWriter archivoAssembler = new ArchivoWriter(args[0] + ".asm");
			archivoAssembler.write(vector.toASM());
			archivoAssembler.cerrarArhivo();

		} else {
			System.err.println("\n\nSe ha producido un error en la generaci�n de c�digo intermedio. Abortando compilaci�n...");
			System.exit(-1);
		}
		
		//System.out.println("\n\nTABLA DE SIMBOLOS\n\n" + TS.toString());
		//System.out.println("\n\nVECTOR POLACA\n\n" + vector.toString());
		//System.out.println("\n\nSALIDA ASSEMBLER\n\n" + vector.toASM());
	}
//#line 432 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 20 "input.y"
{yyval = new ParserVal(val_peek(0).sval); imprimir("Regla 01 (Programa Completo)\n" + yyval.sval);}
break;
case 2:
//#line 22 "input.y"
{yyval = new ParserVal(val_peek(2).sval + "\n" + val_peek(1).sval +  "\n" + val_peek(0).sval); imprimir("Regla 02\n" + yyval.sval + "\n");}
break;
case 3:
//#line 23 "input.y"
{yyval = new ParserVal(val_peek(1).sval + "\n" + val_peek(0).sval); imprimir("Regla 03\n" + yyval.sval + "\n");}
break;
case 4:
//#line 24 "input.y"
{yyval = new ParserVal(val_peek(0).sval); imprimir("Regla 04\n" + yyval.sval + "\n");}
break;
case 5:
//#line 26 "input.y"
{yyval = new ParserVal(val_peek(0).sval); imprimir("Regla 05\n" + yyval.sval + "\n");}
break;
case 6:
//#line 27 "input.y"
{yyval = new ParserVal(val_peek(1).sval + "\n" + val_peek(0).sval); imprimir("Regla 06\n" + yyval.sval + "\n");}
break;
case 7:
//#line 29 "input.y"
{yyval = new ParserVal("TYPE " + val_peek(3).sval + " AS " + val_peek(1).sval + ";"); TS.crearNuevoTipo(val_peek(3).sval, val_peek(1).ival); TS.setTypedefs(listaAux,val_peek(3).sval) /* Tomamos la Tabla de s�mbolos y en el campo TYPEDEF de las variables recibidas en la lista, le seteamos el tipo creado (verificando que no exista ya) */; imprimir("Regla 07\n" + yyval.sval + "\n");}
break;
case 8:
//#line 31 "input.y"
{yyval = new ParserVal(val_peek(0).sval); yyval.ival = CTE_NUM; imprimir("Regla 08\n" + yyval.sval + "\n");}
break;
case 9:
//#line 32 "input.y"
{yyval = new ParserVal(val_peek(0).sval); yyval.ival = CTE_STR; imprimir("Regla 09\n" + yyval.sval + "\n");}
break;
case 10:
//#line 34 "input.y"
{yyval = new ParserVal("[" + val_peek(1).sval + "]"); imprimir("Regla 10\n" + yyval.sval + "\n");}
break;
case 11:
//#line 36 "input.y"
{yyval = new ParserVal(val_peek(0).sval); listaAux = new ArrayList<String>(); listaAux.add(val_peek(0).sval) /* Vamos agregando los valores en una lista para usarlos mas arriba */; imprimir("Regla 11\n" + yyval.sval + "\n");}
break;
case 12:
//#line 37 "input.y"
{yyval = new ParserVal(val_peek(2).sval + "," + val_peek(0).sval); listaAux.add(val_peek(0).sval) /*Esta regla siempre se ejecuta despues que la de arriba, por eso el ArrayList ya fue instanciado con new */; imprimir("Regla 12\n" + yyval.sval + "\n");}
break;
case 13:
//#line 39 "input.y"
{yyval = new ParserVal("[" + val_peek(1).sval + "]"); imprimir("Regla 13\n" + yyval.sval + "\n");}
break;
case 14:
//#line 41 "input.y"
{yyval = new ParserVal(val_peek(0).sval); listaAux = new ArrayList<String>(); listaAux.add(val_peek(0).sval) /* Vamos agregando los valores en una lista para usarlos mas arriba */; imprimir("Regla 14\n" + yyval.sval + "\n");}
break;
case 15:
//#line 42 "input.y"
{yyval = new ParserVal(val_peek(2).sval + "," + val_peek(0).sval); listaAux.add(val_peek(0).sval) /*Esta regla siempre se ejecuta despues que la de arriba, por eso el ArrayList ya fue instanciado con new */; imprimir("Regla 15\n" + yyval.sval + "\n");}
break;
case 16:
//#line 44 "input.y"
{yyval = new ParserVal("DEFVAR\n" + val_peek(1).sval + "\nENDDEF"); imprimir("Regla 16\n" + yyval.sval + "\n");}
break;
case 17:
//#line 46 "input.y"
{yyval = new ParserVal(val_peek(3).sval + ":" + val_peek(1).sval + ";"); TS.setTipos(listaAux,val_peek(1).sval) /* Tomamos la Tabla de s�mbolos y en el campo Tipo de los IDs recibidos en la lista, le seteamos el tipo */; imprimir("Regla 17\n" + yyval.sval + "\n");}
break;
case 18:
//#line 47 "input.y"
{yyval = new ParserVal(val_peek(4).sval + "\n" + val_peek(3).sval + ":" + val_peek(1).sval + ";"); TS.setTipos(listaAux,val_peek(1).sval) /* Tomamos la Tabla de s�mbolos y en el campo Tipo de los IDs recibidos en la lista, le seteamos el tipo */; imprimir("Regla 18\n" + yyval.sval + "\n");}
break;
case 19:
//#line 49 "input.y"
{yyval = new ParserVal(val_peek(0).sval); listaAux = new ArrayList<String>(); listaAux.add(val_peek(0).sval) /* Vamos agregando los IDs en una lista para usarlos mas arriba */; imprimir("Regla 19\n" + yyval.sval + "\n");}
break;
case 20:
//#line 50 "input.y"
{yyval = new ParserVal(val_peek(2).sval + "," + val_peek(0).sval); listaAux.add(val_peek(0).sval) /*Esta regla siempre se ejecuta despues que la de arriba, por eso el ArrayList ya fue instanciado con new */; imprimir("Regla 20\n" + yyval.sval + "\n");}
break;
case 21:
//#line 58 "input.y"
{yyval = new ParserVal(TablaDeSimbolos.TIPO_FLOAT); imprimir("Regla 21\n" + yyval.sval + "\n");}
break;
case 22:
//#line 59 "input.y"
{yyval = new ParserVal(TablaDeSimbolos.TIPO_STRING); imprimir("Regla 22\n" + yyval.sval + "\n");}
break;
case 23:
//#line 60 "input.y"
{yyval = new ParserVal(TablaDeSimbolos.TIPO_POINTER); imprimir("Regla 23\n" + yyval.sval + "\n");}
break;
case 24:
//#line 61 "input.y"
{yyval = new ParserVal(val_peek(0).sval); TS.verificarTipoValido(val_peek(0).sval); imprimir("Regla 24\n" + yyval.sval + "\n");}
break;
case 25:
//#line 63 "input.y"
{yyval = new ParserVal("BEGIN\n" + val_peek(1).sval + "\nEND"); imprimir("Regla 25\n" + yyval.sval + "\n");}
break;
case 26:
//#line 64 "input.y"
{yyval = new ParserVal("BEGIN\nEND"); imprimir("Regla 26\n" + yyval.sval + "\n");}
break;
case 27:
//#line 66 "input.y"
{yyval = new ParserVal(val_peek(0).sval); imprimir("Regla 27\n" + yyval.sval + "\n");}
break;
case 28:
//#line 67 "input.y"
{yyval = new ParserVal(val_peek(1).sval + "\n" + val_peek(0).sval); imprimir("Regla 28\n" + yyval.sval + "\n");}
break;
case 29:
//#line 69 "input.y"
{yyval = new ParserVal(val_peek(0).sval); imprimir("Regla 29\n" + yyval.sval + "\n");}
break;
case 30:
//#line 70 "input.y"
{yyval = new ParserVal(val_peek(0).sval); imprimir("Regla 30\n" + yyval.sval + "\n");}
break;
case 31:
//#line 71 "input.y"
{yyval = new ParserVal(val_peek(0).sval); imprimir("Regla 31\n" + yyval.sval + "\n");}
break;
case 32:
//#line 72 "input.y"
{yyval = new ParserVal(val_peek(0).sval); imprimir("Regla 32\n" + yyval.sval + "\n");}
break;
case 33:
//#line 82 "input.y"
{yyval = new ParserVal(val_peek(3).sval + " " + val_peek(1).sval + " = " + ";"); TS.verificarDeclaracion(val_peek(3).sval); TS.inicializarVariable(val_peek(3).sval); TS.verificarAsignacion(val_peek(3).sval, (ArrayList<String>)val_peek(1).obj); imprimir("Regla 33\n" + yyval.sval + "\n"); vector.agregar(new EntradaVectorPolaca(val_peek(3).sval, TS.getTipoNativo(TS.getEntrada(val_peek(3).sval).getTipo()))); vector.moverLista(listaAuxPolaca);vector.agregar(new EntradaVectorPolaca("="));vector.agregar(new EntradaVectorPolaca(";"));}
break;
case 34:
//#line 87 "input.y"
{yyval = new ParserVal(val_peek(3).sval + " " + val_peek(1).sval + " = " + ";"); TS.verificarDeclaracion(val_peek(3).sval); TS.inicializarVariable(val_peek(3).sval); listaAux = new ArrayList<String>(); listaAux.add(val_peek(1).sval); TS.verificarAsignacion(val_peek(3).sval, listaAux); imprimir("Regla 33\n" + yyval.sval + "\n"); vector.agregar(new EntradaVectorPolaca(val_peek(3).sval, TS.getTipoNativo(TS.getEntrada(val_peek(3).sval).getTipo()))); vector.agregar(new EntradaVectorPolaca(val_peek(1).sval, TS.getEntrada(val_peek(1).sval).getTipo())); vector.agregar(new EntradaVectorPolaca("="));vector.agregar(new EntradaVectorPolaca(";"));}
break;
case 35:
//#line 89 "input.y"
{yyval = new ParserVal(val_peek(0).sval); yyval.obj = val_peek(0).obj; imprimir("Regla 34\n" + yyval.sval + "\n");}
break;
case 36:
//#line 90 "input.y"
{yyval = new ParserVal(val_peek(2).sval + " " + val_peek(0).sval + " +"); yyval.obj = val_peek(2).obj; ((ArrayList<String>)yyval.obj).addAll((Collection)val_peek(0).obj); imprimir("Regla 35\n" + yyval.sval + "\n"); listaAuxPolaca.add(new EntradaVectorPolaca("+"));}
break;
case 37:
//#line 91 "input.y"
{yyval = new ParserVal(val_peek(2).sval + " " + val_peek(0).sval + " -"); yyval.obj = val_peek(2).obj; ((ArrayList<String>)yyval.obj).addAll((Collection)val_peek(0).obj); imprimir("Regla 36\n" + yyval.sval + "\n");listaAuxPolaca.add(new EntradaVectorPolaca("-"));}
break;
case 38:
//#line 93 "input.y"
{yyval = new ParserVal(val_peek(0).sval); yyval.obj = new ArrayList<String>(); ((ArrayList<String>)yyval.obj).addAll((Collection)val_peek(0).obj); imprimir("Regla 37\n" + yyval.sval + "\n");}
break;
case 39:
//#line 94 "input.y"
{yyval = new ParserVal(val_peek(2).sval + " " + val_peek(0).sval + " *"); yyval.obj = val_peek(2).obj; ((ArrayList<String>)yyval.obj).addAll((Collection)val_peek(0).obj); imprimir("Regla 38\n" + yyval.sval + "\n"); listaAuxPolaca.add(new EntradaVectorPolaca("*"));}
break;
case 40:
//#line 95 "input.y"
{yyval = new ParserVal(val_peek(2).sval + " " + val_peek(0).sval + " /"); yyval.obj = val_peek(2).obj; ((ArrayList<String>)yyval.obj).addAll((Collection)val_peek(0).obj); imprimir("Regla 39\n" + yyval.sval + "\n"); listaAuxPolaca.add(new EntradaVectorPolaca("/"));}
break;
case 41:
//#line 103 "input.y"
{yyval = new ParserVal(val_peek(0).sval); TS.verificarDeclaracion(val_peek(0).sval); TS.verificarInicializacionVariable(val_peek(0).sval); yyval.obj = new ArrayList<String>(); ((ArrayList<String>)yyval.obj).add(val_peek(0).sval); imprimir("Regla 40\n" + yyval.sval + "\n"); listaAuxPolaca.add(new EntradaVectorPolaca(val_peek(0).sval, TS.getTipoNativo(TS.getEntrada(val_peek(0).sval).getTipo())));}
break;
case 42:
//#line 104 "input.y"
{yyval = new ParserVal(val_peek(0).sval); yyval.obj = new ArrayList<String>(); ((ArrayList<String>)yyval.obj).add(val_peek(0).sval); imprimir("Regla 41\n" + yyval.sval + "\n"); listaAuxPolaca.add(new EntradaVectorPolaca(TS.getEntrada(val_peek(0).sval).getValor(), TS.getEntrada(val_peek(0).sval).getTipo()));}
break;
case 43:
//#line 105 "input.y"
{yyval = new ParserVal(val_peek(1).sval); yyval.obj = val_peek(1).obj; imprimir("Regla 42\n" + yyval.sval + "\n");}
break;
case 44:
//#line 106 "input.y"
{yyval = new ParserVal(val_peek(0).sval); yyval.obj = new ArrayList<String>(); ((ArrayList<String>)yyval.obj).addAll(listaAux) /* Agregamos todas las CTES que ya trae el average para hacer validacion de tipos despues */; imprimir("Regla 43\n" + yyval.sval + "\n");}
break;
case 45:
//#line 108 "input.y"
{vector.agregar(new EntradaVectorPolaca("@IF")); vector.moverCondicionIF(listaAuxPolaca); vector.agregar(new EntradaVectorPolaca("@THEN"));}
break;
case 46:
//#line 108 "input.y"
{yyval = new ParserVal("IF(" + val_peek(4).sval + ")\n" + val_peek(1).sval + "\nENDIF"); imprimir("Reglas 44 y 45\n" + yyval.sval + "\n");vector.agregar(new EntradaVectorPolaca("@ENDIF"));}
break;
case 47:
//#line 110 "input.y"
{yyval = new ParserVal(val_peek(0).sval); vector.resolverSaltos(vector.getPosicionActual(), stack.pop()) /* Al final del THEN (el ENDIF) es donde van a saltar todas las condiciones de este IF que se encuentren en la pila. Si hay una o dos condiciones, eso lo sabemos por el valor que sacamos del stack y que fue puesto en 'moverCondicionIF()'*/;}
break;
case 48:
//#line 111 "input.y"
{vector.agregar(new EntradaVectorPolaca(VectorPolaca.SIEMPRE)); vector.resolverSaltos(vector.getPosicionActual() + 1, stack.pop());/* Al comienzo del ELSE (posicion actual del vector + 1 debido al casillero de direccion) es donde van a saltar todas las condiciones de este IF que se encuentren en la pila. Si hay una o dos condiciones, eso lo sabemos por el valor que sacamos del stack y que fue puesto en 'moverCondicionIF()'*/ ; stack.push(vector.getPosicionActual())/*Apilamos el casillero para direccion de salto que est� al final de este bloque (THEN) */; vector.agregar(new EntradaVectorPolaca("DIRECCION")); vector.agregar(new EntradaVectorPolaca("@ELSE"));}
break;
case 49:
//#line 111 "input.y"
{yyval = new ParserVal(val_peek(3).sval + "\nELSE\n" + val_peek(0).sval); vector.agregar((new EntradaVectorPolaca(String.valueOf(vector.getPosicionActual()))), stack.pop()); /*En el casillero que est� al final del THEN, le seteamos la direccion del ENDIF*/}
break;
case 50:
//#line 113 "input.y"
{yyval = new ParserVal(val_peek(0).sval); imprimir("Regla 46\n" + yyval.sval + "\n"); listaAuxPolaca.add(new EntradaVectorPolaca(VectorPolaca.SIMPLE)); /*Lo agregamos para mantener un est�ndar y que sea igual a los otros casos*/}
break;
case 51:
//#line 114 "input.y"
{yyval = new ParserVal(val_peek(1).sval + " _NEGACION"); imprimir("Regla 47\n" + yyval.sval + "\n"); listaAuxPolaca.add(new EntradaVectorPolaca(VectorPolaca.NEGACION));}
break;
case 52:
//#line 115 "input.y"
{yyval = new ParserVal(val_peek(2).sval + " " + val_peek(0).sval + " _AND"); imprimir("Regla 48\n" + yyval.sval + "\n"); listaAuxPolaca.add(new EntradaVectorPolaca(VectorPolaca.AND));}
break;
case 53:
//#line 116 "input.y"
{yyval = new ParserVal(val_peek(2).sval + " " + val_peek(0).sval + " _OR"); imprimir("Regla 49\n" + yyval.sval + "\n"); listaAuxPolaca.add(new EntradaVectorPolaca(VectorPolaca.OR));}
break;
case 54:
//#line 118 "input.y"
{yyval = new ParserVal(val_peek(2).sval + " " + val_peek(0).sval + " _CMP " + VectorPolaca.DISTINTO + " DIRECCION"); TS.verificarComparacion((ArrayList<String>)val_peek(2).obj, (ArrayList<String>)val_peek(0).obj); imprimir("Regla 50\n" + yyval.sval + "\n"); listaAuxPolaca.add(new EntradaVectorPolaca("_CMP"));listaAuxPolaca.add(new EntradaVectorPolaca(VectorPolaca.DISTINTO));listaAuxPolaca.add(new EntradaVectorPolaca("DIRECCION"));}
break;
case 55:
//#line 119 "input.y"
{yyval = new ParserVal(val_peek(2).sval + " " + val_peek(0).sval + " _CMP " + VectorPolaca.IGUAL + " DIRECCION"); TS.verificarComparacion((ArrayList<String>)val_peek(2).obj, (ArrayList<String>)val_peek(0).obj); imprimir("Regla 51\n" + yyval.sval + "\n"); listaAuxPolaca.add(new EntradaVectorPolaca("_CMP"));listaAuxPolaca.add(new EntradaVectorPolaca(VectorPolaca.IGUAL));listaAuxPolaca.add(new EntradaVectorPolaca("DIRECCION"));}
break;
case 56:
//#line 120 "input.y"
{yyval = new ParserVal(val_peek(2).sval + " " + val_peek(0).sval + " _CMP " + VectorPolaca.MENOR_O_IGUAL + " DIRECCION"); TS.verificarComparacion((ArrayList<String>)val_peek(2).obj, (ArrayList<String>)val_peek(0).obj); imprimir("Regla 52\n" + yyval.sval + "\n"); listaAuxPolaca.add(new EntradaVectorPolaca("_CMP"));listaAuxPolaca.add(new EntradaVectorPolaca(VectorPolaca.MENOR_O_IGUAL));listaAuxPolaca.add(new EntradaVectorPolaca("DIRECCION"));}
break;
case 57:
//#line 121 "input.y"
{yyval = new ParserVal(val_peek(2).sval + " " + val_peek(0).sval + " _CMP " + VectorPolaca.MAYOR_O_IGUAL + " DIRECCION"); TS.verificarComparacion((ArrayList<String>)val_peek(2).obj, (ArrayList<String>)val_peek(0).obj); imprimir("Regla 53\n" + yyval.sval + "\n"); listaAuxPolaca.add(new EntradaVectorPolaca("_CMP"));listaAuxPolaca.add(new EntradaVectorPolaca(VectorPolaca.MAYOR_O_IGUAL));listaAuxPolaca.add(new EntradaVectorPolaca("DIRECCION"));}
break;
case 58:
//#line 122 "input.y"
{yyval = new ParserVal(val_peek(2).sval + " " + val_peek(0).sval + " _CMP " + VectorPolaca.MENOR + " DIRECCION"); TS.verificarComparacion((ArrayList<String>)val_peek(2).obj, (ArrayList<String>)val_peek(0).obj); imprimir("Regla 54\n" + yyval.sval + "\n"); listaAuxPolaca.add(new EntradaVectorPolaca("_CMP"));listaAuxPolaca.add(new EntradaVectorPolaca(VectorPolaca.MENOR));listaAuxPolaca.add(new EntradaVectorPolaca("DIRECCION"));}
break;
case 59:
//#line 123 "input.y"
{yyval = new ParserVal(val_peek(2).sval + " " + val_peek(0).sval + " _CMP " + VectorPolaca.MAYOR + " DIRECCION"); TS.verificarComparacion((ArrayList<String>)val_peek(2).obj, (ArrayList<String>)val_peek(0).obj); imprimir("Regla 55\n" + yyval.sval + "\n"); listaAuxPolaca.add(new EntradaVectorPolaca("_CMP"));listaAuxPolaca.add(new EntradaVectorPolaca(VectorPolaca.MAYOR));listaAuxPolaca.add(new EntradaVectorPolaca("DIRECCION"));}
break;
case 60:
//#line 125 "input.y"
{vector.agregar(new EntradaVectorPolaca("@REPEAT")); stack.push(vector.getPosicionActual()-1);}
break;
case 61:
//#line 125 "input.y"
{yyval = new ParserVal("REPEAT\n" + val_peek(5).sval + "\nUNTIL(" + val_peek(2).sval + ");"); imprimir("Regla 56\n" + yyval.sval + "\n"); vector.agregar(new EntradaVectorPolaca("@UNTIL")); vector.moverCondicionREPEAT(listaAuxPolaca); vector.agregar(new EntradaVectorPolaca("@END REPEAT-UNTIL"));}
break;
case 62:
//#line 127 "input.y"
{yyval = new ParserVal("DISPLAY(" + val_peek(2).sval + ");"); imprimir("Regla 57\n" + yyval.sval + "\n"); vector.agregar(new EntradaVectorPolaca("@DISPLAY")); vector.agregar(new EntradaVectorPolaca(TS.getEntrada(val_peek(2).sval).getNombre(),TS.getEntrada(val_peek(2).sval).getTipo())); vector.agregar(new EntradaVectorPolaca(";"));}
break;
case 63:
//#line 129 "input.y"
{yyval = new ParserVal("AVG(" + val_peek(1).sval + ")"); imprimir("Regla 58\n" + yyval.sval + "\n"); listaAuxPolaca.addAll(TS.convertirAverageEnPolaca(listaAux));}
break;
case 64:
//#line 131 "input.y"
{yyval = new ParserVal(TS.getNombre(yylval.ival)); imprimir("Regla 59\n" + yyval.sval + "\n");}
break;
case 65:
//#line 133 "input.y"
{yyval = new ParserVal(TS.getNombre(yylval.ival))/* Aca s� o s� necesitamos sacar el nombre y no el valor (aunque aparezcan con un "_"), sino las reglas de mas arriba nunca las van a encontrar en TS */; imprimir("Regla 60\n" + yyval.sval + "\n");}
break;
case 66:
//#line 135 "input.y"
{yyval = new ParserVal(TS.getNombre(yylval.ival))/* Aca s� o s� necesitamos sacar el nombre y no el valor (aunque aparezcan con un "_"), sino las reglas de mas arriba nunca las van a encontrar en TS */; imprimir("Regla 61\n" + yyval.sval + "\n");}
break;
//#line 844 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
