package compilador.semantica;

import compilador.parser.ParserVal;
import compilador.util.TipoToken;

public class ParentesisCierra implements IRutinaSemantica {

	public int execute(char c, StringBuffer token, ParserVal yylval) {
		token.append(")");
		return TipoToken.PAR_CIERRA;
	}

}
