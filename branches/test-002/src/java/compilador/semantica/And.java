package compilador.semantica;

import compilador.util.TipoToken;

public class And implements IRutinaSemantica {

	public int execute(char c, StringBuffer token) {
		token.append("&&");
		return TipoToken.AND;
	}

}
