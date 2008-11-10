package compilador.semantica;

import compilador.parser.ParserVal;
import compilador.parser.Parser;
import compilador.beans.EntradaTS;
import compilador.beans.TablaDeSimbolos;

public class FinalizarConstante implements IRutinaSemantica {

	public int execute(char c, StringBuffer token, ParserVal yylval) {
		float num = Float.parseFloat((token.toString()));
		
		if( (num < TAMANIO_MINIMO_CTE || num > TAMANIO_MAXIMO_CTE) && num != 0) {
			System.out.println("ERROR: Tamanio de Constante fuera de rango");
			return Parser.ERROR_LEXICO;
		}

/* Se modifican cosas... cualquier problema volver a la rev 98 */
		
		yylval.ival = TablaDeSimbolos.getInstance().agregarConstante(token.toString());
		token.delete(0,token.length());
		token.append("CTE_NUM");
		return Parser.CTE_NUM;
	}

}
