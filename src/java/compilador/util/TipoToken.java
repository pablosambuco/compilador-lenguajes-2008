package compilador.util;

public class TipoToken {

	/* TIPOS DE TOKEN */
	/* NOTA: colocamos al principio aquellos que requieran tabla de simbolos */

	public static final int ID 				= 0;
	public static final int CTE_NUM 		= 1;
	public static final int CTE_STR 		= 2;
	public static final int OP_SUMA 		= 3;
	public static final int OP_RESTA 		= 4;
	public static final int OP_MUL 			= 5;
	public static final int OP_DIV 			= 6;
	public static final int OP_ASIG 		= 7;
	public static final int AND 			= 8;
	public static final int OR 				= 9;
	public static final int OP_NEGACION 	= 10;
	public static final int OP_IGUAL 		= 11;
	public static final int OP_DISTINTO 	= 12;
	public static final int OP_MAYOR 		= 13;
	public static final int OP_MENOR 		= 14;
	public static final int OP_MAYOR_IGUAL 	= 15;
	public static final int OP_MENOR_IGUAL 	= 16;
	public static final int PAR_ABRE 		= 17;
	public static final int PAR_CIERRA 		= 18;
	public static final int COR_ABRE 		= 19;
	public static final int COR_CIERRA 		= 20;
	public static final int COMA 			= 21;
	public static final int DOS_PUNTOS 		= 22;
	public static final int PUNTO_Y_COMA 	= 23;
	
	public static final int INCOMPLETO 		= 100;
	
	/* En realidad este Tipo no se usa directamente, las palabras reservadas tienen cada una
	 * su propio tipo; son consecutivos y empiezan en PR. 
	 */
	public static final int PR = 200;
	
	public static final int ERROR_LEXICO = -1;
}
