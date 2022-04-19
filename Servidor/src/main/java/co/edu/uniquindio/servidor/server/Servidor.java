/**
 * mateo
 * Nov 21, 2020 6:56:46 PM
 * Maneja los hilos del servidor
 */


package co.edu.uniquindio.servidor.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor
{
	//Para el servidor
	private HiloServidor hiloServidor;
	private boolean esperando=true;
	private String idAplicacion;
	private ServerSocket servidorComunicacion;
	private ServerSocket servidorObjeto;
	private Socket comunicacion;
	private Socket objeto;
	
	//Para la entrada y salida de datos
	private DataInputStream entradaComunicacion;
	private DataOutputStream salidaComunicacion;
	private ObjectOutputStream salidaObjeto;
	private ObjectInputStream entradaObjeto;
	
	//Para las notificaciones
	private ServerSocket servidorNotificacion;
	private Socket notificacion;
	private ArrayList<Socket> clientes = new ArrayList<Socket>();
	
	//Inicia el servidor
	public void iniciarServidor()
	{
		try
		{
			servidorComunicacion = new ServerSocket(8081);
			servidorObjeto = new ServerSocket(8082);
			servidorNotificacion= new ServerSocket(8083);
			while (esperando)
			{
				System.out.print("Esperando una nueva conexión\n");
				comunicacion=servidorComunicacion.accept();
				objeto=servidorObjeto.accept();
				notificacion=servidorNotificacion.accept();
		
				
				entradaComunicacion= new DataInputStream(comunicacion.getInputStream());
				salidaComunicacion = new DataOutputStream(comunicacion.getOutputStream());
				
				entradaObjeto = new ObjectInputStream(objeto.getInputStream());
				salidaObjeto= new ObjectOutputStream(objeto.getOutputStream());
				new DataOutputStream(notificacion.getOutputStream());
				
				
				clientes.add(notificacion);
				
				iniciarHiloServidor();
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void iniciarHiloServidor()
	{
		hiloServidor= new HiloServidor();
		hiloServidor.iniciarConextion(entradaComunicacion, salidaComunicacion, salidaObjeto, entradaObjeto, this, idAplicacion);
		hiloServidor.start();
	}
	
	public synchronized void notificarTodos()
	{
		for(Socket cliente : clientes)
		{
			try 
			{
				DataOutputStream notificador = new DataOutputStream(cliente.getOutputStream());
				notificador.writeInt(1);
				System.out.print("Se notificó a: "+cliente+"\n");
			} 
			catch (IOException e) 
			{
				System.out.print("No se pudo notificar a: "+cliente+"\n");
			}
		}
	}
	
}
