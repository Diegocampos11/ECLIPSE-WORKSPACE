package clases;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class emisorUDP {

	public static void main(String[] args) {
		int puerto = 4321;
		InetAddress destino = null;
		try {
			destino = InetAddress.getByName("192.168.102.112");
		} catch (UnknownHostException e) {
			System.out.println("Error inneraddress");
			e.printStackTrace();
		}
		byte[] buffer = new byte[1024];
		//String msj = "#quit";
		String msj = ", esto es un mensaje enviado por UDP";
		buffer = msj.getBytes();
		
		DatagramPacket paqueteEnviado = new DatagramPacket( buffer, buffer.length, destino, puerto );
		DatagramSocket socket = null;
		try {
			socket = new DatagramSocket();
		} catch (SocketException e) {
			System.out.println("Error datagram socket");
			e.printStackTrace();
		}
		try {
			socket.send( paqueteEnviado );
		} catch (IOException e) {
			System.out.println("Error socket");
			e.printStackTrace();
		}
		socket.close();
		System.out.println("Paquete enviado :D");
	}

}
