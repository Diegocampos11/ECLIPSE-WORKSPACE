package funcionesHASH;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class programaHASH {

	public static void main(String[] args) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
			String mensaje = "Esto es un mensaje";
			byte mensajesBytes[] = mensaje.getBytes();
			byte[] hashSHA = md.digest( mensajesBytes );
			System.out.println( mensaje + ". hash: " + new String( hashSHA ) );
			System.out.println( "Mensaje en hexadecimal: " + Hexadecimal( hashSHA ) );
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	static String Hexadecimal( byte[] resumen ) {
		String hex = "";
		for (int i = 0; i < resumen.length; i++) {
			String h = Integer.toHexString( resumen[i] & 0xFF );//FF en hexadecimal
			if ( h.length() == 1 ) hex += "0";
			hex += h;
		}
		return hex.toUpperCase();
	}

}
