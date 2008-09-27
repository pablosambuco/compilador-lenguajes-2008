package compilador.semantica;

import compilador.parser.ParserVal;
import compilador.util.TipoToken;

public class ContinuarConstanteString implements IRutinaSemantica {

	public int execute(char c, StringBuffer token, ParserVal yylval) {
		if(token.length() > TAMANIO_MAXIMO_CTE_STRING) {
			//TODO para mi (Pablo S.) este control va sólo en el finalizar por ahora
			//System.out.println("ERROR: Tamanio de Constante String demasiado largo");
		} else {
			token.append(c);
		}
		return TipoToken.INCOMPLETO;
	}

}
