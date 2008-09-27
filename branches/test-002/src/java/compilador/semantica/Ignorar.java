package compilador.semantica;

import compilador.parser.ParserVal;
import compilador.util.TipoToken;

public class Ignorar implements IRutinaSemantica {

	public int execute(char c, StringBuffer token, ParserVal yylval) {
		return TipoToken.INCOMPLETO;
	}

}
