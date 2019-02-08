import java.io.Serializable;

public class Persona implements Serializable{
	int edad;
	String nombre;
	
	public int getEdad() {
		return edad;
	}
	public void setEdad(int edad) {
		this.edad = edad;
	}
	public Persona(int edad, String nombre) {
		super();
		this.edad = edad;
		this.nombre = nombre;
	}
	@Override
	public String toString() {
		return "Persona [edad=" + edad + ", nombre=" + nombre + "]";
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
