/**
 * Mateo Estrada Ramirez
 * Inicia el servidor
 * Nov 23, 2020 7:11:18 PM
 */
package co.edu.uniquindio.servidor.application;

import co.edu.uniquindio.servidor.server.Servidor;

public class HiloEspera extends Thread
{
	private Servidor servidor;
	
	public HiloEspera()
	{
		servidor=new Servidor();
	}
	
	@Override
	public void run()
	{
		servidor.iniciarServidor();
	}
}
