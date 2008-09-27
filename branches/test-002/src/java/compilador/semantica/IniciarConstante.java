package compilador.semantica;

import compilador.parser.ParserVal;
import compilador.util.TipoToken;

public class IniciarConstante implements IRutinaSemantica {

	public int execute(char c, StringBuffer token, ParserVal yylval) {
		if(c == '.') {
			token.append("0");
		}
		token.append(c);
		return TipoToken.INCOMPLETO;
	}

}
