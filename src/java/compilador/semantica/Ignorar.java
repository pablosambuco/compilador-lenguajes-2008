package compilador.semantica;

import compilador.parser.ParserVal;
import compilador.parser.Parser;

public class Ignorar implements IRutinaSemantica {

	public int execute(char c, StringBuffer token, ParserVal yylval) {
		return Parser.INCOMPLETO;
	}

}
