package compilador.sintaxis;

public class Nodo {
	
	private Nodo nodoIzquierdo;
	private Nodo nodoDerecho;
	private Datos datos;
	
	Nodo(Datos datos) {
	      this.nodoIzquierdo = null;
	      this.nodoDerecho = null;
	      this.datos = datos;
    }

	public Datos getDatos() {
		return datos;
	}

	public void setDatos(Datos datos) {
		this.datos = datos;
	}

	public Nodo getNodoDerecho() {
		return nodoDerecho;
	}

	public void setNodoDerecho(Nodo nodoDerecho) {
		this.nodoDerecho = nodoDerecho;
	}

	public Nodo getNodoIzquierdo() {
		return nodoIzquierdo;
	}

	public void setNodoIzquierdo(Nodo nodoIzquierdo) {
		this.nodoIzquierdo = nodoIzquierdo;
	}
    
    
	
}
