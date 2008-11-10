package compilador.semantica;

import compilador.beans.TablaDeSimbolos;
import compilador.parser.Parser;
import compilador.parser.ParserVal;

public class FinalizarConstanteString implements IRutinaSemantica {

	public int execute(char c, StringBuffer token, ParserVal yylval) {
		
		if (token.length() > TablaDeSimbolos.TAMANIO_MAXIMO_CTE_STRING) {
			System.out.println("WARNING: Tamanio de Constante String demasiado largo\n" +
				"Se trunca a " + TablaDeSimbolos.TAMANIO_MAXIMO_CTE_STRING + " caracteres\n" +
				 "Cadena original: " + token.toString() + "\n" +
				 "Cadena truncada: " + token.substring(0, TablaDeSimbolos.TAMANIO_MAXIMO_CTE_STRING) + "\n");
			token.delete(TablaDeSimbolos.TAMANIO_MAXIMO_CTE_STRING, token.length());
			token.setLength(TablaDeSimbolos.TAMANIO_MAXIMO_CTE_STRING);
		}
		
		yylval.ival = TablaDeSimbolos.getInstance().agregarCadena(token.toString());
		token.delete(0,token.length());
		token.append("CTE_STR");
		return Parser.CTE_STR;
	}

}
