package compilador.semantica;

import compilador.util.TipoToken;

public class FinalizarId implements IRutinaSemantica {

	public int execute(char c, StringBuffer token) {
		//TODO Verificar si es reservada
		//TODO Agregar a la tabla de simbolos si no lo es
		
		//llevamos la cadena a la forma <ID: token>
		token.insert(0, "<ID: ");
		token.append(">");
		return TipoToken.ID;
	}

}
