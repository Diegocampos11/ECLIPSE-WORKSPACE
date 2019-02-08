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
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Modelo.Mensaje;

public class Pantalla extends JFrame {
	
	private ReceptorMensajes receptorThread;
	private static JTextArea mensajesRecibidos; 
	private JTextField msjParaEnviar;
	private static final int PUERTO = 4321;
	private int altoTextField = 50;
	
	//emisor y receptor
	private MulticastSocket ms;
	//emisor
	private ByteArrayOutputStream bos = new ByteArrayOutputStream();
	private ObjectOutputStream oos;
	private InetAddress grupo;
	//receptor
	private ByteArrayInputStream bis;
	private ObjectInputStream ois;

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
		configurationMensajes();
		receptorThread = new ReceptorMensajes();
		receptorThread.start();
	}
	
	private void configurationMensajes() {
		try {
			//emisor
			ms = new MulticastSocket( PUERTO );
			grupo = InetAddress.getByName("224.0.0.1");
			ms.joinGroup( grupo );//el socket ya esta escuchando
			oos = new ObjectOutputStream( bos );
			//receptor
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void iniciarPantallaMensajes() {
		mensajesRecibidos = new JTextArea();
		mensajesRecibidos.setLineWrap(true);
		mensajesRecibidos.setWrapStyleWord(true);//textos largos
		mensajesRecibidos.setEditable(false);
		mensajesRecibidos.setBounds(0, 0, this.getContentPane().getWidth(), this.getContentPane().getHeight() - altoTextField );
		this.add(mensajesRecibidos);
	}
	
	private void iniciarMsjParaEnviar() {
		msjParaEnviar = new JTextField();
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
		DatagramPacket paqueteEnviar = new DatagramPacket( bos.toByteArray(), bos.toByteArray().length, grupo, PUERTO );
		//enviar paquete
		try {
			//preparar datos
			Mensaje mensajeEnviar = new Mensaje("Diego", msjParaEnviar.getText(), new java.util.Date() );
			oos.writeObject( mensajeEnviar );
			//enviar paquete
			ms.send( paqueteEnviar );
			anyadirMensaje( mensajeEnviar.toString() );
			System.out.println( "mensaje enviado" + mensajeEnviar.toString() );
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public class ReceptorMensajes extends Thread{
		@Override
		public void run() {
			try {
				//MulticastSocket ms = new MulticastSocket( PUERTO );
				//Obtenemos una IP de multicast, tambien llamada grupos, 
				//por que varios equipos pueden escuchar de esa IP
				//InetAddress grupo = InetAddress.getByName("224.0.0.1");
				//ms.joinGroup( grupo );//el socket ya se encuentra escuchando cualquier dato
				//recepcion de paquete
				while ( true ) {
					DatagramPacket paqueteRecibido = new DatagramPacket(new byte[1024], 1024);
					ms.receive( paqueteRecibido );
					ByteArrayInputStream bis = new ByteArrayInputStream( paqueteRecibido.getData() );
					ObjectInputStream ois = new ObjectInputStream( bis );
					System.out.println("Esperando paquete...");
					Mensaje mensajeRecibido = (Mensaje) ois.readObject();
					//paqueteRecibido.setData( new byte[1024] );
					System.out.println( "mensaje recibido: " + mensajeRecibido );
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
	
	/*private class DeliverMensajes extends Thread{
		
	}*/
}
