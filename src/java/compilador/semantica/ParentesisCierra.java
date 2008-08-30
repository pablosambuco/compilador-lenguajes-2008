package compilador.semantica;

import compilador.util.TipoToken;

public class ParentesisCierra implements IRutinaSemantica {

	public int execute(char c, StringBuffer token) {
		token.append("<P_CIERRA>");
		return TipoToken.P_CIERRA;
	}

}
