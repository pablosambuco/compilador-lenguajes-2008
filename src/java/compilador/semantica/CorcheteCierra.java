package compilador.semantica;

import compilador.util.TipoToken;

public class CorcheteCierra implements IRutinaSemantica {

	public int execute(char c, StringBuffer token) {
		token.append("< ] >");
		return TipoToken.COR_CIERRA;
	}

}
