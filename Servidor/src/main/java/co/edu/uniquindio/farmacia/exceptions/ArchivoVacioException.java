/**
 * Mateo Estrada Ramirez
 * Oct 16, 2020 12:59:31 PM
 */
package co.edu.uniquindio.farmacia.exceptions;

public class ArchivoVacioException extends Exception
{

	/**
	 * Número de serialización
	 */
	private static final long serialVersionUID = 1L;
	
	public ArchivoVacioException()
	{
		super("El archivo está vacío");
	}
}
