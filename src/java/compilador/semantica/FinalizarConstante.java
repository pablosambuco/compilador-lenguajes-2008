package compilador.semantica;

import compilador.analizadorLexicografico.Automata;
import compilador.beans.TablaDeSimbolos;
import compilador.util.TipoToken;

public class FinalizarConstante implements IRutinaSemantica {

	public int execute(char c, StringBuffer token) {
		Automata.yylval = TablaDeSimbolos.getInstance().agregar(token);
		token.delete(0,token.length());
		token.append("CTE_NUM");
		return TipoToken.CTE_NUM;
	}

}
