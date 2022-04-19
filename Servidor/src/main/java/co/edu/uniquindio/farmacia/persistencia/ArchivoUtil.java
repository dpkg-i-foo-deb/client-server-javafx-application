/**
 * Mateo Estrada Ramirez
 * 12/10/2020
 * Clase utilizada para brindar utilidades en cuanto
 * a la creación de logs y serialización se refiere
 */

package co.edu.uniquindio.farmacia.persistencia;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import co.edu.uniquindio.farmacia.exceptions.ArchivoVacioException;


public class ArchivoUtil 
{
	
	//Verifica la existencia de un archivo en la ruta dada
	public static void verificarExistencia(String ruta) throws IOException 
	{
		File archivo = new File(ruta);
		if(!archivo.exists())
		{
			archivo.createNewFile();
		}
	}
	
	/*
	 * Verifica el tamaño de un archivo, si es cero, se lanza una excepción
	 * este método existe para controlar errores cuando el archivo binario
	 * o xml son creados por primera vez
	 */
	public static void verificarArchivoVacio (String ruta) throws ArchivoVacioException
	{
		File archivo=new File(ruta);
		if(archivo.length()==0)
		{
			throw new ArchivoVacioException ();
		}
	}
	
	//Guarda todo el modelo de un objeto a un xml
	public static void serializarXML(String ruta, Object objeto) throws IOException
	{
		verificarExistencia(ruta);
		XMLEncoder codificador;
		codificador= new XMLEncoder(new FileOutputStream(ruta));
		codificador.writeObject(objeto);
		codificador.close();
	}
	
	//Carga los datos de un modelo a través de un archivo XML
	public static Object cargarXML (String ruta) throws IOException, ArchivoVacioException
	{
		verificarExistencia(ruta);
		verificarArchivoVacio(ruta);
		XMLDecoder decodificador;
		Object objeto;
		decodificador= new XMLDecoder(new FileInputStream(ruta));
		objeto=decodificador.readObject();
		return objeto;
	}
}
