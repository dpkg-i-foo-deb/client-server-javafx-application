/**
 * Mateo Estrada Ramirez
 * 12/10/2020
 * Clase utilizada para persistir datos a través
 * de archivos log y serialización
 */

package co.edu.uniquindio.farmacia.persistencia;

import co.edu.uniquindio.farmacia.controllers.SingletonFarmacia;
import co.edu.uniquindio.farmacia.exceptions.PermisoDenegadoException;
import co.edu.uniquindio.farmacia.model.DispensacionMedicamento;
import co.edu.uniquindio.farmacia.model.Empleado;
import co.edu.uniquindio.farmacia.model.Medicamento;
import co.edu.uniquindio.farmacia.model.Paciente;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


public class Persistencia 
{
	//Rutas de los archivos en persistencia
	private static final String RUTA_ARCHIVO_LOG = System.getProperty("user.home")+"/.local/share/persistencia/log/";
	
	//Nombres de los archivos de persistencia
	private static final String NOMBRE_ARCHIVO_LOG = "co.edu.uniquindio.farmacia_Log.txt";
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
	
	//Crea un log a través de la clase archivoUtil
	public static void guardarRegistroLog(String mensaje, int nivel, String accion)
	{
		mkdirs(RUTA_ARCHIVO_LOG);
		ArchivoUtil.guardarLog(mensaje, nivel, accion, RUTA_ARCHIVO_LOG+NOMBRE_ARCHIVO_LOG);
		
	}
	
	//Exporta el contenido de todo el modelo a un reporte en texto plano
	public static void generarReporte(File archivoDestino, String usuario, int seleccion) throws PermisoDenegadoException
	{
		String ruta=archivoDestino.getPath();
		String contenidoReporte="";
		
		//Se genera un reporte de acuerdo a la selección
		if(seleccion==1)
		{
			contenidoReporte=generarReporteMedicamentos(usuario);
		}
		if(seleccion==2)
		{
			contenidoReporte=generarReportePacientes(usuario);
		}
		if(seleccion==3)
		{
			contenidoReporte=generarReporteDispensario(usuario);
		}
		if(seleccion==4)
		{
			contenidoReporte=generarReporteDispensaciones(usuario);
		}
		
		try 
		{
			ArchivoUtil.escribirArchivo(ruta, contenidoReporte, true);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	private static String generarReporteMedicamentos(String usuario)
	{
		String contenido="";
		ArrayList<Medicamento>medicamentos;
		medicamentos=SingletonFarmacia.getInstance().getMedicamentos();
		
		contenido+="Servicios Especializados - Reporte de listado de medicamentos\n"
				+"Fecha: "+ArchivoUtil.obtenerFecha()+"\n"
				+"Reporte realizado por: "+usuario+"\n"
				+"\n\n"
				+"Información del reporte:\n";
		
		for(int contador=0;contador<medicamentos.size();contador++)
		{
			contenido+="Medicamento NO."+(contador+1)+"\n";
			contenido+="Nombre: "+medicamentos.get(contador).getNombre()+".\n";
			contenido+="Código: "+medicamentos.get(contador).getCodigo()+".\n";
			contenido+="Presentación: "+medicamentos.get(contador).getPresentacion()+".\n";
			contenido+="Via de administración "+medicamentos.get(contador).getViaAdministracion()+".\n";
			contenido+="Concentración: "+medicamentos.get(contador).getConcentracion()+".\n";
			contenido+="\n";
		}
		return contenido;
	}
	
	private static String generarReportePacientes(String usuario)
	{
		String contenido="";
		ArrayList<Paciente> pacientes;
		pacientes=SingletonFarmacia.getInstance().getPacientes();
		
		contenido+="Servicios Especializados - Reporte de pacientes registrados\n"
				+"Fecha: "+ArchivoUtil.obtenerFecha()+"\n"
				+"Reporte realizado por: "+usuario+"\n"
				+"\n\n"
				+"Información del reporte:\n";
		
		for(int contador=0;contador<pacientes.size();contador++)
		{
			contenido+="Paciente NO."+(contador+1)+"\n";
			contenido+="Nombre: "+pacientes.get(contador).getNombre();
			contenido+="Cédula: "+pacientes.get(contador).getCedula();
			contenido+="Correo: "+pacientes.get(contador).getCorreo();
			contenido+="Código de la orden: "+pacientes.get(contador).getCodigoOrdenMedica();
			contenido+="Vigencia de la orden: "+pacientes.get(contador).getVigenciaOrdenMedica();
			contenido+="\n";
		}
		return contenido;
	}
	
	private static String generarReporteDispensario(String usuario)
	{
		String contenido="";
		Empleado dispensario=SingletonFarmacia.getInstance().getDispensario();
		
		contenido+="Servicios Especializados - Reporte del dispensario.\n"
				+"Fecha: "+ArchivoUtil.obtenerFecha()+"\n"
				+"Reporte realizado por: "+usuario+"\n"
				+"\n\n"
				+"Información del reporte:\n";
		
		contenido+="Nombre: "+dispensario.getNombre()+"\n";
		contenido+="Cédula: "+dispensario.getCedula()+"\n";
		contenido+="Correo: "+dispensario.getCorreo()+"\n";
		contenido+="Cargo: "+dispensario.getCargo()+"\n";
		
		return contenido;
	}
	
	private static String generarReporteDispensaciones(String usuario)
	{
		String contenido="";
		
		ArrayList<DispensacionMedicamento> dispensaciones=SingletonFarmacia.getInstance().getDispensaciones();
		
		Medicamento medicamento;
		Paciente paciente;
		Empleado dispensario;
		
		contenido+="Servicios Especializados - Reporte de dispensaciones.\n"
				+"Fecha: "+ArchivoUtil.obtenerFecha()+"\n"
				+"Reporte realizado por: "+usuario+"\n"
				+"\n\n"
				+"Información del reporte:\n";
		
		for (int contador=0; contador<dispensaciones.size();contador++)
		{
			paciente=dispensaciones.get(contador).getPaciente();
			medicamento=dispensaciones.get(contador).getMedicamento();
			dispensario=dispensaciones.get(contador).getDispensario();
			contenido+="Dispensación NO. "+(contador+1)+"\n";
			contenido+="Dispensado al paciente: "+paciente.getNombre()+" Con ID: "+paciente.getCedula()+"\n";
			contenido+="Medicamento Dispensado: "+medicamento.getNombre()+" Con código: "+medicamento.getCodigo();
			contenido+="Dosis: "+medicamento.getDosis()+" Concentracion: "+medicamento.getConcentracion()+"\n";
			contenido+="Dispensado por: "+dispensario.getNombre()+" Con ID: "+dispensario.getCedula()+"\n";
			contenido+="\n";
		}
		return contenido;
	}	
}
