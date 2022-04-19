/*
 * Mateo Estrada Ramirez
 * 19/09/2020
 * Excepción utilizada para informar que la selección 
 * realizada es incorrecta
 */

package co.edu.uniquindio.farmacia.exceptions;

public class SeleccionIncorrectaException extends RuntimeException
{

	/**
	 * Número de serialización
	 */
	private static final long serialVersionUID = 1L;
	

	public SeleccionIncorrectaException()
	{
		//Llamar al constructor de la clase padre y establecer mensaje
		super("La selección realizada es incorrecta.\nAsegúrese "
				+ "de seleccionar un objeto\no crearlo.");
	}
	
}
