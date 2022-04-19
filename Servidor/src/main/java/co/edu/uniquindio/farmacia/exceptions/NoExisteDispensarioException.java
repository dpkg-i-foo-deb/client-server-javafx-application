/**
 * Mateo Estrada Ramirez
 * 
 * Oct 25, 2020 10:36:56 AM
 * Controla los eventos ocurridos
 * cuando no existe dispensario
 * en el programa
 */


package co.edu.uniquindio.farmacia.exceptions;

public class NoExisteDispensarioException extends Exception
{
	/**
	 * Número de serialización
	 */
	private static final long serialVersionUID = 1L;
	
	public NoExisteDispensarioException()
	{
		super("No se encuentra algún dispensario en el programa");
	}
}
