package compilador.semantica;

import compilador.parser.ParserVal;
import compilador.util.TipoToken;

public class And implements IRutinaSemantica {

	public int execute(char c, StringBuffer token, ParserVal yylval) {
		token.append("&&");
		return TipoToken.AND;
	}

}
