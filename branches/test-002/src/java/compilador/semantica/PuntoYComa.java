package compilador.semantica;

import compilador.parser.Parser;
import compilador.parser.ParserVal;

public class PuntoYComa implements IRutinaSemantica {

	public int execute(char c, StringBuffer token, ParserVal yylval) {
		token.append(";");
		return Parser.PUNTO_Y_COMA;
	}

}
