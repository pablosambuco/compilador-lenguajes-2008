package compilador.semantica;

import compilador.parser.ParserVal;
import compilador.parser.Parser;
import compilador.beans.EntradaTS;
import compilador.beans.PalabrasReservadas;
import compilador.beans.TablaDeSimbolos;

public class FinalizarId implements IRutinaSemantica {

	public int execute(char c, StringBuffer token, ParserVal yylval) {
		int pr = PalabrasReservadas.getInstance().contiene(token);
		
		if (pr == Parser.ID) {
			//Si es un identificador (NO una palabra reservada), se verifica la cantidad de caracteres
			if(token.length() > TablaDeSimbolos.TAMANIO_MAXIMO_TOKEN) {
				System.out.println("(ERROR: Tamanio de Identificador demasiado largo.)");
				return Parser.ERROR_LEXICO;
			}
		
			yylval.ival = TablaDeSimbolos.getInstance().agregar(new EntradaTS(token.toString()));
			token.delete(0,token.length());
			token.append("ID");
		}
		
		// Se devuelve como tipo de token el numero de palabra reservada o Parser.ID		
		return pr;
	}
	
}
