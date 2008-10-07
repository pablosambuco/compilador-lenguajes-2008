package compilador.semantica;

import compilador.parser.ParserVal;
import compilador.parser.Parser;

public class IniciarId implements IRutinaSemantica {

	public int execute(char c, StringBuffer token, ParserVal yylval) {
		token.append(c);
		return Parser.INCOMPLETO;
	}

}
