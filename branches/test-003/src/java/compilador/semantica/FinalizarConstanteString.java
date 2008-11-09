package compilador.semantica;

import compilador.beans.TablaDeSimbolos;
import compilador.parser.Parser;
import compilador.parser.ParserVal;

public class FinalizarConstanteString implements IRutinaSemantica {

	public int execute(char c, StringBuffer token, ParserVal yylval) {
		
		if (token.length() > TablaDeSimbolos.TAMANIO_MAXIMO_CTE_STRING) {
			System.out.println("(ERROR: Tamanio de Constante String demasiado largo.)");
			//token.setLength(TAMANIO_MAXIMO_CTE_STRING);
			return Parser.ERROR_LEXICO;
		}
		
		yylval.ival = TablaDeSimbolos.getInstance().agregarCadena(token.toString());
		token.delete(0,token.length());
		token.append("CTE_STR");
		return Parser.CTE_STR;
	}

}
