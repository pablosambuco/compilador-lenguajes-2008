package compilador.semantica;

import compilador.util.TipoToken;

public class IniciarConstante implements IRutinaSemantica {

	public int execute(char c, StringBuffer token) {
		token.append(c);
		return TipoToken.INCOMPLETO;
	}

}
