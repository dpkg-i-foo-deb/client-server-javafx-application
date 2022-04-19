/**
 * Mateo Estrada Ramírez
 * 16/09/2020
 * Clase utilizada para la orden médica
 */

package co.edu.uniquindio.farmacia.model;

public class OrdenMedica implements java.io.Serializable
{

	/**
	 * Número de serialización
	 */
	private static final long serialVersionUID = 1L;
	private String codigo;
	private String vigencia;

	public OrdenMedica ()
	{
		
	}
	
	//Métodos generados automáticamente
	public OrdenMedica(String codigo, String vigencia)
	{
		this.codigo=codigo;
		this.vigencia=vigencia;
	}
	//Métodos generados automáticamente
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getVigencia() {
		return vigencia;
	}
	public void setVigencia(String vigencia) {
		this.vigencia = vigencia;
	}
	
	
}
