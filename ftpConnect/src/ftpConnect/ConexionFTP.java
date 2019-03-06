package ftpConnect;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class ConexionFTP {

	public static void main(String[] args) {
		FTPClient client = new FTPClient();
		String servidor = "ftp.rediris.es";
		String username = "anonymous";
		String password = "anonymous";
		try {
			client.connect( servidor );
			if ( client.login( username, password) ) {
				System.out.println( client.getReplyString() );
				int respuesta = client.getReplyCode();
				System.out.println("Código de respuesta: " + respuesta);//depende del protocolo
				if ( ! FTPReply.isPositiveCompletion( respuesta ) ) {
					client.disconnect();
					System.out.println("Conexión rechazada. Código: " + respuesta);
				}
				client.enterLocalPassiveMode();//SINO NO LISTA LOS ARCHIVOS
				client.changeWorkingDirectory("/sites/archlinux.org");
				System.out.println( "Directorio actual: " + client.printWorkingDirectory() );
				FTPFile[] archivos = client.listFiles();
				System.out.println( "N° de archivos: " + archivos.length );
				for (FTPFile ftpFile : archivos) {
					System.out.println( ftpFile.getName() + "---" + ftpFile.getType() );
					if ( ftpFile.getType() == 0 ) {
						System.out.println("Descargando " + ftpFile.getName() );
						FileOutputStream fos = new FileOutputStream( "./" + ftpFile.getName() );
						client.retrieveFile( ftpFile.getName() , fos );//como se llama y donde lo voy a guardar
						System.out.println("...Descargado!\n");
						fos.close();
					}
				}
				FileInputStream fis = new FileInputStream( "./lastsync" );
				boolean res = client.storeFile("welcome.txt", fis);
				if ( ! res ) {
					System.out.println("No se ha podido subir el archivo");
					if ( ! FTPReply.isPositiveCompletion( client.getReplyCode() ) ) {
						System.out.println("Conexión rechazada. Código: " + respuesta);
						System.out.println(client.getReplyString() );
					}
				}
				client.logout();
			}
			else System.out.println("Login incorrecto");
			client.disconnect();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
