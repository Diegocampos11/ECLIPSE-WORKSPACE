package cifrarDescifrar;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import java.security.KeyPairGenerator;

public class CifrarRSA {

	public static void main(String[] args) {
		try {
			KeyPairGenerator generadorClaves = KeyPairGenerator.getInstance( "RSA" );
			generadorClaves.initialize( 2048 );
			KeyPair parClaves = generadorClaves.genKeyPair();
			PrivateKey clavePrivada = parClaves.getPrivate();
			PublicKey clavePublica = parClaves.getPublic();
			Cipher cifrador = Cipher.getInstance( "RSA/ECB/PKCS1Padding" );
			
			byte mensaje[] = "Este es el mensaje".getBytes(); 
			System.out.println( "A encriptar: " + new String( mensaje ) );
			
			//Cifrar
			cifrador.init( Cipher.ENCRYPT_MODE , clavePublica );
			byte[] mensajeCifrado = cifrador.doFinal( mensaje );
			System.out.println( "Cifrado: " + new String ( mensajeCifrado ) );
			
			//Descifro
			cifrador.init( Cipher.DECRYPT_MODE , clavePrivada );
			byte[] mensajeDescifrado = cifrador.doFinal( mensajeCifrado );
			System.out.println( "Descifrado: " + new String( mensajeDescifrado ) );
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
