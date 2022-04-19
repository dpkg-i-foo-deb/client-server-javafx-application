/*
 * Mateo Estrada Ramirez
 * 19/09/2020
 * Clase utilizada para controlar
 * errores por campos vacíos en las
 * propiedades de las clases
 */
package co.edu.uniquindio.farmacia.exceptions;
public class CamposVaciosException extends Exception
{

	/**
	 * ID de serialización
	 */
	private static final long serialVersionUID = 1L;
	//Constructor de la clase
	public CamposVaciosException()
	{
		super("Una o mas de las propiedades del objeto fueron\n"
				+ "dejadas en blanco, verifique e intente nuevamente.");
	}
	
	
}
