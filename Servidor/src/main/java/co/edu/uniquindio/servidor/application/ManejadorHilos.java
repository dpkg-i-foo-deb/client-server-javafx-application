/**
 * Mateo Estrada Ramirez
 * Controla los hilos del servidor
 * Nov 23, 2020 7:10:03 PM
 */
package co.edu.uniquindio.servidor.application;

public class ManejadorHilos 
{
	private HiloEspera hiloEspera;
	private HiloMenu hiloMenu;
	
	public void iniciarHiloEspera()
	{
		hiloEspera= new HiloEspera();
		hiloEspera.start();
	}
	
	public void iniciarHiloMenu()
	{
		hiloMenu= new HiloMenu();
		hiloMenu.start();
	}
	
}
