import java.io.DataInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class ClienteSSL {

	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat( "HH:mm:ss" );
		System.setProperty( "javax.net.ssl.trustStore", "/home/usuario/certificados/AlmacenCliente" );
		System.setProperty( "javax.net.ssl.trustStorePassword" , "987456");
		
		SSLSocketFactory fabricaSSLSocket = ( SSLSocketFactory ) SSLSocketFactory.getDefault(); 
		try {
			SSLSocket sock = (SSLSocket) fabricaSSLSocket.createSocket( "127.0.0.1", ServidorSSL.PUERTO );
			//cliente conectado
			System.out.println("Me he conectado");
			
			SSLSession sesionSSL = sock.getSession();//coge la hora cuando se se obtiene la sesion SSL
			System.out.println( "Host "+ sesionSSL.getPeerHost() );
			System.out.println( "Cifrado "+ sesionSSL.getCipherSuite() );
			System.out.println( "Protocolo "+ sesionSSL.getProtocol() );
			System.out.println( "Id sesion "+ new BigInteger( sesionSSL.getId() ) );
			System.out.println( "hora Sesion "+ sdf.format( new Date( sesionSSL.getCreationTime() ) ) );
			DataInputStream entrada = new DataInputStream( sock.getInputStream() );
			String saludo = entrada.readUTF();
			System.out.println( saludo );
			entrada.close();
			sock.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
