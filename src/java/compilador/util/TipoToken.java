package compilador.util;

public class TipoToken {

	/* TIPOS DE TOKEN */
	/* NOTA: colocamos al principio aquellos que requieran tabla de simbolos */

	public static final int ID = 0;
	public static final int CTE = 1;
	public static final int PR = 10;
	public static final int OPMAS = 11;
	public static final int OPMENOS = 12;
	public static final int OPMUL = 13;
	public static final int OPDIV = 14;
	public static final int P_CIERRA = 15;
	public static final int P_ABRE = 16;
	public static final int OPASIG = 17;
	
	public static final int INCOMPLETO = 255;
	public static final int ERROR_LEXICO = -1;
}
