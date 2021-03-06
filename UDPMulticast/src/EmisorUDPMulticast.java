import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class EmisorUDPMulticast {

	public static void main(String[] args) {
		try {
			MulticastSocket ms = new MulticastSocket( 4321 );
			//Obtenemos una IP de multicast, tambien llamada grupos, 
			//por que varios equipos pueden escuchar de esa IP
			InetAddress grupo = InetAddress.getByName("224.0.0.1");
			ms.joinGroup( grupo );//el socket ya esta escuchando
			//preparar datos
			Mensaje mensajeEnviar = new Mensaje("Hola amigos de youtube");
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream( bos );
			oos.writeObject( mensajeEnviar );
			DatagramPacket paqueteEnviar = new DatagramPacket( bos.toByteArray(), bos.toByteArray().length, grupo, 4321 );
			//enviar paquete
			ms.send( paqueteEnviar );
			System.out.println("Paquete enviado :D!");
			oos.close();
			bos.close();
			ms.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
