package compilador.semantica;

import compilador.parser.ParserVal;
import compilador.beans.PalabrasReservadas;
import compilador.beans.TablaDeSimbolos;
import compilador.util.TipoToken;

public class FinalizarId implements IRutinaSemantica {

	public int execute(char c, StringBuffer token, ParserVal yylval) {
		int pr = PalabrasReservadas.getInstance().contiene(token);
		
		// Se va a devolver como tipo de token el numero de palabra reservada
		if (pr == TipoToken.ID) {
			yylval.ival = TablaDeSimbolos.getInstance().agregar(token);
			token.delete(0,token.length());
			token.append("ID");
		}
		
		return pr;
	}
	
}
