/**
 * Mateo Estrada Ramirez
 * Oct 16, 2020 10:15:47 AM
 * Clase utilizada para controlar la excepción
 * personalizada que ocurre cuando no se encuentra
 * un medicamento
 */
package co.edu.uniquindio.farmacia.exceptions;

public class MedicamentoNoEncontradoException extends Exception
{

	/**
	 * Número de serialización
	 */
	private static final long serialVersionUID = 1L;
	
	public MedicamentoNoEncontradoException(String codigo)
	{
		super("No se encontró el medicamento solicitado"
				+"\n"+codigo);
	}

}
