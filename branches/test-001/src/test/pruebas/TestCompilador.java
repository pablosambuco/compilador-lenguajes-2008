package pruebas;

import compilador.parser.Parser;

import junit.framework.TestCase;

public class TestCompilador extends TestCase {

	public void testCompilador(){

		String []argumentos = {new String("src/test/pruebas/programa.txt")};
		
		Parser.main(argumentos);
		
		//con esta l�nea se testea solo el analizador lexicografico
		//Main.main(argumentos);
		
	}

}
