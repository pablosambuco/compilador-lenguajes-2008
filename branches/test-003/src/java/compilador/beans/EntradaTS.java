package compilador.beans;

public class EntradaTS {

	private String nombre;
	private String tipo;
	private String valor;
	private String typedef;
	private String longitud;

	public EntradaTS(String nombre, String tipo, String valor,
			String typedef, String longitud) {
		this.nombre = nombre;
		this.tipo = tipo;
		this.valor = valor;
		this.typedef = typedef;
		this.longitud = longitud;
	}

	public EntradaTS(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((nombre == null) ? 0 : nombre.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		final EntradaTS other = (EntradaTS) obj;
		return nombre.equals(other.nombre);
	}

	public String getLongitud() {
		return longitud;
	}

	public void setLongitud(String longitud) {
		this.longitud = longitud;
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

	public String getTypedef() {
		return typedef;
	}

	public void setTypedef(String typedef) {
		this.typedef = typedef;
	}

	public String getValor() {
		return valor;
	}

	/**
	 * @param valor the valor to set
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}
}
