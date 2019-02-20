package servidor;

import java.util.ArrayList;

import clases.Espectaculo;

public class Servidor {
	/*
	 * almacenservidor=practicadiego
		almacencliente=clientediego
		
		la clase servidor solo lanzará el hilo y luego hago un wait
		cada metodo tiene sus getters y sus setters
		GeneradorEspectaculos creara el socket
	 * */

	public static final int PUERTO = 4321;
	private ArrayList<Espectaculo> listaEspectaculo;
	private static GeneradorEspectaculos hiloGenerador;

	public static void main(String[] args) {
		hiloGenerador = new GeneradorEspectaculos();
		hiloGenerador.start();
		try {
			String s = new String();
			synchronized (s) {
				//System.out.println("anteswait");
				s.wait();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
