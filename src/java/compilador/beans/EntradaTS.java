package compilador.beans;

public class EntradaTS {
	
	private String nombre;
	private String tipo;
	private String valor;
	private String typedef;
	private String ren;
	private String longitud;
	

	public EntradaTS(String nombre, String tipo, String valor, String typedef, String ren, String longitud) {
		this.nombre = nombre;
		this.tipo = tipo;
		this.valor = valor;
		this.typedef = typedef;
		this.ren = ren;
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
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final EntradaTS other = (EntradaTS) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
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
	public String getRen() {
		return ren;
	}
	public void setRen(String ren) {
		this.ren = ren;
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
	public void setValor(String valor) {
		this.valor = valor;
	}
	
	

}
