/**
 *Mateo Estrada Ramirez
 * Nov 22, 2020 11:10:52 PM
 * Maneja el hilo notificacion
 */


package co.edu.uniquindio.farmacia.application;

public class ManejadorHilos 
{
	private HiloNotificacion hiloNotificacion;
	
	public void iniciarHiloNotificacion()
	{
		hiloNotificacion= new HiloNotificacion();
		hiloNotificacion.start();
	}
	
	public void detenerHiloNotificacion()
	{
		hiloNotificacion.cerrarHilo();
	}
}
