package compilador.semantica;

import compilador.parser.ParserVal;
import compilador.parser.Parser;

public class Error implements IRutinaSemantica {

	public int execute(char c, StringBuffer token, ParserVal yylval) {
		
		token.delete(0, token.length()); //borramos lo que haya hasta ahora en el token 
		token.append("�ERROR_LEXICO!"); //seteamos el error
		return Parser.ERROR_LEXICO;
	}

}
