package compilador.semantica;

import compilador.util.TipoToken;

public class FinalizarConstanteString implements IRutinaSemantica {

	public int execute(char c, StringBuffer token) {
		//FIXME Agregar a la tabla de simbolos si no lo es
		
		//llevamos la cadena a la forma <CTE: token>
		token.insert(0, "<CTE_STRING: \"");
		token.append("\" >");
		return TipoToken.CTE_STRING;
	}

}
