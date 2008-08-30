package compilador.semantica;

import compilador.util.TipoToken;

public class Ignorar implements IRutinaSemantica {

	public int execute(char c, StringBuffer token) {
		return TipoToken.INCOMPLETO;
	}

}
