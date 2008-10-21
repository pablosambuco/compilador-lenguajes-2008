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
	
	public void push(Integer valor) {
		stack.push(valor);
	}
	
	public Integer pop() {
		return stack.pop();
	}
}
