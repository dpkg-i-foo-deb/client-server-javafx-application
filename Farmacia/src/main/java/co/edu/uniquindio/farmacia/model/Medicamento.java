/**
 * Mateo Estrada Ramirez
 * 16/09/2020
 * Clase utilizada para el medicamento
 */

package co.edu.uniquindio.farmacia.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Medicamento implements java.io.Serializable
{

	/**
	 * Número de serialización
	 */
	private static final long serialVersionUID = 1L;
	private String nombre;
	private String presentacion;
	private String viaAdministracion;
	private String codigo;
	private String dosis;
	private String concentracion;

	public Medicamento()
	{
		
	}
	
	public Medicamento(String nombre, String presentacion, String viaAdm, String codigo, String dosis, String concentracion)
	{
		this.nombre=nombre;
		this.presentacion=presentacion;
		this.viaAdministracion=viaAdm;
		this.codigo=codigo;
		this.dosis=dosis;
		this.concentracion=concentracion;
	}
	
	
	public StringProperty getNombreProperty()
	{
		return new SimpleStringProperty(nombre);
	}
	public StringProperty getPresentacionProperty()
	{
		return new SimpleStringProperty(presentacion);
	}
	public StringProperty getViaAdministracionProperty()
	{
		return new SimpleStringProperty(viaAdministracion);
	}
	public StringProperty getCodigoProperty()
	{
		return new SimpleStringProperty(codigo);
	}
	public StringProperty getDosisProperty()
	{
		return new SimpleStringProperty(dosis);
	}
	public StringProperty getConcentracionProperty()
	{
		return new SimpleStringProperty(concentracion);
	}
	
	//Métodos generados automáticamente
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getPresentacion() {
		return presentacion;
	}
	public void setPresentacion(String presentacion) {
		this.presentacion = presentacion;
	}
	public String getViaAdministracion() {
		return viaAdministracion;
	}
	public void setViaAdministracion(String viaAdministracion) {
		this.viaAdministracion = viaAdministracion;
	}
	public String getDosis() {
		return dosis;
	}
	public void setDosis(String dosis) {
		this.dosis = dosis;
	}
	public String getConcentracion() {
		return concentracion;
	}
	public void setConcentracion(String concentracion) {
		this.concentracion = concentracion;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
}
