package compilador.semantica;

public interface IRutinaSemantica {
	
	public static final float TAMANIO_MAXIMO_TOKEN = 15;
	public static final float TAMANIO_MAXIMO_CTE = 99999999; //TODO cambiar
	public static final float TAMANIO_MAXIMO_CTE_STRING = 30;
	
	public int execute(char c, StringBuffer token);

}
