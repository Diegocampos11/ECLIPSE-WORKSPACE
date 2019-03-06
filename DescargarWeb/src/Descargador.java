import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Descargador {

	public static void main(String[] args) {
		try {
			URL url = new URL("http://www.zaragoza.es/sede/servicio/restaurante.json");
			URLConnection conexion = url.openConnection();
			BufferedReader input = new BufferedReader( new InputStreamReader( conexion.getInputStream() ) );
			String linea = null;
			while ( ( linea = input.readLine() ) != null ) {
				System.out.println( linea );
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
