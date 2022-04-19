/**
 * Mateo Estrada Ramirez
 * 14/10/2020
 * Clase utilizada para almacenar datos
 * relacionados con los usuarios del programa
 */

package co.edu.uniquindio.farmacia.model;

public class Usuario implements java.io.Serializable
{
	/**
	 * Número de serialización
	 */
	private static final long serialVersionUID = 1L;
	private String uid;
	private String pass;
	private boolean esAdministrador;
	
	public Usuario (String identificador, String clave, boolean esAdministrador)
	{
		this.uid=identificador;
		this.pass=clave;
		this.setEsAdministrador(esAdministrador);
		
	}
	
	public Usuario() 
	{
	
	}

	//Métodos generados automáticamente
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}

	public boolean getEsAdministrador() {
		return esAdministrador;
	}

	public void setEsAdministrador(boolean esAdministrador) {
		this.esAdministrador = esAdministrador;
	}
	
	
}
