package pruebas;

import junit.framework.TestCase;

import compilador.parser.Parser;

public class TestCompilador extends TestCase {

	public void testCompilador(){
		String []argumentos = {new String("src/test/pruebas/programa.txt")};
		Parser.main(argumentos);
	}
	
	/*public void testVectorPolaca(){
		VectorPolaca vector = VectorPolaca.getInstance();
		vector.agregar(new EntradaVectorPolaca("paolo"));
		vector.agregar(new EntradaVectorPolaca("paola"));
		vector.agregar(new EntradaVectorPolaca("gaston"));
		vector.agregar(new EntradaVectorPolaca("mario"),vector.getPosicionActual()+3);
		vector.imprimirVector();
	}*/
	
	/*public void testStackPolaca(){
		StackAuxiliarPolaca stack = StackAuxiliarPolaca.getInstance();
		
		stack.push(23);
		stack.push(34);
		stack.pop();
		stack.pop();
		stack.push(34);
		stack.pop();
		stack.pop(); //aca revienta
	}*/

}
