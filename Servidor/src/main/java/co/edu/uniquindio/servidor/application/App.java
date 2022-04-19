/**
 * Mateo Estrada Ramirez
 * 21/11/2020
 * Inicia el servidor
 */

package co.edu.uniquindio.servidor.application;

import co.edu.uniquindio.farmacia.exceptions.ArchivoVacioException;
import co.edu.uniquindio.farmacia.model.Farmacia;
import co.edu.uniquindio.farmacia.persistencia.Persistencia;

public class App  
{
	private static Farmacia farmacia;
	private static ManejadorHilos manejadorHilos;
	
    public static void main(String[] args) 
    {
    	manejadorHilos= new ManejadorHilos();
    	try 
    	{
			farmacia=Persistencia.cargarModeloXML();
		} 
    	catch (ArchivoVacioException e) 
    	{
			System.out.print("No existe información en el model.xml\n");
			farmacia= new Farmacia();
			Persistencia.guardarModeloXML(farmacia);
			System.out.print("Se creó uno nuevo con datos vacíos");
		}
    	
    	System.out.print("Iniciando servidor\n");
    	manejadorHilos.iniciarHiloEspera();
    	
    	try 
    	{
			Thread.sleep(2000);
		}
    	catch (InterruptedException e) 
    	{
			e.printStackTrace();
		}
    	
    	manejadorHilos.iniciarHiloMenu();

    }
    
    public static Farmacia getFarmacia()
    {
    	return farmacia;
    }
}