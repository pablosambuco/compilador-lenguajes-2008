package compilador.semantica;

import compilador.parser.ParserVal;
import compilador.parser.Parser;

public class DosPuntos implements IRutinaSemantica {

	public int execute(char c, StringBuffer token, ParserVal yylval) {
		token.append(":");
		return Parser.DOS_PUNTOS;
	}

}
