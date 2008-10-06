package compilador.semantica;

import compilador.parser.ParserVal;
import compilador.parser.Parser;
import compilador.beans.TablaDeSimbolos;

public class FinalizarConstanteString implements IRutinaSemantica {

	public int execute(char c, StringBuffer token, ParserVal yylval) {
		
		if (token.length() > TAMANIO_MAXIMO_CTE_STRING) {
			System.out.println("(ERROR: Tamanio de Constante String demasiado largo.)");
			//token.setLength(TAMANIO_MAXIMO_CTE_STRING);
			return Parser.ERROR_LEXICO;
		}
		
		yylval.ival = TablaDeSimbolos.getInstance().agregar(token);
		token.delete(0,token.length());
		token.append("CTE_STR");
		return Parser.CTE_STR;
	}

}
