/**
 * mateo
 * Nov 22, 2020 8:06:36 PM
 * Ocurre cuando un objeto tiene relación con otro
 */


package co.edu.uniquindio.farmacia.exceptions;

public class ObjetoRelacionadoException extends Exception
{

	/**
	 * Número de serialización
	 */
	private static final long serialVersionUID = 1L;
	
	public ObjetoRelacionadoException()
	{
		super("La selección está relacionada con una o más dispensaciones.");
	}

}
