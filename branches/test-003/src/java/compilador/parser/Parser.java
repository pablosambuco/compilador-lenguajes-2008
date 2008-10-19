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
import compilador.sintaxis.ArbolSintactico;
import compilador.sintaxis.Datos;
import compilador.sintaxis.Nodo;
import compilador.beans.TablaDeSimbolos;
import compilador.util.ArchivoReader;
//#line 24 "Parser.java"




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
   18,   18,   19,   23,   23,   23,   24,   24,   24,   25,
   25,   25,   25,   20,   20,   27,   27,   27,   27,   28,
   28,   28,   28,   28,   28,   21,   22,   26,    6,   11,
   13,
};
final static short yylen[] = {                            2,
    1,    3,    2,    1,    1,    2,    5,    1,    1,    3,
    1,    3,    3,    1,    3,    3,    4,    5,    1,    3,
    1,    1,    1,    1,    3,    2,    1,    2,    1,    1,
    1,    1,    4,    1,    3,    3,    1,    3,    3,    1,
    1,    3,    1,    6,    8,    1,    4,    3,    3,    3,
    3,    3,    3,    3,    3,    7,    5,    4,    1,    1,
    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    1,    0,    0,    4,    5,   59,
   26,    0,    0,    0,    0,    0,   27,   29,   30,   31,
   32,   19,    0,    0,    0,    0,    6,    3,    0,    0,
    0,    0,   25,   28,   16,    0,    0,    0,    0,    2,
   60,    0,    0,    0,   40,   41,    0,    0,   37,   43,
    0,    0,    0,   61,    0,    0,    0,   20,   22,   21,
   23,   24,    0,    0,    0,    8,    9,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   33,    0,   17,    0,   11,
    0,   14,    7,    0,   42,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   38,   39,    0,   48,   49,
    0,   57,   18,   10,    0,   13,    0,   47,   58,    0,
   44,    0,   12,   15,    0,   56,   45,
};
final static short yydgoto[] = {                          4,
    5,    6,    7,    8,    9,   45,   65,   66,   67,   89,
   46,   91,   55,   23,   24,   63,   16,   17,   18,   19,
   20,   21,   47,   48,   49,   50,   51,   52,
};
final static short yysindex[] = {                      -178,
 -214, -236, -236,    0,    0, -259, -271,    0,    0,    0,
    0, -232, -212, -228, -238, -213,    0,    0,    0,    0,
    0,    0, -246, -248, -239, -271,    0,    0, -234, -203,
 -200, -249,    0,    0,    0, -169, -236, -184, -215,    0,
    0, -207, -249, -190,    0,    0,  -95, -149,    0,    0,
 -204, -145, -177,    0, -173, -241, -184,    0,    0,    0,
    0,    0, -174, -123, -164,    0,    0, -249, -247, -136,
 -249, -249, -249, -249, -249, -249, -249, -249, -249, -249,
 -212, -249, -249, -234, -133,    0, -124,    0, -126,    0,
 -110,    0,    0, -144,    0,  -88, -112, -149, -149,  -70,
  -70,  -70,  -70,  -70,  -70,    0,    0, -225,    0,    0,
  -87,    0,    0,    0,  -88,    0, -200,    0,    0, -212,
    0,  -85,    0,    0, -197,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0, -143,    0,    0,
    0,  -82,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0, -127, -111, -179,
  -94,  -86,  -83,  -81,  -79,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,  195,    9,  196,   -1,    0,  133,    0,    0,
  -58,    0,  -59,    0,  181,  148,   -9,  -13,    0,    0,
    0,    0,  -25,  126,  120,    0,  122,   22,
};
final static int YYTABLESIZE=206;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         15,
   22,   25,   34,   30,   92,   90,   56,   10,   41,    1,
   10,   15,   71,   72,   15,   28,   34,   69,   71,   72,
   10,   22,   10,   41,   43,   32,    2,   95,   15,   37,
   38,   10,   42,    3,   40,   58,   62,   90,   86,   43,
   35,   29,   10,   10,   10,   31,   44,  100,  101,  102,
  103,  104,  105,   10,   39,   62,  123,  124,   54,   10,
   64,   44,   12,  120,  121,   13,   68,   11,   33,   14,
   81,  108,   10,   12,   12,   12,   13,   13,   13,   15,
   14,   14,   14,   70,   12,   50,   50,   13,   53,   94,
   12,   14,  127,   13,   34,   50,   84,   14,   59,   60,
   61,   85,    1,  109,  110,   88,   15,    2,   37,   57,
  125,   34,   79,   80,    3,   93,   34,   34,   15,   82,
   83,   34,   34,   15,   34,   34,   34,   34,   34,   34,
  118,   34,   35,   35,   41,   54,   34,   35,   35,   96,
   35,   35,   35,   35,   35,   35,  112,   35,   36,   36,
  114,  115,   35,   36,   36,  113,   36,   36,   36,   36,
   36,   36,  119,   36,   71,   72,  116,  117,   36,   41,
   51,   51,   73,   74,   75,   76,   77,   78,   52,   52,
   51,   53,   53,   54,   54,   55,   55,  122,   52,   71,
   72,   53,   46,   54,  126,   55,   98,   99,  106,  107,
   26,   27,   97,   36,   87,  111,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          1,
    2,    3,   16,   13,   64,   64,   32,  257,  258,  281,
  257,   13,  260,  261,   16,    7,   30,   43,  260,  261,
  257,   23,  257,  258,  274,  264,  286,  275,   30,  278,
  279,  257,  267,  293,   26,   37,   38,   96,  280,  274,
  287,  274,  257,  257,  257,  274,  296,   73,   74,   75,
   76,   77,   78,  257,  294,   57,  115,  117,  259,  257,
  276,  296,  288,  289,  290,  291,  274,  282,  282,  295,
  275,   81,  257,  288,  288,  288,  291,  291,  291,   81,
  295,  295,  295,  274,  288,  265,  266,  291,  292,   68,
  288,  295,  290,  291,  108,  275,  274,  295,  283,  284,
  285,  275,  281,   82,   83,  280,  108,  286,  278,  279,
  120,  125,  262,  263,  293,  280,  260,  261,  120,  265,
  266,  265,  266,  125,  268,  269,  270,  271,  272,  273,
  275,  275,  260,  261,  258,  259,  280,  265,  266,  276,
  268,  269,  270,  271,  272,  273,  280,  275,  260,  261,
  277,  278,  280,  265,  266,  280,  268,  269,  270,  271,
  272,  273,  275,  275,  260,  261,  277,  278,  280,  258,
  265,  266,  268,  269,  270,  271,  272,  273,  265,  266,
  275,  265,  266,  265,  266,  265,  266,  275,  275,  260,
  261,  275,  275,  275,  280,  275,   71,   72,   79,   80,
    6,    6,   70,   23,   57,   84,
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
"condicional : IF PAR_ABRE condicion PAR_CIERRA sentencias ENDIF",
"condicional : IF PAR_ABRE condicion PAR_CIERRA sentencias ELSE sentencias ENDIF",
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
"bucle : REPEAT sentencias UNTIL PAR_ABRE condicion PAR_CIERRA PUNTO_Y_COMA",
"display_command : DISPLAY PAR_ABRE cte_str PAR_CIERRA PUNTO_Y_COMA",
"average : AVG PAR_ABRE lista_num PAR_CIERRA",
"id : ID",
"cte_num : CTE_NUM",
"cte_str : CTE_STR",
};

//#line 114 "input.y"
	void yyerror(String mensaje) {
		System.out.println("Error: " + mensaje);
	}
	public static final int ESTADO_INICIAL = 0;
	public static final int ESTADO_FINAL = 36;

		//Manejo de errores lexicos
		public static int INCOMPLETO=0;
		public static int ERROR_LEXICO=YYERRCODE;

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
		System.out.print("\n\nImprimimos desde el Arbol \n \n");
		ArbolSintactico arbol = ArbolSintactico.getInstance();
		arbol.imprimirArbol();

	}
//#line 380 "Parser.java"
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
//#line 16 "input.y"
{yyval = new ParserVal(val_peek(0).sval); System.out.println("Regla 01 (Programa Completo)\n" + yyval.sval);}
break;
case 2:
//#line 18 "input.y"
{yyval = new ParserVal(val_peek(2).sval + "\n" + val_peek(1).sval +  "\n" + val_peek(0).sval); System.out.println("Regla 02\n" + yyval.sval + "\n");}
break;
case 3:
//#line 19 "input.y"
{yyval = new ParserVal(val_peek(1).sval + "\n" + val_peek(0).sval); System.out.println("Regla 03\n" + yyval.sval + "\n");}
break;
case 4:
//#line 20 "input.y"
{yyval = new ParserVal(val_peek(0).sval); System.out.println("Regla 04\n" + yyval.sval + "\n");}
break;
case 5:
//#line 22 "input.y"
{yyval = new ParserVal(val_peek(0).sval); System.out.println("Regla 05\n" + yyval.sval + "\n");}
break;
case 6:
//#line 23 "input.y"
{yyval = new ParserVal(val_peek(1).sval + "\n" + val_peek(0).sval); System.out.println("Regla 06\n" + yyval.sval + "\n");}
break;
case 7:
//#line 25 "input.y"
{yyval = new ParserVal("TYPE " + val_peek(3).sval + " AS " + val_peek(1).sval + ";");  System.out.println("Regla 07\n" + yyval.sval + "\n");}
break;
case 8:
//#line 27 "input.y"
{yyval = new ParserVal(val_peek(0).sval); System.out.println("Regla 08\n" + yyval.sval + "\n");}
break;
case 9:
//#line 28 "input.y"
{yyval = new ParserVal(val_peek(0).sval); System.out.println("Regla 09\n" + yyval.sval + "\n");}
break;
case 10:
//#line 30 "input.y"
{yyval = new ParserVal("[" + val_peek(1).sval + "]"); System.out.println("Regla 10\n" + yyval.sval + "\n");}
break;
case 11:
//#line 32 "input.y"
{yyval = new ParserVal(val_peek(0).sval); System.out.println("Regla 11\n" + yyval.sval + "\n");}
break;
case 12:
//#line 33 "input.y"
{yyval = new ParserVal(val_peek(2).sval + "," + val_peek(0).sval); System.out.println("Regla 12\n" + yyval.sval + "\n");}
break;
case 13:
//#line 35 "input.y"
{yyval = new ParserVal("[" + val_peek(1).sval + "]"); System.out.println("Regla 13\n" + yyval.sval + "\n");}
break;
case 14:
//#line 37 "input.y"
{yyval = new ParserVal(val_peek(0).sval); System.out.println("Regla 14\n" + yyval.sval + "\n");}
break;
case 15:
//#line 38 "input.y"
{yyval = new ParserVal(val_peek(2).sval + "," + val_peek(0).sval); System.out.println("Regla 15\n" + yyval.sval + "\n");}
break;
case 16:
//#line 40 "input.y"
{yyval = new ParserVal("DEFVAR\n" + val_peek(1).sval + "\nENDDEF"); System.out.println("Regla 16\n" + yyval.sval + "\n");}
break;
case 17:
//#line 42 "input.y"
{yyval = new ParserVal(val_peek(3).sval + ":" + val_peek(1).sval + ";"); System.out.println("Regla 17\n" + yyval.sval + "\n");}
break;
case 18:
//#line 43 "input.y"
{yyval = new ParserVal(val_peek(4).sval + "\n" + val_peek(3).sval + ":" + val_peek(1).sval + ";"); System.out.println("Regla 18\n" + yyval.sval + "\n");}
break;
case 19:
//#line 45 "input.y"
{yyval = new ParserVal(val_peek(0).sval); System.out.println("Regla 19\n" + yyval.sval + "\n");}
break;
case 20:
//#line 46 "input.y"
{yyval = new ParserVal(val_peek(2).sval + "," + val_peek(0).sval); System.out.println("Regla 20\n" + yyval.sval + "\n");}
break;
case 21:
//#line 48 "input.y"
{yyval = new ParserVal("FLOAT"); System.out.println("Regla 21\n" + yyval.sval + "\n");}
break;
case 22:
//#line 49 "input.y"
{yyval = new ParserVal("STRING"); System.out.println("Regla 22\n" + yyval.sval + "\n");}
break;
case 23:
//#line 50 "input.y"
{yyval = new ParserVal("POINTER"); System.out.println("Regla 23\n" + yyval.sval + "\n");}
break;
case 24:
//#line 51 "input.y"
{yyval = new ParserVal(val_peek(0).sval); System.out.println("Regla 24\n" + yyval.sval + "\n");}
break;
case 25:
//#line 53 "input.y"
{yyval = new ParserVal("BEGIN\n" + val_peek(1).sval + "\nEND"); System.out.println("Regla 25\n" + yyval.sval + "\n");}
break;
case 26:
//#line 54 "input.y"
{yyval = new ParserVal("BEGIN\nEND"); System.out.println("Regla 26\n" + yyval.sval + "\n");}
break;
case 27:
//#line 56 "input.y"
{yyval = new ParserVal(val_peek(0).sval); System.out.println("Regla 27\n" + yyval.sval + "\n");}
break;
case 28:
//#line 57 "input.y"
{yyval = new ParserVal(val_peek(1).sval + "\n" + val_peek(0).sval); System.out.println("Regla 28\n" + yyval.sval + "\n");}
break;
case 29:
//#line 59 "input.y"
{yyval = new ParserVal(val_peek(0).sval); System.out.println("Regla 29\n" + yyval.sval + "\n");}
break;
case 30:
//#line 60 "input.y"
{yyval = new ParserVal(val_peek(0).sval); System.out.println("Regla 30\n" + yyval.sval + "\n");}
break;
case 31:
//#line 61 "input.y"
{yyval = new ParserVal(val_peek(0).sval); System.out.println("Regla 31\n" + yyval.sval + "\n");}
break;
case 32:
//#line 62 "input.y"
{yyval = new ParserVal(val_peek(0).sval); System.out.println("Regla 32\n" + yyval.sval + "\n");}
break;
case 33:
//#line 64 "input.y"
{yyval = new ParserVal(val_peek(3).sval + " = " + val_peek(1).sval + ";"); System.out.println("Regla 33\n" + yyval.sval + "\n"); yyval.obj = ArbolSintactico.getInstance().insertar(new Datos("="),(Nodo)val_peek(3).obj,(Nodo)val_peek(1).obj);ArbolSintactico.getInstance().setRaiz((Nodo)yyval.obj);}
break;
case 34:
//#line 66 "input.y"
{yyval = new ParserVal(val_peek(0).sval); System.out.println("Regla 34\n" + yyval.sval + "\n"); yyval.obj = val_peek(0).obj;}
break;
case 35:
//#line 67 "input.y"
{yyval = new ParserVal(val_peek(2).sval + " + " + val_peek(0).sval); System.out.println("Regla 35\n" + yyval.sval + "\n"); yyval.obj = ArbolSintactico.getInstance().insertar(new Datos("+"),(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);}
break;
case 36:
//#line 68 "input.y"
{yyval = new ParserVal(val_peek(2).sval + " - " + val_peek(0).sval); System.out.println("Regla 36\n" + yyval.sval + "\n"); yyval.obj = ArbolSintactico.getInstance().insertar(new Datos("-"),(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);}
break;
case 37:
//#line 70 "input.y"
{yyval = new ParserVal(val_peek(0).sval);System.out.println("Regla 37\n" + yyval.sval + "\n"); yyval.obj = val_peek(0).obj;}
break;
case 38:
//#line 71 "input.y"
{yyval = new ParserVal(val_peek(2).sval + " * " + val_peek(0).sval); System.out.println("Regla 38\n" + yyval.sval + "\n"); yyval.obj = ArbolSintactico.getInstance().insertar(new Datos("*"),(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);}
break;
case 39:
//#line 72 "input.y"
{yyval = new ParserVal(val_peek(2).sval + " / " + val_peek(0).sval); System.out.println("Regla 39\n" + yyval.sval + "\n"); yyval.obj = ArbolSintactico.getInstance().insertar(new Datos("/"),(Nodo)val_peek(2).obj,(Nodo)val_peek(0).obj);}
break;
case 40:
//#line 74 "input.y"
{yyval = new ParserVal(val_peek(0).sval); System.out.println("Regla 40\n" + yyval.sval + "\n"); yyval.obj = val_peek(0).obj;}
break;
case 41:
//#line 75 "input.y"
{yyval = new ParserVal(val_peek(0).sval); System.out.println("Regla 41\n" + yyval.sval + "\n"); yyval.obj = val_peek(0).obj;}
break;
case 42:
//#line 76 "input.y"
{yyval = new ParserVal("(" + val_peek(1).sval + ")"); System.out.println("Regla 42\n" + yyval.sval + "\n");}
break;
case 43:
//#line 77 "input.y"
{yyval = new ParserVal(val_peek(0).sval); System.out.println("Regla 43\n" + yyval.sval + "\n");}
break;
case 44:
//#line 79 "input.y"
{yyval = new ParserVal("IF(" + val_peek(3).sval + ")\n" + val_peek(2).sval + "\nENDIF"); System.out.println("Regla 44\n" + yyval.sval + "\n");}
break;
case 45:
//#line 80 "input.y"
{yyval = new ParserVal("IF(" + val_peek(5).sval + ")\n" + val_peek(3).sval + "\nELSE\n" + val_peek(1).sval + "\nENDIF"); System.out.println("Regla 45\n" + yyval.sval + "\n");}
break;
case 46:
//#line 82 "input.y"
{yyval = new ParserVal(val_peek(0).sval); System.out.println("Regla 46\n" + yyval.sval + "\n");}
break;
case 47:
//#line 83 "input.y"
{yyval = new ParserVal("!(" + val_peek(1).sval + ")"); System.out.println("Regla 47\n" + yyval.sval + "\n");}
break;
case 48:
//#line 84 "input.y"
{yyval = new ParserVal(val_peek(2).sval + "&&" + val_peek(0).sval); System.out.println("Regla 48\n" + yyval.sval + "\n");}
break;
case 49:
//#line 85 "input.y"
{yyval = new ParserVal(val_peek(2).sval + "||" + val_peek(0).sval); System.out.println("Regla 49\n" + yyval.sval + "\n");}
break;
case 50:
//#line 87 "input.y"
{yyval = new ParserVal(val_peek(2).sval + "==" + val_peek(0).sval); System.out.println("Regla 50\n" + yyval.sval + "\n");}
break;
case 51:
//#line 88 "input.y"
{yyval = new ParserVal(val_peek(2).sval + "!=" + val_peek(0).sval); System.out.println("Regla 51\n" + yyval.sval + "\n");}
break;
case 52:
//#line 89 "input.y"
{yyval = new ParserVal(val_peek(2).sval + ">" + val_peek(0).sval); System.out.println("Regla 52\n" + yyval.sval + "\n");}
break;
case 53:
//#line 90 "input.y"
{yyval = new ParserVal(val_peek(2).sval + "<" + val_peek(0).sval); System.out.println("Regla 53\n" + yyval.sval + "\n");}
break;
case 54:
//#line 91 "input.y"
{yyval = new ParserVal(val_peek(2).sval + ">=" + val_peek(0).sval); System.out.println("Regla 54\n" + yyval.sval + "\n");}
break;
case 55:
//#line 92 "input.y"
{yyval = new ParserVal(val_peek(2).sval + "<=" + val_peek(0).sval); System.out.println("Regla 55\n" + yyval.sval + "\n");}
break;
case 56:
//#line 94 "input.y"
{yyval = new ParserVal("REPEAT\n" + val_peek(5).sval + "\nUNTIL(" + val_peek(2).sval + ");"); System.out.println("Regla 56\n" + yyval.sval + "\n");}
break;
case 57:
//#line 96 "input.y"
{yyval = new ParserVal("DISPLAY(" + val_peek(2).sval + ");"); System.out.println("Regla 57\n" + yyval.sval + "\n");}
break;
case 58:
//#line 98 "input.y"
{yyval = new ParserVal("AVG(" + val_peek(1).sval + ")"); System.out.println("Regla 58\n" + yyval.sval + "\n");}
break;
case 59:
//#line 100 "input.y"
{yyval = new ParserVal(TablaDeSimbolos.getInstance().getPos(yylval.ival));
		yyval.obj = ArbolSintactico.getInstance().crearHoja(new Datos(TablaDeSimbolos.getInstance().getPos(yylval.ival)));
		System.out.println("Regla 59\n" + yyval.sval + "\n");}
break;
case 60:
//#line 104 "input.y"
{yyval = new ParserVal(TablaDeSimbolos.getInstance().getPos(yylval.ival));
				 yyval.obj = ArbolSintactico.getInstance().crearHoja(new Datos(TablaDeSimbolos.getInstance().getPos(yylval.ival)));
				 System.out.println("Regla 60\n" + yyval.sval + "\n");}
break;
case 61:
//#line 108 "input.y"
{yyval = new ParserVal("\"" + TablaDeSimbolos.getInstance().getPos(yylval.ival) + "\"");
				 yyval.obj = ArbolSintactico.getInstance().crearHoja(new Datos(TablaDeSimbolos.getInstance().getPos(yylval.ival)));
				 System.out.println("Regla 61\n" + yyval.sval + "\n");}
break;
//#line 778 "Parser.java"
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
