package compilador.semantica;

import compilador.util.TipoToken;

public class IniciarConstante implements IRutinaSemantica {

	public int execute(char c, StringBuffer token) {
		if(c == '.') {
			token.append("0");
		}
		token.append(c);
		return TipoToken.INCOMPLETO;
	}

}
