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
//#line 21 "Parser.java"




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
    0,    1,    1,    1,    2,    2,    5,    6,    6,    7,
    9,    9,    8,   10,   10,    3,   12,   12,   13,   13,
   14,   14,   14,   14,    4,    4,   16,   16,   17,   17,
   17,   17,   18,   22,   22,   22,   23,   23,   23,   24,
   24,   24,   24,   19,   19,   27,   27,   27,   27,   28,
   28,   28,   28,   28,   28,   20,   21,   26,   15,   25,
   11,
};
final static short yylen[] = {                            2,
    1,    3,    2,    1,    1,    2,    5,    1,    1,    3,
    1,    3,    3,    1,    3,    3,    4,    5,    1,    3,
    1,    1,    1,    1,    3,    2,    1,    2,    1,    1,
    1,    1,    4,    1,    3,    3,    1,    3,    3,    1,
    1,    3,    1,    6,    8,    1,    2,    3,    3,    3,
    3,    3,    3,    3,    3,    7,    5,    4,    2,    2,
    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    1,    0,    0,    4,    5,    0,
   26,    0,    0,    0,    0,    0,   27,   29,   30,   31,
   32,    0,    0,   19,    0,    0,    6,    3,   59,    0,
    0,    0,    0,   25,   28,   16,    0,    0,    0,    0,
    2,    0,    0,    0,    0,   40,    0,    0,   37,   41,
   43,    0,    0,    0,    0,    0,    0,   20,   22,   21,
   23,    0,   24,    0,    0,    8,    9,   60,   47,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   33,    0,   17,   11,
   61,    0,    0,   14,    7,   42,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   38,   39,    0,   48,
   49,    0,   57,   18,   10,    0,   13,    0,   58,    0,
   44,    0,   12,   15,    0,   56,   45,
};
final static short yydgoto[] = {                          4,
    5,    6,    7,    8,    9,   65,   66,   67,   92,   93,
   94,   22,   23,   62,   46,   16,   17,   18,   19,   20,
   21,   47,   48,   49,   50,   51,   52,   53,
};
final static short yysindex[] = {                      -261,
 -215, -248, -246,    0,    0, -204, -257,    0,    0, -231,
    0, -233, -197, -221, -209, -205,    0,    0,    0,    0,
    0, -251, -265,    0, -228, -257,    0,    0,    0, -239,
 -217, -189, -235,    0,    0,    0, -182, -248, -178, -192,
    0, -164, -235, -235, -173,    0,  -95, -147,    0,    0,
    0, -171, -145, -165, -162, -253, -178,    0,    0,    0,
    0, -149,    0, -123, -140,    0,    0,    0,    0, -161,
 -129, -235, -235, -235, -235, -235, -235, -235, -235, -235,
 -235, -197, -235, -235, -239, -124,    0, -117,    0,    0,
    0, -126, -110,    0,    0,    0,  -88,  -87, -147, -147,
  -76,  -76,  -76,  -76,  -76,  -76,    0,    0, -226,    0,
    0,  -82,    0,    0,    0,  -64,    0,  -63,    0, -197,
    0,  -85,    0,    0, -203,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0, -143,    0,    0,
    0,    0,  -78,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0, -127, -111,
 -232, -207, -163,  -94,  -86,  -83,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,  192,    3,  193,    0,  129,    0,    0,    0,
   83,    0,  180,  146,   -1,  -10,  -14,    0,    0,    0,
    0,  -28,  114,  110,    0,    0,  119,  -39,
};
final static int YYTABLESIZE=204;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         15,
   24,   35,   31,   69,   56,   10,   72,   73,   10,   28,
   25,   15,   38,   39,   15,   70,   35,   10,   42,    1,
   24,   10,   42,    1,    2,   29,   87,   43,   41,   15,
   10,    3,   50,   50,   44,   36,   58,   63,   44,   10,
   30,   10,   50,  110,  111,  101,  102,  103,  104,  105,
  106,   10,   32,   10,   33,   63,   45,   51,   51,   10,
   45,   12,  120,  121,   13,   40,   11,   51,   14,   55,
   12,  109,   12,   13,   54,   13,   34,   14,   10,   14,
   15,    2,   12,   64,   12,   13,  127,   13,    3,   14,
   12,   14,   68,   13,   35,   38,   57,   14,   72,   73,
   71,   52,   52,   82,   59,   60,   61,   15,   85,  125,
   35,   52,   86,   96,   80,   81,   34,   34,   15,   83,
   84,   34,   34,   15,   34,   34,   34,   34,   34,   34,
   89,   34,   35,   35,   90,   91,   34,   35,   35,   95,
   35,   35,   35,   35,   35,   35,   97,   35,   36,   36,
  115,  116,   35,   36,   36,  113,   36,   36,   36,   36,
   36,   36,  114,   36,   72,   73,  117,  118,   36,   90,
   53,   53,   74,   75,   76,   77,   78,   79,   54,   54,
   53,   55,   55,   72,   73,   99,  100,  119,   54,  107,
  108,   55,  122,  123,  126,   91,   46,   26,   27,   98,
  124,   37,   88,  112,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          1,
    2,   16,   13,   43,   33,  257,  260,  261,  257,    7,
  257,   13,  278,  279,   16,   44,   31,  257,  258,  281,
   22,  257,  258,  281,  286,  257,  280,  267,   26,   31,
  257,  293,  265,  266,  274,  287,   38,   39,  274,  257,
  274,  257,  275,   83,   84,   74,   75,   76,   77,   78,
   79,  257,  274,  257,  264,   57,  296,  265,  266,  257,
  296,  288,  289,  290,  291,  294,  282,  275,  295,  259,
  288,   82,  288,  291,  292,  291,  282,  295,  257,  295,
   82,  286,  288,  276,  288,  291,  290,  291,  293,  295,
  288,  295,  257,  291,  109,  278,  279,  295,  260,  261,
  274,  265,  266,  275,  283,  284,  285,  109,  274,  120,
  125,  275,  275,  275,  262,  263,  260,  261,  120,  265,
  266,  265,  266,  125,  268,  269,  270,  271,  272,  273,
  280,  275,  260,  261,  258,  259,  280,  265,  266,  280,
  268,  269,  270,  271,  272,  273,  276,  275,  260,  261,
  277,  278,  280,  265,  266,  280,  268,  269,  270,  271,
  272,  273,  280,  275,  260,  261,  277,  278,  280,  258,
  265,  266,  268,  269,  270,  271,  272,  273,  265,  266,
  275,  265,  266,  260,  261,   72,   73,  275,  275,   80,
   81,  275,  275,  258,  280,  259,  275,    6,    6,   71,
  118,   22,   57,   85,
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
"def_tipo : TYPE ID AS lista PUNTO_Y_COMA",
"lista : lista_num",
"lista : lista_str",
"lista_num : COR_ABRE lis_num_c COR_CIERRA",
"lis_num_c : CTE_NUM",
"lis_num_c : lis_num_c COMA CTE_NUM",
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
"condicion : OP_NEGACION comparacion",
"condicion : comparacion AND comparacion",
"condicion : comparacion OR comparacion",
"comparacion : expresion OP_IGUAL expresion",
"comparacion : expresion OP_DISTINTO expresion",
"comparacion : expresion OP_MAYOR expresion",
"comparacion : expresion OP_MENOR expresion",
"comparacion : expresion OP_MAYOR_IGUAL expresion",
"comparacion : expresion OP_MENOR_IGUAL expresion",
"bucle : REPEAT sentencias UNTIL PAR_ABRE condicion PAR_CIERRA PUNTO_Y_COMA",
"display_command : DISPLAY PAR_ABRE CTE_STR PAR_CIERRA PUNTO_Y_COMA",
"average : AVG PAR_ABRE lista_num PAR_CIERRA",
"id : ID ID",
"cte_num : CTE_NUM ID",
"cte_str : CTE_STR",
};

//#line 105 "input.y"
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
//#line 374 "Parser.java"
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
//#line 13 "input.y"
{System.out.println("Compila OK!");}
break;
case 5:
//#line 19 "input.y"
{System.out.println(yyval.sval);}
break;
case 6:
//#line 20 "input.y"
{System.out.println(yyval.sval);}
break;
case 7:
//#line 22 "input.y"
{yyval.sval = "TYPE " + "XXXXXXXXXX" + " AS " + val_peek(4).sval + ";"; }
break;
case 8:
//#line 24 "input.y"
{yyval.sval = val_peek(0).sval;}
break;
case 9:
//#line 25 "input.y"
{yyval.sval = val_peek(0).sval;}
break;
case 10:
//#line 27 "input.y"
{yyval.sval = "[" + val_peek(2).sval + "]";}
break;
case 11:
//#line 29 "input.y"
{yyval.sval = TablaDeSimbolos.getInstance().getPos(yylval.ival);}
break;
case 12:
//#line 30 "input.y"
{yyval.sval = val_peek(2).sval + "," + TablaDeSimbolos.getInstance().getPos(yylval.ival);}
break;
case 13:
//#line 32 "input.y"
{yyval.sval = "[" + val_peek(2).sval + "]";}
break;
case 14:
//#line 34 "input.y"
{yyval.sval = val_peek(0).sval;}
break;
case 15:
//#line 35 "input.y"
{yyval.sval = val_peek(2).sval + "," + val_peek(1).sval;}
break;
case 17:
//#line 39 "input.y"
{yyval.sval = val_peek(3).sval + ":" + val_peek(2).sval + ";";}
break;
case 18:
//#line 40 "input.y"
{yyval.sval = val_peek(4).sval + "\n" + val_peek(3).sval + ":" + val_peek(2).sval + ";";}
break;
case 19:
//#line 42 "input.y"
{yyval.sval = val_peek(0).sval;}
break;
case 20:
//#line 43 "input.y"
{yyval.sval = val_peek(2).sval + "," + val_peek(1).sval;}
break;
case 21:
//#line 45 "input.y"
{yyval.sval = "FLOAT";}
break;
case 22:
//#line 46 "input.y"
{yyval.sval = "STRING";}
break;
case 23:
//#line 47 "input.y"
{yyval.sval = "POINTER";}
break;
case 24:
//#line 48 "input.y"
{yyval.sval = val_peek(0).sval;}
break;
case 25:
//#line 50 "input.y"
{yyval.sval = "BEGIN " + val_peek(2).sval + " END";}
break;
case 26:
//#line 51 "input.y"
{yyval.sval = "BEGIN " + " END";}
break;
case 29:
//#line 56 "input.y"
{yyval.sval = val_peek(0).sval;}
break;
case 33:
//#line 61 "input.y"
{yyval.sval = val_peek(3).sval + " = " + val_peek(2).sval + ";";}
break;
case 35:
//#line 64 "input.y"
{yyval.sval = val_peek(2).sval + " + " + val_peek(1).sval;}
break;
case 36:
//#line 65 "input.y"
{yyval.sval = val_peek(2).sval + " - " + val_peek(1).sval;}
break;
case 38:
//#line 68 "input.y"
{yyval.sval = val_peek(2).sval + " * " + val_peek(1).sval;}
break;
case 39:
//#line 69 "input.y"
{yyval.sval = val_peek(2).sval + " / " + val_peek(1).sval;}
break;
case 40:
//#line 71 "input.y"
{yyval.sval = val_peek(0).sval;}
break;
case 41:
//#line 72 "input.y"
{yyval.sval = val_peek(0).sval;}
break;
case 42:
//#line 73 "input.y"
{yyval.sval = "(" + val_peek(2).sval + ")";}
break;
case 43:
//#line 74 "input.y"
{yyval.sval = val_peek(0).sval;}
break;
case 47:
//#line 80 "input.y"
{val_peek(1).sval = "!";}
break;
case 48:
//#line 81 "input.y"
{val_peek(1).sval = "&&";}
break;
case 49:
//#line 82 "input.y"
{val_peek(1).sval = "||";}
break;
case 50:
//#line 84 "input.y"
{val_peek(1).sval = "==";}
break;
case 51:
//#line 85 "input.y"
{val_peek(1).sval = "!=";}
break;
case 52:
//#line 86 "input.y"
{val_peek(1).sval = ">";}
break;
case 53:
//#line 87 "input.y"
{val_peek(1).sval = "<";}
break;
case 54:
//#line 88 "input.y"
{val_peek(1).sval = ">=";}
break;
case 55:
//#line 89 "input.y"
{val_peek(1).sval = "<=";}
break;
case 59:
//#line 97 "input.y"
{yyval.sval = TablaDeSimbolos.getInstance().getPos(yylval.ival);}
break;
case 60:
//#line 99 "input.y"
{yyval.sval = TablaDeSimbolos.getInstance().getPos(yylval.ival);}
break;
case 61:
//#line 101 "input.y"
{yyval.sval = "\"" + TablaDeSimbolos.getInstance().getPos(yylval.ival) + "\"";}
break;
//#line 698 "Parser.java"
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
