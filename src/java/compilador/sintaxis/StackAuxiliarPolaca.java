package compilador.sintaxis;

import java.util.Stack;

public class StackAuxiliarPolaca {

	private java.util.Stack<Integer> stack;

	private static StackAuxiliarPolaca instance;
	public static StackAuxiliarPolaca getInstance() {
		if (instance == null)
			instance = new StackAuxiliarPolaca();
		return instance;
	}
	private StackAuxiliarPolaca() {
		stack = new Stack<Integer>();
	}
	
	public void push(int valor) {
		stack.push(valor);
	}
	
	public int pop() {
		return stack.pop().intValue(); //si la pila está vacía esto revienta (lo cual está bien, porque se considera incorrecto devolver algun otro valor ya que eso limitaría lo que la pila en sí puede almacenar)
	}
	
	public int getTamanio() {
		return stack.size();
	}
}
