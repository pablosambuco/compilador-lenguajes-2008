package compilador.semantica;

import compilador.util.TipoToken;

public class OperadorSuma implements IRutinaSemantica {

	public int execute(char c, StringBuffer token) {
		token.append("< + >");
		return TipoToken.OP_SUMA;
	}

}
