package compilador.beans;

import java.util.Hashtable;
import compilador.util.TipoToken;

public class PalabrasReservadas {

	private static java.util.Hashtable<String, Integer> palabras;
	private static PalabrasReservadas instance;

	//Las PR son consecutivas, el inicio se configura en TipoToken
	private static int palabra = TipoToken.PR;
	
	public static PalabrasReservadas getInstance() {
		if(instance == null)
			instance = new PalabrasReservadas();
		return instance;		
	}
	
	private PalabrasReservadas()
	{
		palabras = new Hashtable<String, Integer>();
		
		agregar("BEGIN");
		agregar("END");		
		agregar("STRING");
		agregar("FLOAT");
		agregar("POINTER");
		agregar("DEFVAR");
		agregar("ENDDEF");
		agregar("IF");
		agregar("ELSE");
		agregar("ENDIF");
		agregar("REPEAT");
		agregar("UNTIL");
		agregar("TYPE");
		agregar("AS");
		agregar("DISPLAY");
		agregar("AVG");
	}
	
	public int contiene(StringBuffer token) {
		Integer valor = (Integer) palabras.get(token.toString());
		if(valor == null)
			return TipoToken.ID;
		else
			return valor.intValue();
	}
	
	private void agregar(String key)
	{
		palabras.put(key, new Integer(palabra++));		
	}
}
