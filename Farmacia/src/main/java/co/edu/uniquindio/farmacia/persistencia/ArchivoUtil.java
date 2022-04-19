/**
 * Mateo Estrada Ramirez
 * 12/10/2020
 * Clase utilizada para brindar utilidades en cuanto
 * a la creación de logs y serialización se refiere
 */

package co.edu.uniquindio.farmacia.persistencia;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import co.edu.uniquindio.farmacia.exceptions.PermisoDenegadoException;


public class ArchivoUtil 
{

	private static String fecha="";
	
	//Verifica la existencia de un archivo en la ruta dada
	public static void verificarExistencia(String ruta) throws IOException 
	{
		File archivo = new File(ruta);
		if(!archivo.exists())
		{
			archivo.createNewFile();
		}
	}
	
	
	//Genera un log que se ve bonito
	public static void guardarLog(String mensaje, int nivel, String accion, String ruta)
	{
		Logger LOGGER = Logger.getLogger(ArchivoUtil.class.getName());
		FileHandler fileHandler= null;
		getFecha();
		//Intentar generar el log
		try
		{
			fileHandler = new FileHandler(ruta, true);
			fileHandler.setFormatter(new SimpleFormatter());
			LOGGER.addHandler(fileHandler);
			//Crear el log de acuerdo al nivel
			switch (nivel) 
			{
				case 1: 
						LOGGER.log(Level.INFO,fecha+", "+mensaje+", "+ accion);
						break;
				case 2: 
						LOGGER.log(Level.WARNING,fecha+", "+mensaje+", "+accion);
						break;
				case 3:
						LOGGER.log(Level.SEVERE,fecha+", "+mensaje+", "+accion);
						break;
				default:
						break;
			}
		}
		catch (SecurityException e)
		{
			LOGGER.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		} 
		catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		}
		finally
		{
			fileHandler.close();
		}
	}
	
	//Escribe datos en un archivo
	public static void escribirArchivo(String ruta, String contenido, Boolean sobreEscribir) throws IOException, PermisoDenegadoException
	{
		verificarPrivilegios(ruta);
		FileWriter fileWriter = new FileWriter(ruta, !sobreEscribir);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		bufferedWriter.write(contenido);
		bufferedWriter.close();
		fileWriter.close();
	}
	
	//Verifica los privilegios de escritura en la ruta seleccionada
	private static void verificarPrivilegios(String ruta) throws PermisoDenegadoException
	{
		File archivo = new File(ruta);
		if(!archivo.canWrite())
		{
			throw new PermisoDenegadoException();
		}
	}
	
	//Recupera la fecha actual 
	private static void getFecha()
	{
		String mes="", minuto="",dia="";
		int an=0;
		int diaN, mesN, hora , minutoN;
		Calendar calendario =Calendar.getInstance();
		//Establecer los valores de dia, mes y año
		diaN=calendario.get(Calendar.DATE);
		mesN=calendario.get(Calendar.MONTH)+1;
		minutoN=calendario.get(Calendar.MINUTE);
		hora=calendario.get(Calendar.HOUR_OF_DAY);
		an=calendario.get(Calendar.YEAR);
		
		//Hacer que la fecha quede bonita
		if(diaN<10)
		{
			dia="0"+diaN;
		}
		else
		{
			dia=""+diaN;
		}
		if(mesN<10)
		{
			mes="0"+mes;
		}
		else
		{
			mes=""+mesN;
		}
		if(minutoN<10)
		{
			minuto="0"+minutoN;
		}
		else
		{
			minuto=""+minutoN;
		}
		fecha= an + "-"+ mes + "/" + dia+ "-" + hora + ":" + minuto;  	
	}
	
	public static String obtenerFecha()
	{
		getFecha();
		return fecha;
	}
}
