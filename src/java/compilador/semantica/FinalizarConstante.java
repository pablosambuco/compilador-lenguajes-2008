package compilador.semantica;

import compilador.analizadorLexicografico.Automata;
import compilador.beans.TablaDeSimbolos;
import compilador.util.TipoToken;

public class FinalizarConstante implements IRutinaSemantica {

	public int execute(char c, StringBuffer token) {
		Automata.yylval = TablaDeSimbolos.getInstance().agregar(token).hashCode();
		
		//llevamos la cadena a la forma <CTE: token>
		token.insert(0, "<CTE_NUM: ");
		token.append(">");
		return TipoToken.CTE_NUM;
	}

}
