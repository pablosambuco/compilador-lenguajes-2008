package compilador.semantica;

import compilador.util.TipoToken;

public class OperadorMultiplicacion implements IRutinaSemantica {

	public int execute(char c, StringBuffer token) {
		token.append("<OPMUL>");
		return TipoToken.OPMUL;
	}

}
