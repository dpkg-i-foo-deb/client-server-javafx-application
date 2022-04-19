/**
 * Mateo Estrada Ramirez
 * 14/10/2020
 * Clase utilizada para manejar las excepciones 
 * personalizadas cuando falla la verificación de 
 * credenciales de un usuario
 */
package co.edu.uniquindio.farmacia.exceptions;

public class VerificacionCredencialesFallidaException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public VerificacionCredencialesFallidaException()
	{
		super ("Fallo en la verificación de credenciales"
				+"\nverifique e intente nuevamente\n o cree usuarios.");
	}

}
