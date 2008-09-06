package compilador.semantica;

import compilador.util.TipoToken;

public class CorcheteAbre implements IRutinaSemantica {

	public int execute(char c, StringBuffer token) {
		token.append("< [ >");
		return TipoToken.CORCHETE_ABRE;
	}

}
