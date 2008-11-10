package compilador.semantica;

import compilador.parser.Parser;
import compilador.parser.ParserVal;

public class ContinuarConstanteString implements IRutinaSemantica {

	public int execute(char c, StringBuffer token, ParserVal yylval) {
		token.append(c);
		return Parser.INCOMPLETO;
	}

}
