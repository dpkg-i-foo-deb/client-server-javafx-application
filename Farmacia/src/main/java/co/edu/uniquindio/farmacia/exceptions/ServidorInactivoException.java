/**
 * mateo
 * Nov 23, 2020 4:38:16 PM
 * Ocurre cuando se trata de iniciar el programa
 * y el servidor no está activo
 */
package co.edu.uniquindio.farmacia.exceptions;

public class ServidorInactivoException extends Exception
{
	/**
	 * Número de Serialización
	 */
	private static final long serialVersionUID = 1L;

	public ServidorInactivoException()
	{
		super("No fue posible alcanzar el servidor, se abortará la ejecución del programa");
	}
}
