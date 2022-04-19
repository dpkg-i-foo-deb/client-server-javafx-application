/**
 * Mateo Estrada Ramirez
 * Oct 16, 2020 10:11:31 AM
 * Clase utilizada para controlar
 * la excepción lanzada cuando no se 
 * encuentra el paciente solicitado en 
 * la farmacia
 */
package co.edu.uniquindio.farmacia.exceptions;

public class PacienteNoEncontradoException extends Exception
{
	/**
	 * Número de serialización
	 */
	private static final long serialVersionUID = 1L;

	public PacienteNoEncontradoException (String cedula)
	{
		super ("No se encontró el paciente solicitado:"
				+"\n"+cedula);
	}
}
