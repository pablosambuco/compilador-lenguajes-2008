package compilador.semantica;

import compilador.util.TipoToken;

public class Error implements IRutinaSemantica {

	public int execute(char c, StringBuffer token) {
		
		token.delete(0, token.length()); //borramos lo que haya hasta ahora en el token 
		token.append("¡ERROR_LEXICO!"); //seteamos el error
		return TipoToken.ERROR_LEXICO;
	}

}
