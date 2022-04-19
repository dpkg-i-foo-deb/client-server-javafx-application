/*
 * Mateo Estrada Ramirez
 * 19/09/2020
 * Clase utilizada para controlar
 * las excepciones que ocurran
 * en la interfaz del paciente
 */
package co.edu.uniquindio.farmacia.exceptions;

public class LimiteDispensacionesException extends Exception
{

	/**
	 * Número de serialización
	 */
	private static final long serialVersionUID = 1L;
	

	public LimiteDispensacionesException()
	{
		super("¡El paciente ha alcanzado el límite de\n"
				+"Dispensaciones!."
				+ "Este incidente será reportado.");
	}
	
}
