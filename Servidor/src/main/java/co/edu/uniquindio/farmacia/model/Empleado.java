/*
 * Mateo Estrada Ramírez
 * 16/09/2020
 * Clase utilizada para el empleado
 * extiende de persona
 */

package co.edu.uniquindio.farmacia.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Empleado extends Persona implements java.io.Serializable
{

	/**
	 * Número de serialización
	 */
	private static final long serialVersionUID = 1L;
	private String cargo;
	

	public Empleado ()
	{

	}
	
	public StringProperty getCargoProperty()
	{
		return new SimpleStringProperty(cargo);
	}
	
	//Métodos generados automáticamente
	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	
	
}
