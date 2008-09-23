package compilador.semantica;

import compilador.util.TipoToken;

public class IniciarConstanteString implements IRutinaSemantica {

	public int execute(char c, StringBuffer token) {
		//me llegó una comilla, la cual ignoramos
		return TipoToken.INCOMPLETO;
	}

}
