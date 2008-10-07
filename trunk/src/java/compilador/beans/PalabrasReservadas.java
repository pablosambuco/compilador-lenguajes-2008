package compilador.beans;

import java.util.Hashtable;

import compilador.parser.Parser;

public class PalabrasReservadas {

	private static java.util.Hashtable<String, Integer> palabras;
	private static PalabrasReservadas instance;

	public static PalabrasReservadas getInstance() {
		if(instance == null)
			instance = new PalabrasReservadas();
		return instance;		
	}
	
	private PalabrasReservadas()
	{
		palabras = new Hashtable<String, Integer>();
		
		agregar("BEGIN",Parser.BEGIN);
		agregar("END",Parser.END);		
		agregar("STRING",Parser.STRING);
		agregar("FLOAT",Parser.FLOAT);
		agregar("POINTER",Parser.POINTER);
		agregar("DEFVAR",Parser.DEFVAR);
		agregar("ENDDEF",Parser.ENDDEF);
		agregar("IF",Parser.IF);
		agregar("ELSE",Parser.ELSE);
		agregar("ENDIF",Parser.ENDIF);
		agregar("REPEAT",Parser.REPEAT);
		agregar("UNTIL",Parser.UNTIL);
		agregar("TYPE",Parser.TYPE);
		agregar("AS",Parser.AS);
		agregar("DISPLAY",Parser.DISPLAY);
		agregar("AVG",Parser.AVG);
	}
	
	public int contiene(StringBuffer token) {
		Integer valor = (Integer) palabras.get(token.toString());
		if(valor == null)
			return Parser.ID;
		else
			return valor.intValue();
	}
	
	private void agregar(String key, int numero)
	{
		palabras.put(key, new Integer(numero));		
	}
}
