/**
 * Mateo Estrada Ramirez
 * 12/10/2020
 * Clase utilizada para manejar la excepción personalizada
 * que ocurre cuando el código de una orden médica
 * es igual al de otra
 */

package co.edu.uniquindio.farmacia.exceptions;

public class CodigoOrdenMedicaDuplicadoException extends Exception
{

	/**
	 * ID de serialización
	 */
	private static final long serialVersionUID = 1L;

	public CodigoOrdenMedicaDuplicadoException()
	{
		super("Ya existe un código de orden médica\n"
				+"igual en el programa, verifique e intente nuevamente.");
	}

}
