import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class emisorUDP {

	public static void main(String[] args) {
		Persona p1 = new Persona( 21, "Diego" );
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream( bos );//metera los objetos a este array de bytes
			oos.writeObject( p1 );//mete el objeto all array de objetos ya que previamente esta vinculado
			oos.close();
			byte[] bytesObjeto = bos.toByteArray();//obtengo los bytes del array xd
			
			//enviamos el paquete
			int puerto = 4321;
			InetAddress destino = null;
			try {
				destino = InetAddress.getByName("192.168.102.112");
			} catch (UnknownHostException e) {
				System.out.println("Error inneraddress");
				e.printStackTrace();
			}
			DatagramPacket paqueteEnviado = new DatagramPacket( bytesObjeto, bytesObjeto.length, destino, puerto );
			DatagramSocket socket = null;
			try {
				socket = new DatagramSocket( );
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
			oos.close();
			bos.close();
			System.out.println("Paquete enviado :D");
		} catch (IOException e) {
			System.err.println("Error :)");
			e.printStackTrace();
		} 
	}

}
