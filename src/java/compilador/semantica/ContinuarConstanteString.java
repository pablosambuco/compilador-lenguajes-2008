package compilador.semantica;

import compilador.util.TipoToken;

public class ContinuarConstanteString implements IRutinaSemantica {

	public int execute(char c, StringBuffer token) {
		if(token.length() > TAMANIO_MAXIMO_CTE_STRING) {
			//TODO esto es un error y la verdad que no se como deberiamos tratarlo
			System.out.println("ERROR: Tamanio de Constante String demasiado largo");
			return TipoToken.INCOMPLETO;
		} else {
			token.append(c);
		}
		return TipoToken.INCOMPLETO;
	}

}
