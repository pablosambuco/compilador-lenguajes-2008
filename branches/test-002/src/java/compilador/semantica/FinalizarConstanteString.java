package compilador.semantica;

import compilador.parser.ParserVal;
import compilador.beans.TablaDeSimbolos;
import compilador.util.TipoToken;

public class FinalizarConstanteString implements IRutinaSemantica {

	public int execute(char c, StringBuffer token, ParserVal yylval) {
		if (token.length() > TAMANIO_MAXIMO_CTE_STRING) {
			System.out.println("(ERROR: Tamanio de Constante String demasiado largo. Truncado a 30)");
			token.setLength(30);
		}
		yylval.ival = TablaDeSimbolos.getInstance().agregar(token);
		token.delete(0,token.length());
		token.append("CTE_STR");
		return TipoToken.CTE_STR;
	}

}
