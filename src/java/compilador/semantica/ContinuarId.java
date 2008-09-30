package compilador.semantica;

import compilador.parser.ParserVal;
import compilador.parser.Parser;

public class ContinuarId implements IRutinaSemantica {

	public int execute(char c, StringBuffer token, ParserVal yylval) {
		if(token.length() > TAMANIO_MAXIMO_TOKEN) {
			//TODO esto es un error y la verdad que no se como deberiamos tratarlo
			System.out.println("ERROR: Tamanio de ID demasiado largo");
		} else {
			token.append(c);
		}
		return Parser.INCOMPLETO;

	}

}
