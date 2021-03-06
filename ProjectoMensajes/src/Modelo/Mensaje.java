package Modelo;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Mensaje implements Serializable{
	private static final long serialVersionUID = -3360365077803926229L;
	String nombre;
	String msj;
	Date fechaHora;
	public Mensaje(String nombre, String msj, Date fechaHora) {
		super();
		this.nombre = nombre;
		this.msj = msj;
		this.fechaHora = fechaHora;
	}
	public Mensaje(String nombre, String msj) {
		super();
		this.nombre = nombre;
		this.msj = msj;
		this.fechaHora = new Date();
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getMsj() {
		return msj;
	}
	public void setMsj(String msj) {
		this.msj = msj;
	}
	public Date getFechaHora() {
		return fechaHora;
	}
	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}
	@Override
	public String toString() {
		SimpleDateFormat sdf= 
				new SimpleDateFormat("(dd/MM/yyyy-HH:mm:ss)");
		return nombre+" "+  sdf.format(fechaHora)+": " + msj;
	}
	
	
}
