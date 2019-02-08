import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Cliente {

	public static void main(String[] args) {
		try {
			Socket socket = new Socket( "192.168.102.199", 4321 );
			DataInputStream entrada = new DataInputStream( socket.getInputStream() ); 
			DataOutputStream salida = new DataOutputStream( socket.getOutputStream() );
			int numero = (int) (Math.random() * 10) + 1;
			while( true ) {
				int numServ = entrada.readInt();				
				if ( numServ == numero ) {
					salida.writeBoolean( true );
					break;
				}
				else {
					salida.writeBoolean( false );
				}
			}
			System.out.println( "Mi numero yeah" + numero );
			entrada.close();
			salida.close();
			socket.close();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
