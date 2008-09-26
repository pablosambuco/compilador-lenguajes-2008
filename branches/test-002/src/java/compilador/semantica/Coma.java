package compilador.semantica;

import compilador.util.TipoToken;

public class Coma implements IRutinaSemantica {

	public int execute(char c, StringBuffer token) {
		token.append(",");
		return TipoToken.COMA;
	}

}
