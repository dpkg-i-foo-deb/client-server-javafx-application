/*
 * Mateo Estrada Ramirez
 * Nov 23, 2020 5:10:02 PM
 * Ocurre cuando se guarda un archivo a un
 * directorio que requiera privilegios elevados
 */


package co.edu.uniquindio.farmacia.exceptions;

public class PermisoDenegadoException extends Exception
{

	/**
	 * Número de Serialización
	 */
	private static final long serialVersionUID = 1L;
	
	public PermisoDenegadoException()
	{
		super("No posee privilegios para guardar el archivo\nen el directorio seleccionado.");
	}

}
