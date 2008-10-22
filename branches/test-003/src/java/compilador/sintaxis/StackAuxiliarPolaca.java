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
		return stack.pop().intValue(); //si la pila est� vac�a esto revienta (lo cual est� bien, porque se considera incorrecto devolver algun otro valor ya que eso limitar�a lo que la pila en s� puede almacenar)
	}
	
	public int getTamanio() {
		return stack.size();
	}
}
