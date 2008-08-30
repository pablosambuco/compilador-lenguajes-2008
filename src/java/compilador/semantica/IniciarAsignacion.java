package compilador.semantica;

import compilador.util.TipoToken;

public class IniciarAsignacion implements IRutinaSemantica {

	public int execute(char c, StringBuffer token) {
		return TipoToken.INCOMPLETO;
	}

}
