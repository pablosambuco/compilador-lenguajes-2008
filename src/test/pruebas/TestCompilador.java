package pruebas;

import junit.framework.TestCase;

import compilador.parser.Parser;

public class TestCompilador extends TestCase {

	public void testCompilador(){
		String []argumentos = {new String("src/test/pruebas/programa.txt")};
		Parser.main(argumentos);
	}

}
