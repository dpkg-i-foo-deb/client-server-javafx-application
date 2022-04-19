/*
 * Mateo Estrada Ramirez
 * 16/09/2020
 * Clase utilizada para la dispensación de
 * un medicamento, esta es la transacción que
 * se realizará en la interfaz de usuario
 */

package co.edu.uniquindio.farmacia.model;

import javafx.beans.property.StringProperty;

public class DispensacionMedicamento implements java.io.Serializable
{
	/**
	 * Número de serialización
	 */
	private static final long serialVersionUID = 1L;
	private Paciente paciente;
	private Medicamento medicamento;
	private Empleado dispensario;

	public DispensacionMedicamento ()
	{
		
	}
	
	public DispensacionMedicamento(Paciente paciente, Medicamento medicamento, Empleado dispensario)
	{
		this.dispensario= new Empleado();
		this.paciente=paciente;
		this.medicamento=medicamento;
		this.establecerEmpleado(dispensario);
	}
	
	public String getCorreoPaciente()
	{
		return paciente.getCorreo();
	}
	public String getCodigoOrdenPaciente()
	{
		return paciente.getCodigoOrdenMedica();
	}
	public String getVigenciaOrdenPaciente()
	{
		return paciente.getVigenciaOrdenMedica();
	}
	public String getPresentacionMedicamento()
	{
		return medicamento.getPresentacion();
	}
	public String getViaAdministracionMedicamento()
	{
		return medicamento.getViaAdministracion();
	}
	public String getConcentracionMedicamento()
	{
		return medicamento.getConcentracion();
	}
	public String getDosisMedicamento()
	{
		return medicamento.getDosis();
	}
	
	
	//Relacionados con string property
	public StringProperty getNombrePacienteProperty()
	{
		return paciente.getNombreProperty();
	}
	public StringProperty getCedulaPacienteProperty()
	{
		return paciente.getCedulaProperty();
	}
	public StringProperty getCorreoPacienteProperty()
	{
		return paciente.getCorreoProperty();
	}
	public StringProperty getCodigoOrdenPacienteProperty()
	{
		return paciente.getCodigoOrdenMedicaProperty();
	}
	public StringProperty getVigenciaOrdenMedicaProperty()
	{
		return paciente.getVigenciaOrdenMedicaProperty();
	}
	
	public StringProperty getNombreEmpleadoProperty()
	{
		return dispensario.getNombreProperty();
	}
	public StringProperty getCedulaEmpleadoProperty()
	{
		return dispensario.getCedulaProperty();
	}
	public StringProperty getCorreoEmpleadoProperty()
	{
		return dispensario.getCorreoProperty();
	}
	public StringProperty getCargoEmpleadoProperty()
	{
		return dispensario.getCargoProperty();
	}
	
	public StringProperty getNombreMedicamentoProperty()
	{
		return medicamento.getNombreProperty();
	}
	public StringProperty getCodigoMedicamentoProperty()
	{
		return medicamento.getCodigoProperty();
	}
	public StringProperty getConcentracionMedicamentoProperty()
	{
		return medicamento.getConcentracionProperty();
	}
	public StringProperty getDosisMedicamentoProperty ()
	{
		return medicamento.getDosisProperty();
	}
	public StringProperty getPresentacionMedicamentoProperty()
	{
		return medicamento.getPresentacionProperty();
	}
	public StringProperty getViaAdministracionMedicamentoProperty()
	{
		return medicamento.getViaAdministracionProperty();
	}
	/* Establece los datos de un 
	 * empleado
	 */
	public void establecerEmpleado(Empleado dispensario)
	{
		this.dispensario= new Empleado();
		this.dispensario.setNombre(dispensario.getNombre());
		this.dispensario.setCorreo(dispensario.getCorreo());
		this.dispensario.setCedula(dispensario.getCedula());
		this.dispensario.setCargo(dispensario.getCargo());
	}
	
	//Utilizados para obtener parámetros en específico
	public String getCodigoMedicamento()
	{
		return this.medicamento.getCodigo();
	}
	public String getCedulaPaciente()
	{
		return this.paciente.getCedula();
	}
	public String getNombreDispensario()
	{
		return this.dispensario.getNombre();
	}
	public String getCedulaDispensario()
	{
		return this.dispensario.getCedula();
	}
	public String getCorreoDispensario()
	{
		return this.dispensario.getCorreo();
	}
	public String getCargoDispensario()
	{
		return this.dispensario.getCargo();
	}
	
	//Generados automáticamente
	@Override
	public String toString() {
		return this.paciente.getNombre()+" "+this.getDispensario().getNombre()+" "+this.getMedicamento().getNombre();
	}
	//Métodos generados automáticamente
	public Paciente getPaciente() {
		return paciente;
	}
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}
	public Empleado getDispensario() {
		return dispensario;
	}
	public void setDispensario(Empleado dispensario) {
		this.dispensario = dispensario;
	}
	public Medicamento getMedicamento() {
		return medicamento;
	}
	public void setMedicamento(Medicamento medicamento) {
		this.medicamento = medicamento;
	}
	
	
}
