package compilador.util;

public class TipoToken {

	/* TIPOS DE TOKEN */
	/* NOTA: colocamos al principio aquellos que requieran tabla de simbolos */

	public static final int ID = 0;
	public static final int CTE = 1;
	public static final int CTE_STRING = 2;
	public static final int OP_MAS = 2;
	public static final int OP_MENOS = 4;
	public static final int OP_MUL = 5;
	public static final int OP_DIV = 6;
	public static final int OP_ASIG = 7;
	public static final int OP_COMPARACION = 8;
	public static final int OP_NEGACION = 9;
	public static final int OP_DISTINTO = 10;
	public static final int AND = 11;
	public static final int OR = 12;
	public static final int OP_MAYOR = 13;
	public static final int OP_MENOR = 14;
	public static final int OP_MAYOR_IGUAL = 15;
	public static final int OP_MENOR_IGUAL = 16;
	public static final int PARENTESIS_ABRE = 17;
	public static final int PARENTESIS_CIERRA = 18;
	public static final int CORCHETE_ABRE = 19;
	public static final int CORCHETE_CIERRA = 20;
	public static final int COMA = 21;
	public static final int DOS_PUNTOS = 22;
	public static final int PUNTO_Y_COMA = 23;
	
	public static final int PR = 100;
	
	public static final int INCOMPLETO = 255;
	public static final int ERROR_LEXICO = -1;
}
