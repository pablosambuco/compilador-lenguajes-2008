package compilador.semantica;

import compilador.util.TipoToken;

public class FinalizarAsignacion implements IRutinaSemantica {

	public int execute(char c, StringBuffer token) {
		token.append("<OPASIG>");
		return TipoToken.OPASIG;
	}

}
