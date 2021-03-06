import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import Modelo.Mensaje;

public class ReceptorUDPMulticast {

	public static void main(String[] args) {
		try {
			MulticastSocket ms = new MulticastSocket( 4321 );
			//Obtenemos una IP de multicast, tambien llamada grupos, 
			//por que varios equipos pueden escuchar de esa IP
			InetAddress grupo = InetAddress.getByName("224.0.0.0");
			ms.joinGroup( grupo );//el socket ya se encuentra escuchando cualquier dato
			//recepcion de paquete
			DatagramPacket paqueteRecibido = new DatagramPacket(new byte[1024], 1024);
			while ( true ) {
				System.out.println("Esperando paquete...");
				ms.receive( paqueteRecibido );
				ByteArrayInputStream bis = new ByteArrayInputStream( paqueteRecibido.getData() );
				ObjectInputStream ois = new ObjectInputStream( bis );
				Mensaje mensajeRecibido = (Mensaje) ois.readObject();
				System.out.println( "mensaje recibido: " + mensajeRecibido );
			}
			//ois.close();
			//bis.close();
			//ms.close();
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
