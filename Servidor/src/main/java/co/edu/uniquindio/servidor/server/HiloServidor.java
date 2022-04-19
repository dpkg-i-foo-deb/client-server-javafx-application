/**
 * mateo
 * Nov 21, 2020 6:54:23 PM
 * Ejecuta un hilo de acuerdo a la 
 * petición realizada
 */


package co.edu.uniquindio.servidor.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import co.edu.uniquindio.farmacia.exceptions.ArchivoVacioException;
import co.edu.uniquindio.farmacia.model.Farmacia;
import co.edu.uniquindio.farmacia.persistencia.Persistencia;

public class HiloServidor extends Thread
{
	//Para el servidor
	private Servidor servidor;
	private DataInputStream entradaComunicacion;
	private DataOutputStream salidaComunicacion;
	private ObjectOutputStream salidaObjeto;
	private ObjectInputStream entradaObjeto;
	private int opcion;
	
	//Para la farmacia
	private Farmacia farmacia;
	
	public HiloServidor()
	{
		try 
		{
			farmacia=Persistencia.cargarModeloXML();
		} 
		catch (ArchivoVacioException e) 
		{
			System.out.print("El archivo xml está vacío\n");
			guardarModelo();
			System.out.print("Se creó uno nuevo\n");
		}
	}
	public void iniciarConextion(DataInputStream entradaComunicacion, DataOutputStream salidaComunicacion,
			ObjectOutputStream salidaObjeto, ObjectInputStream entradaObjeto, Servidor servidor, 
			String idAplicacion)
	{
		this.entradaComunicacion=entradaComunicacion;
		this.salidaComunicacion=salidaComunicacion;
		this.salidaObjeto=salidaObjeto;
		this.entradaObjeto=entradaObjeto;
		this.servidor=servidor;
	}
	
	@Override
	public void run()
	{
		try 
		{
			opcion=entradaComunicacion.readInt();
			System.out.print("Se recibió una solicitud con la opción: "+opcion+"\n");
			switch(opcion)
			{
				//Caso 1, enviar la farmacia entera
				case 1: enviarFarmacia();
						break;

				//Caso 2, guardar datos a la persistencia
				case 2: guardarModelo();
						break;
						
				//Caso 3, iniciar sesión
				case 3: recibirCredenciales();
						break;
						
				//Caso 4, obtener el rol de un usuario
				case 4: enviarRolUsuario();
						break;
			}
		} 
		catch (IOException e)
		{
		
			e.printStackTrace();
		}
	}
	
	private void enviarFarmacia()
	{
		try
		{
			farmacia=Persistencia.cargarModeloXML();
			salidaObjeto.writeObject(farmacia);
			System.out.print("Se ha enviado el modelo al cliente\n");
		} 
		catch (ArchivoVacioException e)
		{
			System.out.print("No existe/ No se encuentra el archivo XML de persistencia\n");
			Persistencia.guardarModeloXML(farmacia);
			System.out.print("Se ha creado uno con los datos vacíos para iniciar el programa cliente\n");
			enviarFarmacia();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	private void guardarModelo()
	{
		try 
		{
			farmacia= (Farmacia) entradaObjeto.readObject();
			Persistencia.guardarModeloXML(farmacia);
			System.out.print("Se han guardado datos que llegaron desde la aplicación cliente\n");
			servidor.notificarTodos();
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{

			e.printStackTrace();
		}
	}
	
	//Recibe credenciales desde el cliente
	private void recibirCredenciales()
	{
		//Donde 0 es el usuario y 1 la contraseña
		String[] credenciales;
		
		try 
		{
			credenciales=(String[]) entradaObjeto.readObject();
			
			if(verificarCredenciales(credenciales))
			{
				salidaComunicacion.writeBoolean(true);
			}
			else
			{
				salidaComunicacion.writeBoolean(false);
			}
		} 
		catch (ClassNotFoundException e) 
		{
			System.out.print("Se recibió un objeto de credenciales inválido"+"\n");
		} 
		catch (IOException e)
		{
			System.out.print("No se pudieron recibir las credenciales\n");
		}
	}
	
	//Verifica las credenciales recibidas
	private boolean verificarCredenciales(String[]credenciales)
	{	
		for(int contador=0;contador<farmacia.getUsuarios().size();contador++)
		{
			if(farmacia.getUsuarios().get(contador).getUid().equals(credenciales[0]) &&
					farmacia.getUsuarios().get(contador).getPass().equals(credenciales[1]))
			{
				return true;
			}
		}
		return false;
	}
	
	//Envia true si es admin, false si no lo es
	private void enviarRolUsuario()
	{
		String usuario;
		try 
		{
			usuario=entradaComunicacion.readUTF();
			salidaComunicacion.writeBoolean(farmacia.getRolUsuario(usuario));
			
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
}
