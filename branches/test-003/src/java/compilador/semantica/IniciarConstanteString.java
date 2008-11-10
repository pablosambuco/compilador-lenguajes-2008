package compilador.semantica;

import compilador.parser.ParserVal;
import compilador.parser.Parser;

public class IniciarConstanteString implements IRutinaSemantica {

	public int execute(char c, StringBuffer token, ParserVal yylval) {
		//me llego una comilla, la cual ignoramos
		return Parser.INCOMPLETO;
	}

}
