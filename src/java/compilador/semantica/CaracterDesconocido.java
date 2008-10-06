package compilador.semantica;

import compilador.parser.Parser;
import compilador.parser.ParserVal;

public class CaracterDesconocido implements IRutinaSemantica {

	public int execute(char c, StringBuffer token, ParserVal yylval) {
		System.out.print("Caracter Desconocido: " + "\"" + c + "\" ");
		return Parser.ERROR_LEXICO;
	}

}
