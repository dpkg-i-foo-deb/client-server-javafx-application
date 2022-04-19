/**
 * Mateo Estrada Ramirez
 * 16/09/2020
 * Clase utilizada para el paciente,
 * implementa IPaciente y extiende de persona
 */

package co.edu.uniquindio.farmacia.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Paciente extends Persona 
{
	/**
	 * Número de serialización
	 */
	private static final long serialVersionUID = 1L;
	private OrdenMedica ordenMedica;
	private int cantidadDispensaciones;

	public Paciente ()
	{
		this.cantidadDispensaciones=0;
		this.ordenMedica=new OrdenMedica();
	}
	
	public Paciente (String nombre, String correo, String cedula, String codigo, String vigencia)
	{
		this.cantidadDispensaciones=0;
		setNombre(nombre);
		setCorreo(correo);
		setCedula(cedula);
		this.ordenMedica=new OrdenMedica(codigo, vigencia);
	}
	
	public StringProperty getCodigoOrdenMedicaProperty()
	{
		return new SimpleStringProperty(getCodigoOrdenMedica());
	}
	public StringProperty getVigenciaOrdenMedicaProperty()
	{
		return new SimpleStringProperty(getVigenciaOrdenMedica());
	}
	
	//Métodos generados automáticamente
	public int getCantidadDispensaciones() {
		return cantidadDispensaciones;
	}
	public void setCantidadDispensaciones(int cantidadDispensaciones) {
		this.cantidadDispensaciones = cantidadDispensaciones;
	}
	public void incrementar()
	{
		this.cantidadDispensaciones++;
	}
	//Método de la interfaz paciente
	
	public int getMaxDispensaciones() 
	{
		return IPaciente.MAX_DISPENSACIONES;
	}
	
	
	/*
	 * Métodos para obtener información sobre
	 * la orden médica
	 */
	public String getCodigoOrdenMedica ()
	{
		return this.ordenMedica.getCodigo();
	}
	public String getVigenciaOrdenMedica()
	{
		return this.ordenMedica.getVigencia();
	}
	public void setCodigoOrdenMedica (String codigo)
	{
		this.ordenMedica.setCodigo(codigo);
	}
	public void setVigenciaOrdenMedica(String vigencia)
	{
		this.ordenMedica.setVigencia(vigencia);
	}
	
	//Métodos generados automáticamente
	public OrdenMedica getOrdenMedica() {
		return ordenMedica;
	}
	public void setOrdenMedica(OrdenMedica ordenMedica) {
		this.ordenMedica = ordenMedica;
	}
	

}
