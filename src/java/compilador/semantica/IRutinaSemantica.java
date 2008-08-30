package compilador.semantica;

public interface IRutinaSemantica {
	
	public static final int TAMANIO_MAXIMO_TOKEN = 15;
	public static final int TAMANIO_MAXIMO_CTE = 99999999; //TODO cambiar
	
	public int execute(char c, StringBuffer token);

}
