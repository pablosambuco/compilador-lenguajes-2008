package compilador.semantica;

import compilador.util.TipoToken;

public class ParentesisAbre implements IRutinaSemantica {

	public int execute(char c, StringBuffer token) {
		token.append("<P_ABRE>");
		return TipoToken.P_ABRE;
	}

}