package compilador.semantica;

import compilador.util.TipoToken;

public class PuntoYComa implements IRutinaSemantica {

	public int execute(char c, StringBuffer token) {
		token.append("< ; >");
		return TipoToken.PUNTO_Y_COMA;
	}

}
