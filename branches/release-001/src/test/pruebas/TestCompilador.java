package pruebas;

import junit.framework.TestCase;

public class TestCompilador extends TestCase {

	public void testCompilador(){
		String []argumentos = {new String("src/test/pruebas/programa.txt")};
		Main.main(argumentos);
	}

}
