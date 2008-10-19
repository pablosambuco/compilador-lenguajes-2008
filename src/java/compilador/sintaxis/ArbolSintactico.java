package compilador.sintaxis;

public class ArbolSintactico {
	
	private Nodo raiz = null;

	//Singleton
	private static ArbolSintactico instancia;
	private ArbolSintactico() {}
	public static ArbolSintactico getInstance(){
		if(instancia == null)
			instancia = new ArbolSintactico();
		return instancia;
	}

	public Nodo getRaiz() {
		return raiz;
	}

	public void setRaiz(Nodo raiz) {
		this.raiz = raiz;
	}

	public Nodo insertar(Datos datos, Nodo nodoIzquierdo, Nodo nodoDerecho) {
	  	
		Nodo nuevoNodo = new Nodo(datos);
	  	nuevoNodo.setNodoIzquierdo(nodoIzquierdo);
	  	nuevoNodo.setNodoDerecho(nodoDerecho);
	  
	    return nuevoNodo;
	}
	  
	public Nodo crearHoja(Datos datos) {
		Nodo nuevoNodo = new Nodo(datos);
		return nuevoNodo;
	}
	
	public void imprimirArbol() {
	 imprimirArbol(raiz);
	 System.out.println();
	}
	
	private void imprimirArbol(Nodo nodo) {
	 if (nodo == null) return;
	
	 imprimirArbol(nodo.getNodoIzquierdo());
	 System.out.print(nodo.getDatos());
	 imprimirArbol(nodo.getNodoDerecho());
	}
	 
	public void imprimirArbolPostorder() {
	 imprimirArbolPostorder(raiz);
	 System.out.println();
	}
	
	public void imprimirArbolPostorder(Nodo nodo) {
	  if (nodo == null) return;
	
	  imprimirArbolPostorder(nodo.getNodoIzquierdo());
	  imprimirArbolPostorder(nodo.getNodoDerecho());
	
	 System.out.print(nodo.getDatos());
	}
 
}
