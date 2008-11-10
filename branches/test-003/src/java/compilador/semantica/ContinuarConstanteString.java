package compilador.semantica;

import compilador.beans.TablaDeSimbolos;
import compilador.parser.ParserVal;
import compilador.parser.Parser;

public class ContinuarConstanteString implements IRutinaSemantica {

	public int execute(char c, StringBuffer token, ParserVal yylval) {
		if(token.length() > TablaDeSimbolos.TAMANIO_MAXIMO_CTE_STRING) {
		} else {
			token.append(c);
		}
		return Parser.INCOMPLETO;
	}

}
