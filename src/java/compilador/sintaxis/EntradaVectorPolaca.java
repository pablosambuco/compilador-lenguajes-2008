package compilador.sintaxis;

public class EntradaVectorPolaca {

	private String nombre = null;
	private String tipo = null;

	public EntradaVectorPolaca(String nombre, String tipo) {
		this.nombre = nombre;
		this.tipo = tipo;
	}

	public EntradaVectorPolaca(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	
	public String toString() {
		return nombre.toString();
	}

}
