/**
 * Mateo Estrada Ramirez
 * 12/10/2020
 * Clase utilizada para manejar la excepción personalizada
 * que ocurre cuando se trata de crear un paciente
 * con una cédula duplicada
 */

package co.edu.uniquindio.farmacia.exceptions;

public class CedulaPacienteDuplicadaException extends Exception
{
	
	/**
	 * Número de serialización
	 */
	private static final long serialVersionUID = 1L;

	public CedulaPacienteDuplicadaException()
	{
		super("Ya existe una cédula igual registrada en\n"
				+"el programa, verifique e intente nuevamente.");
	}
}
