package compilador.semantica;

import compilador.analizadorLexicografico.Automata;
import compilador.beans.PalabrasReservadas;
import compilador.beans.TablaDeSimbolos;
import compilador.util.TipoToken;

public class FinalizarId implements IRutinaSemantica {

	public int execute(char c, StringBuffer token) {
		int pr = PalabrasReservadas.getInstance().contiene(token);
		
		// Se va a devolver como tipo de token el numero de palabra reservada
		if (pr == TipoToken.ID) {
			Automata.yylval = TablaDeSimbolos.getInstance().agregar(token);
			token.insert(0, "<ID: ");
			token.append(">");
			
		} else {
			token.insert(0, "<PR: ");
			token.append(">");
		}
		return pr;

	}

}
