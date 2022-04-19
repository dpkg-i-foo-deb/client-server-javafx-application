/*
 * Mateo Estrada Ramirez
 * 16/09/2020
 * Clase utilizada para definir los atributos de una persona
 */

package co.edu.uniquindio.farmacia.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class Persona implements java.io.Serializable
{	

	/**
	 * Número de serialización
	 */
	private static final long serialVersionUID = 1L;
	private String nombre;
	private String correo;
	private String cedula;
	
	public StringProperty getNombreProperty()
	{
		return new SimpleStringProperty(nombre);
	}
	public StringProperty getCorreoProperty()
	{
		return new SimpleStringProperty(correo);
	}
	public StringProperty getCedulaProperty()
	{
		return new SimpleStringProperty(cedula);
	}
	
	//Métodos generados automáticamente
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getCedula() {
		return cedula;
	}
	public void setCedula(String cedula) {
		this.cedula = cedula;
	}
	
	

}
