package compilador.semantica;

import compilador.parser.ParserVal;
import compilador.parser.Parser;
import compilador.beans.EntradaTS;
import compilador.beans.TablaDeSimbolos;

public class FinalizarConstanteString implements IRutinaSemantica {

	public int execute(char c, StringBuffer token, ParserVal yylval) {
		
		if (token.length() > TablaDeSimbolos.TAMANIO_MAXIMO_CTE_STRING) {
			System.out.println("(ERROR: Tamanio de Constante String demasiado largo.)");
			//token.setLength(TAMANIO_MAXIMO_CTE_STRING);
			return Parser.ERROR_LEXICO;
		}
		
		//creamos una entrada en la tabla de simbolos y le seteamos ciertos atributos
		EntradaTS entrada = new EntradaTS("@"+token.toString());
		entrada.setTipo(TablaDeSimbolos.TIPO_CTE_STRING);
		entrada.setValor(token.toString());
		entrada.setLongitud(String.valueOf(token.length()));
		
		yylval.ival = TablaDeSimbolos.getInstance().agregar(entrada);
		token.delete(0,token.length());
		token.append("CTE_STR");
		return Parser.CTE_STR;
	}

}
