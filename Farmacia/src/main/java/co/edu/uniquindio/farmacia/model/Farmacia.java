/**
 * Mateo Estrada Ramirez
 * 16/09/2020
 * Clase Principal del programa, utilizada para 
 * contener a otras clases
 */
package co.edu.uniquindio.farmacia.model;

import java.util.ArrayList;

import co.edu.uniquindio.farmacia.exceptions.CamposVaciosException;
import co.edu.uniquindio.farmacia.exceptions.CedulaPacienteDuplicadaException;
import co.edu.uniquindio.farmacia.exceptions.CodigoMedicamentoDuplicadoException;
import co.edu.uniquindio.farmacia.exceptions.CodigoOrdenMedicaDuplicadoException;
import co.edu.uniquindio.farmacia.exceptions.LimiteDispensacionesException;
import co.edu.uniquindio.farmacia.exceptions.ObjetoRelacionadoException;

public class Farmacia implements java.io.Serializable
{

	/**
	 * Número de serialización
	 */
	private static final long serialVersionUID = 1L;
	private String nombre;
	private	Empleado dispensario;
	private ArrayList<Paciente>pacientes;
	private ArrayList<OrdenMedica> ordenes;
	private ArrayList<Medicamento> medicamentos;
	private ArrayList<DispensacionMedicamento>dispensaciones;
	private ArrayList <Usuario> usuarios;

	public Farmacia(String nombre)
	{

		this.nombre=nombre;
		this.pacientes=new ArrayList<Paciente>();
		this.ordenes=new ArrayList<OrdenMedica>();
		this.medicamentos=new ArrayList<Medicamento>();
		this.dispensaciones=new ArrayList<DispensacionMedicamento>();
		this.usuarios= new ArrayList<Usuario>();
	}
	
	public Farmacia ()
	{
		this.pacientes=new ArrayList<Paciente>();
		this.ordenes=new ArrayList<OrdenMedica>();
		this.medicamentos=new ArrayList<Medicamento>();
		this.dispensaciones=new ArrayList<DispensacionMedicamento>();
		this.usuarios= new ArrayList<Usuario>();
		this.dispensario=new Empleado();
	}
	
	/*
	 * Obtiene los datos de medicamentos
	 * a partir de una posición
	 */
	public String getNombreMedicamento(int posicion)
	{
		return this.medicamentos.get(posicion).getNombre();
	}
	public String getPresentacionMedicamento(int posicion)
	{
		return this.medicamentos.get(posicion).getPresentacion();
	}
	public String getCodigoMedicamento(int posicion)
	{
		return this.medicamentos.get(posicion).getCodigo();
	}
	public String getDosisMedicamento(int posicion)
	{
		return this.medicamentos.get(posicion).getDosis();
	}
	public String getViaAdministracionMedicamento(int posicion)
	{
		return this.medicamentos.get(posicion).getViaAdministracion();
	}
	public String getConcentracionMedicamento(int posicion)
	{
		return this.medicamentos.get(posicion).getConcentracion();
	}
	
	
	/*
	 * Crea un nuevo medicamento
	 */
	public void addMedicamento(String nombre, String presentacion, String viaAdm, String codigo, String dosis, String concentracion) throws CamposVaciosException, CodigoMedicamentoDuplicadoException
	{
		//Verifica si existen códigos duplicados 
		this.verificarCodigoDuplicado(codigo, -1);
		
		//Verifica si hay campos vacíos
		this.verificarCamposVacios(nombre, presentacion, viaAdm, codigo, dosis, concentracion);

		this.medicamentos.add(new Medicamento(nombre, presentacion, viaAdm, codigo, dosis, concentracion));
	}
	
	//Verifica que no existan medicamentos con un código duplicado
	public void verificarCodigoDuplicado(String codigo, int seleccion)throws CodigoMedicamentoDuplicadoException
	{
	
		for (int contador=0; contador<medicamentos.size();contador++) 
		{
			if(contador!=seleccion)
			{
				if(medicamentos.get(contador).getCodigo().equals(codigo))
				{
					//Si existe un duplicado, lanzar la excepción
					throw new CodigoMedicamentoDuplicadoException();
				}
			}

		}
	}
	
	/*
	 * Edita la información de un medicamento
	 */
	public void editarMedicamento(String nombre, String presentacion, String viaAdm, String codigo, String dosis, String concentracion, int posicion) throws CamposVaciosException, CodigoMedicamentoDuplicadoException
	{
		//Verifica si existe un código duplicado si hay más de un medicamento
		if(this.medicamentos.size()!=1)
			this.verificarCodigoDuplicado(codigo, posicion);
		
		//Verifica si hay campos vacíos
		this.verificarCamposVacios(nombre, presentacion, viaAdm, codigo, dosis, concentracion);
		

		this.medicamentos.get(posicion).setNombre(nombre);
		this.medicamentos.get(posicion).setPresentacion(presentacion);
		this.medicamentos.get(posicion).setViaAdministracion(viaAdm);
		this.medicamentos.get(posicion).setCodigo(codigo);
		this.medicamentos.get(posicion).setDosis(dosis);
		this.medicamentos.get(posicion).setConcentracion(concentracion);
	}
	
	/*
	 * Elimina un medicamento
	 */
	public void eliminarMedicamento(int posicion)
	{
		this.medicamentos.remove(posicion);
	}
	
	/*
	 * Obtiene datos de pacientes de acuerdo a una posición
	 */
	public String getNombrePaciente(int posicion)
	{
		return this.pacientes.get(posicion).getNombre();
	}
	public String getCorreoPaciente(int posicion)
	{
		return this.pacientes.get(posicion).getCorreo();
	}
	public String getCedulaPaciente(int posicion)
	{
		return this.pacientes.get(posicion).getCedula();
	}
	public String getCodigoOrdenPaciente(int posicion)
	{
		return this.pacientes.get(posicion).getCodigoOrdenMedica();
	}
	public String getVigenciaOrdenPaciente(int posicion)
	{
		return this.pacientes.get(posicion).getVigenciaOrdenMedica();
	}
	
	/*
	 * Edita los datos de un paciente
	 */
	public void editarPaciente(String nombre, String correo, String cedula, String codigo, String vigencia, int posicion) throws CamposVaciosException, CedulaPacienteDuplicadaException, CodigoOrdenMedicaDuplicadoException
	{
		//Verifica si hay campos vacíos
		this.verificarCamposVacios(nombre, correo, cedula, codigo,vigencia);
		
		//Verifica si la cédula está duplicada
		this.verificarCedulaPacienteDuplicada(cedula, posicion);
		
		//Verifica si la orden está duplicada
		this.verificarOrdenPaciente(codigo, posicion);
		
		this.pacientes.get(posicion).setNombre(nombre);
		this.pacientes.get(posicion).setCorreo(correo);
		this.pacientes.get(posicion).setCedula(cedula);
		this.pacientes.get(posicion).setCodigoOrdenMedica(codigo);
		this.pacientes.get(posicion).setVigenciaOrdenMedica(vigencia);
	}
	
	/*
	 * Elimina un paciente
	 */
	public void eliminarPaciente(int posicion)
	{
		this.pacientes.remove(posicion);
	}
	
	
	/*
	 * Crea un nuevo paciente
	 */
	public void addPaciente(String nombre, String correo, String cedula, String codigo, String vigencia) throws CamposVaciosException, CedulaPacienteDuplicadaException, CodigoOrdenMedicaDuplicadoException
	{
		//Verifica si hay campos vacíos
		this.verificarCamposVacios(nombre, correo, cedula, codigo, vigencia);
		
		//Verifica si la cédula está duplicada
		this.verificarCedulaPacienteDuplicada(cedula, -1);
		
		//Verifica si la orden está duplicada
		this.verificarOrdenPaciente(codigo, -1);
		
		this.pacientes.add(new Paciente(nombre, correo, cedula, codigo, vigencia));
	}
	
	//Verifica si n argumentos poseen cadenas del tipo ""
	public void verificarCamposVacios(String ...argumentos) throws CamposVaciosException
	{
		for(int contador=0;contador<=argumentos.length-1;contador++)
		{
			if(argumentos[contador].equals(""))
			{
				//Si existe un vacío, se lanza la excepción
				throw new CamposVaciosException();
			}
		}
	}
	
	//Verifica si la orden de un paciente es igual a la de otro
	public void verificarOrdenPaciente(String codigo, int seleccion) throws CodigoOrdenMedicaDuplicadoException
	{
		for (int contador=0; contador<pacientes.size(); contador++)
		{
			if(contador!=seleccion)
			{
				if(pacientes.get(contador).getOrdenMedica().getCodigo().equals(codigo) && pacientes.size()>1)
				{
					//Si la orden es la misma... Se lanza la excepción
					throw new CodigoOrdenMedicaDuplicadoException();
				}
			}

		}
	}
	
	//Verifica si un paciente posee la misma cédula que otro
	public void verificarCedulaPacienteDuplicada(String cedula, int seleccion) throws CedulaPacienteDuplicadaException
	{
		for(int contador=0; contador<pacientes.size();contador++)
		{
			if(contador!=seleccion)
			{
				if(pacientes.get(contador).getCedula().equals(cedula))
				{
					//Si la cédula es la misma... Se lanza la excepción
					throw new CedulaPacienteDuplicadaException();
				}
			}
		}
	}
	
	/*
	 * Relacionados con la orden médica de un paciente
	 */
	public String getCodigoOrdenMedicaPaciente(int posicion)
	{
		return this.pacientes.get(posicion).getCodigoOrdenMedica();
	}
	public String getVigenciaOrdenMedicaPaciente(int posicion)
	{
		return this.pacientes.get(posicion).getVigenciaOrdenMedica();
	}
	public int getNumeroDispensacionesPaciente(int posicion)
	{
		return this.pacientes.get(posicion).getCantidadDispensaciones();
	}
	
	
	//Incrementa las dispensaciones de un paciente
	public void incrementarDispensacionPaciente(int posicion)
	{
		this.pacientes.get(posicion).incrementar();
	}
	
	/*
	 *Añade una dispensación
	 */
	public void addDispensacion(int posicionMedicamento, int posicionPaciente) throws LimiteDispensacionesException 
	{
		verificarLimiteDispensaciones(posicionPaciente, pacientes.get(posicionPaciente).getCantidadDispensaciones());
		dispensaciones.add(new DispensacionMedicamento(this.pacientes.get(posicionPaciente), this.getMedicamentos().get(posicionMedicamento),dispensario));
		incrementarDispensacionPaciente(posicionPaciente);
	}
	
	//Verifica si el paciente alcanzó el límite de dispensaciones
	public void verificarLimiteDispensaciones(int posicion, int cantidad)throws LimiteDispensacionesException
	{
		
		if(cantidad>=this.pacientes.get(posicion).getMaxDispensaciones())
		{
			//Si la cantidad es igual o superior al maximo, lanzar excepción
			throw new LimiteDispensacionesException();
		}
	}
	
	//Obtiene la lista de dispensaciones
	public ArrayList<String> getListaDispensaciones()
	{
		ArrayList<String>dispensaciones=new ArrayList<String>();

		for(int contador=0;contador<this.dispensaciones.size();contador++)
		{
			dispensaciones.add(this.dispensaciones.get(contador).toString());
		}

		return dispensaciones;
	}
	
	
	/*
	 * Relacionado con la clase
	 * empleado
	 */
	
	public String getNombreEmpleado()
	{
		if(dispensario==null)
		{
			return "";
		}
		return this.dispensario.getNombre();
	}
	public String getCedulaEmpleado()
	{
		if(dispensario==null)
		{
			return "";
		}
		return this.dispensario.getCedula();
	}
	public String getCargoEmpleado()
	{
		if(dispensario==null)
		{
			return "";
		}
		return this.dispensario.getCargo();
	}
	public String getCorreoEmpleado()
	{
		if(dispensario==null)
		{
			return "";
		}
		return this.dispensario.getCorreo();
	}
	
	//Edita un dispensario
	public void setDispensario(String nombre, String correo, String cedula, String cargo) throws CamposVaciosException
	{
		this.verificarCamposVacios(nombre, correo, cedula, cargo);
		//Establecer los atributos
		this.setNombreEmpleado(nombre);
		this.setCorreoEmpleado(correo);
		this.setCedulaEmpleado(cedula);
		this.setCargoEmpleado(cargo);
	}	
	/*
	 * Relacionados con el dispensario
	 */
	public void setNombreEmpleado(String nombre)
	{
		if(dispensario==null)
		{
			dispensario=new Empleado();
		}
		this.dispensario.setNombre(nombre);
		
	}
	public void setCorreoEmpleado(String correo)
	{
		this.dispensario.setCorreo(correo);
	}
	public void setCedulaEmpleado(String cedula)
	{
		this.dispensario.setCedula(cedula);
	}
	public void setCargoEmpleado(String cargo)
	{
		this.dispensario.setCargo(cargo);
	}

	//Relacionados con obtener datos de las dispensaciones
	public String getCorreoPacienteDispensacion(int posicion)
	{
		return dispensaciones.get(posicion).getCorreoPaciente();
	}
	public String getCedulaPacienteDispensacion(int posicion)
	{
		return dispensaciones.get(posicion).getCedulaPaciente();
	}
	public String getCodigoOrdenPacienteDispensacion(int posicion)
	{
		return dispensaciones.get(posicion).getCodigoOrdenPaciente();
	}
	public String getVigenciaOrdenPacienteDispensacion(int posicion)
	{
		return dispensaciones.get(posicion).getVigenciaOrdenPaciente();
	}
	public String getPresentacionmedicamentoDispensacion(int posicion)
	{
		return dispensaciones.get(posicion).getPresentacionMedicamento();
	}
	public String getViaAdministracionMedicamentoDispensacion(int posicion)
	{
		return dispensaciones.get(posicion).getViaAdministracionMedicamento();
	}
	public String getConcentracionMedicamentoDispensacion(int posicion)
	{
		return dispensaciones.get(posicion).getConcentracionMedicamento();
	}
	public String getDosisMedicamentoDispensacion(int posicion)
	{
		return dispensaciones.get(posicion).getDosisMedicamento();
	}
	public String getCorreoDispensarioDispensacion(int posicion)
	{
		return dispensaciones.get(posicion).getCorreoDispensario();
	}
	public String getCargoDispensacionDispensacion(int posicion)
	{
		return dispensaciones.get(posicion).getCargoDispensario();
	}
	public String getNombreDispensarioDispensacion(int posicion)
	{
		return dispensaciones.get(posicion).getNombreDispensario();
	}
	public String getCedulaDispensarioDispensacion(int posicion)
	{
		return dispensaciones.get(posicion).getCedulaDispensario();
	}
	
	//Relacionado con obtener el nombre de determinado usuario
	public boolean getRolUsuario(String nombre)
	{
		for(int contador=0; contador<usuarios.size();contador++)
		{
			if(usuarios.get(contador).getUid().equals(nombre))
			{
				return usuarios.get(contador).getEsAdministrador();
			}
		}
		return false;
	}
	
	
	//Verifica si un medicamento posee alguna relación con las dispensaciones
	public void verificarRelacionMedicamento(int seleccion) throws ObjetoRelacionadoException
	{
		Medicamento medicamento = medicamentos.get(seleccion);
		for(int contador=0; contador<dispensaciones.size();contador++)
		{
			if(dispensaciones.get(contador).getMedicamento().equals(medicamento))
			{
				throw new ObjetoRelacionadoException();
			}
		}
	}
	
	//Verifica si un paciente posee alguna relación con las dispensaciones
	public void verificarRelacionPaciente(int seleccion) throws ObjetoRelacionadoException
	{
		Paciente paciente = pacientes.get(seleccion);
		for(int contador=0; contador<dispensaciones.size();contador++)
		{
			if(dispensaciones.get(contador).getPaciente().equals(paciente))
			{
				throw new ObjetoRelacionadoException();
			}
		}
	}
	
	//Métodos generados automáticamente
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public ArrayList<Usuario> getUsuarios() {
		return usuarios;
	}


	public void setUsuarios(ArrayList<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Empleado getDispensario() {
		return dispensario;
	}
	public void setDispensario(Empleado dispensario) {
		this.dispensario = dispensario;
	}
	public ArrayList<Paciente> getPacientes() {
		return pacientes;
	}
	public void setPacientes(ArrayList<Paciente> pacientes) {
		this.pacientes = pacientes;
	}
	public ArrayList<OrdenMedica> getOrdenes() {
		return ordenes;
	}
	public void setOrdenes(ArrayList<OrdenMedica> ordenes) {
		this.ordenes = ordenes;
	}
	public ArrayList<Medicamento> getMedicamentos() {
		return medicamentos;
	}
	public void setMedicamentos(ArrayList<Medicamento> medicamentos) {
		this.medicamentos = medicamentos;
	}
	public ArrayList<DispensacionMedicamento> getDispensaciones() {
		return dispensaciones;
	}
	public void setDispensaciones(ArrayList<DispensacionMedicamento> dispensaciones) {
		this.dispensaciones = dispensaciones;
	}
	

}
