package interfaces;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import Modelo.Mensaje;

public class Pantalla extends JFrame {
	
	private ReceptorMensajes receptorThread;
	static JTextArea mensajesRecibidos;
	JTextField msjParaEnviar;
	int altoTextField = 50;
	
	//remitente
	private MulticastSocket ms;
	private ByteArrayOutputStream bos;
	private ObjectOutputStream oos;
	private InetAddress grupo;
	private Mensaje mensajeEnviar;
	private DatagramPacket paqueteEnviar;
	private String nombre;
	private byte estadoConfiguracion = 0;
	private String IPGroup;

	public Pantalla() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Al cerrar el programa se detiene
		setSize(400,600); //tamaño, ancho x alto
		setLocationRelativeTo(null); //Centra la ventana en el monitor, luego se podrá mover.
		setVisible(true); //Muestra la ventana
		setLayout(null); //No queremos plantillas
		setResizable(false);//Si queremos tamaño fijo
		setTitle("Mensajes recibidos");
		iniciarPantallaMensajes();
		iniciarMsjParaEnviar();
		//tengo que unirme al grupo antes de lanzar el thread :D
		//configurationMensajes();
		receptorThread = new ReceptorMensajes();
		//receptorThread.start();
	}

	private void iniciarPantallaMensajes() {
		mensajesRecibidos = new JTextArea();
		mensajesRecibidos.setFont( mensajesRecibidos.getFont().deriveFont( 18f ) );
		mensajesRecibidos.setLineWrap(true);
		mensajesRecibidos.setWrapStyleWord(true);//textos largos
		mensajesRecibidos.setEditable(false);
		mensajesRecibidos.setBounds(0, 0, this.getContentPane().getWidth(), this.getContentPane().getHeight() - altoTextField );
		this.add(mensajesRecibidos);
		anyadirMensaje("Bienvenido al chat de Diego Camposx!\n"
				+ "Ingrese la IP del grupo...");
	}
	
	private void iniciarMsjParaEnviar() {
		msjParaEnviar = new JTextField();
		msjParaEnviar.setFont( mensajesRecibidos.getFont() );
		msjParaEnviar.setBounds( 0, this.getContentPane().getHeight() - altoTextField, this.getContentPane().getWidth(), altoTextField);
		this.add( msjParaEnviar );
		msjParaEnviar.requestFocus();
		msjParaEnviar.addKeyListener(new KeyListener() {

	        @Override
	        public void keyPressed(KeyEvent e) {
	            if ((e.getKeyCode() == KeyEvent.VK_ENTER) ) {
	                enterPresionado();
	            }
	        }

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
	    });
	}
	
	public static void anyadirMensaje(String msj) {
		mensajesRecibidos.append("\n"+msj);
	}
	
	private void enterPresionado() {
		try {
			switch ( estadoConfiguracion ){//preparar datos
				case 2://case almost always true
					mensajeEnviar = new Mensaje( nombre, msjParaEnviar.getText(), new java.util.Date() );
					bos = new ByteArrayOutputStream();
					oos = new ObjectOutputStream( bos );
					oos.writeObject( mensajeEnviar );
					paqueteEnviar = new DatagramPacket( bos.toByteArray(), bos.toByteArray().length, grupo, 4321 );
					//enviar paquete
					ms.send( paqueteEnviar );
					//limpiar paquete tambien
					oos.close();
					bos.close();
				break;
				case 0:
					//pido la ip
					IPGroup = msjParaEnviar.getText();
					estadoConfiguracion = 1;
					//le digo que ingrese el nombre y le digo IP
					anyadirMensaje("Su IP de grupo se ha establecido como: " + IPGroup + "\nIngrese su nombre a mostrar..." );
				break;
				case 1:
					//pido el nombre
					nombre = msjParaEnviar.getText();
					anyadirMensaje("Su nombre se ha establecido como: " + nombre + " \n"
						+ "Ya puedes empezar a chatear!!" );
					//Inicio el socket para que no reciba mensajes cuando aun no ha configurado por completo su propio chat
					ms = new MulticastSocket( 4321 );
					grupo = InetAddress.getByName( IPGroup );
					ms.joinGroup( grupo );//el socket ya esta escuchando
					//estadoConfiguracion terminado, codigo 2
					estadoConfiguracion = 2;
					receptorThread.start();
				break;
			}
			limpiarDatos();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public class ReceptorMensajes extends Thread{
		@Override
		public void run() {
			try {
				DatagramPacket paqueteRecibido = new DatagramPacket(new byte[1024], 1024);
				ByteArrayInputStream bis;
				ObjectInputStream ois;
				Mensaje mensajeRecibido;
				while ( true ) {
					//System.out.println("PANTALLA Esperando paquete...");
					ms.receive( paqueteRecibido );
					bis = new ByteArrayInputStream( paqueteRecibido.getData() );
					ois = new ObjectInputStream( bis );
					mensajeRecibido = (Mensaje) ois.readObject();
					//System.out.println( "mensaje recibido: " + mensajeRecibido );
					anyadirMensaje( mensajeRecibido.toString() );
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
	
	private void limpiarDatos(){
		msjParaEnviar.setText("");
	}
}
