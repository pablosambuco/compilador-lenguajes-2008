package compilador.beans;

public class PalabraReservada {
	
	private String nombre;

	public PalabraReservada(String token) {
		this.nombre = token;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
