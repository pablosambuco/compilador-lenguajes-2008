package compilador.semantica;

import compilador.util.TipoToken;

public class DosPuntos implements IRutinaSemantica {

	public int execute(char c, StringBuffer token) {
		token.append("< : >");
		return TipoToken.DOS_PUNTOS;
	}

}
