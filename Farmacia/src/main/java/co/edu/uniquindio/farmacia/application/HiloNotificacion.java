/**
 * Mateo Estrada Ramirez
 * Nov 22, 2020 11:11:25 PM
 * Espera una notificación desde el servidor para
 * actualizar datos
 */
package co.edu.uniquindio.farmacia.application;

import java.io.DataInputStream;
import java.io.IOException;

import co.edu.uniquindio.farmacia.controllers.SingletonFarmacia;

public class HiloNotificacion extends Thread
{
	private DataInputStream entradaNotificaciones;
	private boolean servidorActivo=true;
	private boolean ejecutandoPrograma=true;
	public HiloNotificacion()
	{
		entradaNotificaciones=SingletonFarmacia.getEntradaNotificaciones();
	}
	
	@Override
	public void run()
	{
		@SuppressWarnings("unused")
		int opcion;
		while (servidorActivo && ejecutandoPrograma)
		{
			try {
				opcion=entradaNotificaciones.readInt();
				SingletonFarmacia.getInstance().generarLog("Tratando de actualizar datos",1,
						"Se recibió la notificación de guardado de datos desde el servidor");
				
				SingletonFarmacia.getInstance().recargarDatos();
			} catch (IOException e) 
			{
				e.printStackTrace();
				servidorActivo=false;
			}
		}
	}
	
	public void cerrarHilo()
	{
		ejecutandoPrograma=false;
	}
}
