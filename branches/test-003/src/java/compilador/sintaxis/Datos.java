package compilador.sintaxis;

public class Datos {
	
	private String valor = null;

	public Datos(String valor) {
		super();
		this.valor = valor;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String toString() {
		return valor.toString();
	}

}
