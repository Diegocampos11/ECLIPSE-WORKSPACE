import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class receptorUDP {

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
		DatagramPacket paqueteRecibido = new DatagramPacket( buffer, buffer.length  );
		DatagramSocket socket = null;
		try {
			socket = new DatagramSocket( puerto );
		} catch (SocketException e) {
			System.out.println("Error datagram socket");
			e.printStackTrace();
		}
		try {
			System.out.println("Esperando paquete");
			socket.receive( paqueteRecibido );
			//obtenemos los datos recibidos
			ByteArrayInputStream bis = new ByteArrayInputStream(paqueteRecibido.getData());
			ObjectInputStream ois = new ObjectInputStream( bis );
			Persona p1 = (Persona) ois.readObject();
			ois.close();
			bis.close();
			socket.close();
			System.out.println("Persona recibida" + p1);
		} catch (IOException e) {
			System.out.println("Error datagram socket receive");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
