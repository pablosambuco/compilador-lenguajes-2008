package compilador.semantica;

import compilador.parser.ParserVal;
import compilador.parser.Parser;
import compilador.beans.TablaDeSimbolos;

public class FinalizarConstante implements IRutinaSemantica {

	public int execute(char c, StringBuffer token, ParserVal yylval) {
		yylval.ival = TablaDeSimbolos.getInstance().agregar(token);
		token.delete(0,token.length());
		token.append("CTE_NUM");
		return Parser.CTE_NUM;
	}

}
