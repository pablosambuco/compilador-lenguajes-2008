package compilador.semantica;

import compilador.util.TipoToken;

public class ContinuarConstante implements IRutinaSemantica {

	public int execute(char c, StringBuffer token) {
		if(Integer.parseInt(token.toString()) > TAMANIO_MAXIMO_CTE) {
			//TODO esto es un error y la verdad que no se como deberiamos tratarlo
			System.out.println("ERROR: Tamanio de Constante demasiado largo");
		} else {
			token.append(c);
		}
		return TipoToken.INCOMPLETO;
	}

}
