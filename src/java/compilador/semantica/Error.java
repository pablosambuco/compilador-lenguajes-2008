package compilador.semantica;

import compilador.util.TipoToken;

public class Error implements IRutinaSemantica {

	public int execute(char c, StringBuffer token) {
		token.append("¡ERROR_LEXICO!");
		return TipoToken.ERROR_LEXICO;
	}

}
