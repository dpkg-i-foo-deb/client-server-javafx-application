/**
 * Mateo Estrada Ramirez
 * 12/10/2020
 * Clase utilizada para manejar una excepción
 * personalizada cuando dos medicamentos
 * tengan el mismo código
 */

package co.edu.uniquindio.farmacia.exceptions;

public class CodigoMedicamentoDuplicadoException extends Exception
{

	/**
	 * Versión para serialización
	 */
	private static final long serialVersionUID = 1L;

	public CodigoMedicamentoDuplicadoException()
	{
		super("Ya existe un medicamento con el código"
				+"\ningresado, verifique e intente nuevamente.");
	}

}
