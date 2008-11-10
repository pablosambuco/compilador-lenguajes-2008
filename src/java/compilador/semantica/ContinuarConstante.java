package compilador.semantica;

import compilador.parser.ParserVal;
import compilador.parser.Parser;

public class ContinuarConstante implements IRutinaSemantica {

	public int execute(char c, StringBuffer token, ParserVal yylval) {
		
		//TODO - Revisar. Si tomamos numeros negativos conviene guardar el modulo.
		float num = Float.parseFloat((token.toString()));
		
		if( (num < TAMANIO_MINIMO_CTE || num > TAMANIO_MAXIMO_CTE) && num != 0) {
			//TODO esto es un error y la verdad que no se como deberiamos tratarlo
			//System.out.println("ERROR: Tamanio de Constante fuera de rango");
		} else {
			token.append(c);
		}
		return Parser.INCOMPLETO;
	}

}
