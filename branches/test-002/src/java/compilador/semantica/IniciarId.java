package compilador.semantica;

import compilador.parser.ParserVal;
import compilador.util.TipoToken;

public class IniciarId implements IRutinaSemantica {

	public int execute(char c, StringBuffer token, ParserVal yylval) {
		token.append(c);
		return TipoToken.INCOMPLETO;
	}

}
