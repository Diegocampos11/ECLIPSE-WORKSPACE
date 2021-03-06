package clases;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class ReceptorUDP {
	public static int PUERTO = 4321;

	public static void main(String[] args) {
		DatagramSocket socket = null;
		try {
			socket = new DatagramSocket( PUERTO );
		} catch (SocketException e) {
			System.out.println("Error daagram socket");
			e.printStackTrace();
		}
		DatagramPacket paqueteRecibido = new DatagramPacket( new byte[1024], 1024 );
		String mensaje = "";
		while( ! mensaje.equals("#quit") ) {
			try {
				//System.out.println("Esperando mensaje...");
				socket.receive( paqueteRecibido );
				//System.out.println("OK...");
			} catch (IOException e) {
				System.out.println("Error paquete recibido");
				e.printStackTrace();
			}
			mensaje = new String( paqueteRecibido.getData() );
			/*vaciar el buffer del paquete o vaciar el paquete xd 2 soluciones xd*/
			paqueteRecibido.setData( new byte[1024] );
			//
			//paqueteRecibido = new DatagramPacket( new byte[1024], 1024 );
			/*o*/
			System.out.println("El mensaje recibido es: " + mensaje );
			System.out.println("De: " + paqueteRecibido.getAddress().getHostAddress() );
		}
		socket.close();
	}

}
