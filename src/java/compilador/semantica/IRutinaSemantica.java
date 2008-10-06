package compilador.semantica;

import compilador.parser.ParserVal;

public interface IRutinaSemantica {
	
	public static final int TAMANIO_MAXIMO_TOKEN = 15;
	public static final int TAMANIO_MAXIMO_CTE_STRING = 30;

	/* TODO - Cambiar estas constantes, tener en cuenta el 0 y n�meros negativos */
	public static final float TAMANIO_MINIMO_CTE = Float.MIN_VALUE; //TODO cambiar por los valores de C
	public static final float TAMANIO_MAXIMO_CTE = Float.MAX_VALUE; //TODO cambiar por los valores de C

	public int execute(char c, StringBuffer token, ParserVal yylval);

}
