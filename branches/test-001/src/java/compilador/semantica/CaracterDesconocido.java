package compilador.semantica;

import compilador.util.TipoToken;

public class CaracterDesconocido implements IRutinaSemantica {

	public int execute(char c, StringBuffer token) {
		System.out.print("Caracter Desconocido: " + "\"" + c + "\" ");
		return TipoToken.ERROR_LEXICO;
	}

}
