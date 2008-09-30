package compilador.semantica;

import compilador.parser.Parser;
import compilador.parser.ParserVal;

public class ParentesisAbre implements IRutinaSemantica {

	public int execute(char c, StringBuffer token, ParserVal yylval) {
		token.append("(");
		return Parser.PAR_ABRE;
	}

}
