/**
 * Mateo Estrada Ramirez
 * 12/10/2020
 * Clase utilizada para persistir datos a través
 * de archivos log y serialización
 */

package co.edu.uniquindio.farmacia.persistencia;

import co.edu.uniquindio.farmacia.exceptions.ArchivoVacioException;
import co.edu.uniquindio.farmacia.model.Farmacia;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Persistencia 
{
	//Rutas de los archivos en persistencia
	private static final String RUTA_ARCHIVO_MODELO_XML = System.getProperty("user.home")+"/.local/share/servidor-farmacia/";
	
	//Nombres de los archivos de persistencia
	private static final String NOMBRE_ARCHIVO_MODELO_XML="model.xml";
	
	/*
	 * Verifica la existencia de los directorios
	 * para la persistencia y trata de crearlos
	 */
	public static void mkdirs (String path)
	{
		Path ruta= Paths.get(path);
		try
		{
			Files.createDirectories(ruta);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}
	
	//Guarda todo el modelo a un xml
	public static void guardarModeloXML(Farmacia farmacia)
	{
		mkdirs(RUTA_ARCHIVO_MODELO_XML);
		try
		{
			ArchivoUtil.serializarXML(RUTA_ARCHIVO_MODELO_XML+NOMBRE_ARCHIVO_MODELO_XML, farmacia);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	//Carga el modelo desde un xml
	public static Farmacia cargarModeloXML()throws ArchivoVacioException
	{
		Farmacia farmacia = null;
		try
		{
			//Se carga el modelo y se hace casting a un puntero del tamaño de farmacia
			farmacia=(Farmacia)ArchivoUtil.cargarXML(RUTA_ARCHIVO_MODELO_XML+NOMBRE_ARCHIVO_MODELO_XML);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return farmacia;
	}
}
