package compilador.util;

public class TipoToken {

	/* TIPOS DE TOKEN */
	/* NOTA: colocamos al principio aquellos que requieran tabla de simbolos */

	public static final int ID 				= 257;
	public static final int CTE_NUM 		= 258;
	public static final int CTE_STR 		= 259;
	public static final int OP_SUMA 		= 260;
	public static final int OP_RESTA 		= 261;
	public static final int OP_MUL 			= 262;
	public static final int OP_DIV 			= 263;
	public static final int OP_ASIG 		= 264;
	public static final int AND 			= 265;
	public static final int OR 				= 266;
	public static final int OP_NEGACION 	= 267;
	public static final int OP_IGUAL 		= 268;
	public static final int OP_DISTINTO 	= 269;
	public static final int OP_MAYOR 		= 270;
	public static final int OP_MENOR 		= 271;
	public static final int OP_MAYOR_IGUAL 	= 272;
	public static final int OP_MENOR_IGUAL 	= 273;
	public static final int PAR_ABRE 		= 274;
	public static final int PAR_CIERRA 		= 275;
	public static final int COR_ABRE 		= 276;
	public static final int COR_CIERRA 		= 277;
	public static final int COMA 			= 278;
	public static final int DOS_PUNTOS 		= 279;
	public static final int PUNTO_Y_COMA 	= 280;
	
	public static final int INCOMPLETO 		= 0; //cuando el automata devuelva esto, yyparse() terminara de parsear ya que no estara recibiendo mas tokens

	public static final int PR = 281;
	
	
	/* En realidad este Tipo no se usa directamente, las palabras reservadas tienen cada una
	 * su propio tipo; son consecutivos y empiezan en PR. 
	 */
	
	public static final int ERROR_LEXICO = -1;
}
