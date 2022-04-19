/*
 * Mateo Estrada Ramirez
 * 16/09/2020
 * ControladorPrincipal para la ventana
 */
//Especificar el paquete
package co.edu.uniquindio.farmacia.controllers;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import co.edu.uniquindio.farmacia.application.ManejadorHilos;
import co.edu.uniquindio.farmacia.exceptions.CamposVaciosException;
import co.edu.uniquindio.farmacia.exceptions.CedulaPacienteDuplicadaException;
import co.edu.uniquindio.farmacia.exceptions.CodigoMedicamentoDuplicadoException;
import co.edu.uniquindio.farmacia.exceptions.CodigoOrdenMedicaDuplicadoException;
import co.edu.uniquindio.farmacia.exceptions.LimiteDispensacionesException;
import co.edu.uniquindio.farmacia.exceptions.ObjetoRelacionadoException;
import co.edu.uniquindio.farmacia.exceptions.PermisoDenegadoException;
import co.edu.uniquindio.farmacia.exceptions.VerificacionCredencialesFallidaException;
import co.edu.uniquindio.farmacia.model.DispensacionMedicamento;
import co.edu.uniquindio.farmacia.model.Empleado;
import co.edu.uniquindio.farmacia.model.Farmacia;
import co.edu.uniquindio.farmacia.model.Medicamento;
import co.edu.uniquindio.farmacia.model.Paciente;
import co.edu.uniquindio.farmacia.persistencia.Persistencia;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

//Declaración de clase
public class SingletonFarmacia implements Runnable
{
	/*
	 * Atributo estático para apuntar
	 * la instancia de esta clase en nulo
	 * sin necesidad de crear un objeto
	 */
	private static SingletonFarmacia instancia=null;
	
	//Atributo de la clase principal
	private Farmacia farmacia;
	
	//Para los datos de las clases
	private ObservableList<Medicamento> datosMedicamentos=FXCollections.observableArrayList();
	private ObservableList<Paciente> datosPacientes=FXCollections.observableArrayList();
	private ObservableList<DispensacionMedicamento> datosDispensaciones=FXCollections.observableArrayList();
	
	//Para hilos de guardado de datos y logging
	private ManejadorHilos manejadorHilos;
	private Thread hiloLog;
	private Thread hiloReporte;
	private Thread hiloCargarFarmacia;
	private int nivel=0;
	private String accion="";
	private String mensaje="";
	private File archivoDestino;
	private String usuario;
	private int seleccion;
	
	//Para sockets y transferencia de datos
	private Socket socketComunicacion;
	private Socket socketObjeto;
	private Socket socketNotificacion;
	private DataOutputStream salidaComunicacion;
	private DataInputStream entradaComunicacion;
	private ObjectOutputStream salidaObjeto;
	private ObjectInputStream entradaObjeto;
	private static DataInputStream entradaNotificaciones;
	private final String HOST= "EliteBook-8470p";
	
	//Para hilos
	
	//Constructor privado, garantiza una única instancia
	private SingletonFarmacia()
	{
		//Conecta al servidor
		iniciarConexion();
		//inicializar atributos
		inicializar();
	}
	
	/*
	 * Utilizado para devolver la instancia del singleton
	*/
	public static SingletonFarmacia getInstance()
	{
		if(instancia==null)
		{
			instancia=new SingletonFarmacia();
		}
		return instancia;
	}


	//Método inicializar
	private void inicializar() 
	{
		//Recupera el modelo desde el servidor
		solicitarModelo();
		leerModelo();
		
		//Establece los datos de los observable list
		establecerDatos();
		manejadorHilos=new ManejadorHilos();
		manejadorHilos.iniciarHiloNotificacion();
	}
	
	//Relacionado con el servidor
	private void iniciarConexion()
	{
		try 
		{
			socketComunicacion= new Socket(HOST,8081);
			socketObjeto= new Socket(HOST, 8082);
			socketNotificacion = new Socket(HOST, 8083);
			
			salidaComunicacion = new DataOutputStream(socketComunicacion.getOutputStream());
			entradaComunicacion = new DataInputStream(socketComunicacion.getInputStream());
			
			salidaObjeto = new ObjectOutputStream(socketObjeto.getOutputStream());
			entradaObjeto = new ObjectInputStream(socketObjeto.getInputStream());
			
			entradaNotificaciones = new DataInputStream(socketNotificacion.getInputStream());

			
		} 
		catch (UnknownHostException e) 
		{
			generarLog(HOST,3,"No se pudo encontrar el host");
		} 
		catch (IOException e) {
			generarLog("Error al conectar con el servidor",3,"No se pudo conectar al servidor");
		}
		
	}
	
	private void solicitarModelo()
	{
		try 
		{
			salidaComunicacion.writeInt(1);
		} 
		catch (IOException e) 
		{
			generarLog("No se pudo solicitar información",3,
					"No fue posible solicitar el modelo");
		}
	}
	
	private void leerModelo()
	{
		try 
		{
			farmacia= (Farmacia) entradaObjeto.readObject();
		} 
		catch (ClassNotFoundException e)
		{
			generarLog("Objeto inválido",3,"El objeto recibido no es válido");
		} 
		catch (IOException e) 
		{
			generarLog("No se pudo cargar el modelo",3,
					"Ocurrió un error al intentar cargar el modelo");
		}
	}
	
	//Recarga los datos del modelo
	public synchronized void recargarDatos()
	{
		hiloCargarFarmacia=new Thread(this);
		hiloCargarFarmacia.start();
	}
	
	/*
	 * Relacionado con la interfaz gráfica
	 */
	private void establecerDatos()
	{
		datosMedicamentos.addAll(farmacia.getMedicamentos());
		datosPacientes.addAll(farmacia.getPacientes());
		datosDispensaciones.addAll(farmacia.getDispensaciones());
	}
	
	private void limpiarDatos()
	{
		datosMedicamentos.clear();
		datosPacientes.clear();
		datosDispensaciones.clear();
	}
	
	public ObservableList<Medicamento> getDatosMedicamentos()
	{
		datosMedicamentos.clear();
		datosMedicamentos.addAll(farmacia.getMedicamentos());
		return datosMedicamentos;
	}
	
	public ObservableList<Paciente> getDatosPacientes()
	{
		datosPacientes.clear();
		datosPacientes.addAll(farmacia.getPacientes());
		return datosPacientes;
	}
	
	public ObservableList<DispensacionMedicamento> getDatosDipensaciones()
	{
		datosDispensaciones.clear();
		datosDispensaciones.addAll(farmacia.getDispensaciones());
		return datosDispensaciones;
	}
	
	/*
	 * Relacionado con log y persistencia
	 */
	
	//Genera un log
	public void generarLog( String mensaje, int nivel, String accion)
	{
		this.nivel=nivel;
		this.mensaje=mensaje;
		this.accion=accion;
		hiloLog= new Thread(this);
		hiloLog.start();
	}

	//Se ejecuta cuando se va a cerrar el programa
	public void cerrarPrograma()
	{
		manejadorHilos.detenerHiloNotificacion();
	}
	
	//Guarda el objeto de farmacia al servidor
	public void guardarFarmacia()
	{
		generarLog(ControladorVistaPrincipal.getUsuario(),1,"Tratando de enviar datos al servidor");
		iniciarConexion();
		solicitarGuardado();
		enviarFarmacia();
	}
	
	private void solicitarGuardado()
	{
		try 
		{
			salidaComunicacion.writeInt(2);
		} 
		catch (IOException e) 
		{
			generarLog("No fue posible solicitar el guardado de datos",3,"Error en el servidor");
		}
	}
	
	private void enviarFarmacia()
	{
		try 
		{
			salidaObjeto.writeObject(farmacia);
		} 
		catch (IOException e) 
		{
			generarLog("No fue posible enviar la farmacia",3,"Error del servidor");
		}
	}
	
	public void iniciarSesion(String[]credenciales) throws VerificacionCredencialesFallidaException
	{
		iniciarConexion();
		enviarCredenciales(credenciales);
		verificarCredenciales();
	}
	
	private void enviarCredenciales(String[]credenciales) throws VerificacionCredencialesFallidaException
	{

		try 
		{
			salidaComunicacion.writeInt(3);
			salidaObjeto.writeObject(credenciales);

		} 
		catch (IOException e) 
		{
			generarLog("No se pudieron enviar las credenciales",3,
					"Ocurrió un error al enviar las credenciales al servidor");
			e.printStackTrace();
			throw new VerificacionCredencialesFallidaException();
		}
		
	}
	
	private void verificarCredenciales() throws VerificacionCredencialesFallidaException
	{
		boolean verificacionExitosa;
		try 
		{
			verificacionExitosa= entradaComunicacion.readBoolean();
			if(!verificacionExitosa)
			{
				throw new VerificacionCredencialesFallidaException();
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			throw new VerificacionCredencialesFallidaException();
		}
	}
	
	//Obtiene el rol de un usuario
	public boolean getRolUsuario()
	{
		solicitarRol();
		enviarUsuario();
		return recibirRol();
	}
	
	private void solicitarRol()
	{
		iniciarConexion();
		try 
		{
			salidaComunicacion.writeInt(4);
		} 
		catch (IOException e) 
		{
			generarLog("Error del servidor",3,"No fue posible obtener el rol de usuario");
		}
	}
	
	private void enviarUsuario()
	{
		try 
		{
			salidaComunicacion.writeUTF(ControladorVistaPrincipal.getUsuario());
		} 
		catch (IOException e) 
		{
			generarLog("Error del servidor",3,"No fue posible enviar el usuario");
		}
	}
	
	private boolean recibirRol()
	{
		boolean retorno=false;
		try 
		{
			retorno=entradaComunicacion.readBoolean();
		} 
		catch (IOException e) 
		{
			generarLog("Error del servidor",3,"No fue posible recibir el rol de usuario");
		}
		return retorno;
	}
	
	//Genera un reporte a través de la persistencia
	public void generarReporte(File archivoDestino, String usuario, int seleccion)
	{
		this.archivoDestino=archivoDestino;
		this.usuario=usuario;
		this.seleccion=seleccion;
		hiloReporte= new Thread(this);
		hiloReporte.start();

	}
	
	//Relacionado con hilos
	@Override
	public void run()
	{
		Thread hiloActual= Thread.currentThread();
		
		//Realiza acciones dependiendo del hilo
		if(hiloActual==hiloLog)
		{
			Persistencia.guardarRegistroLog(mensaje, nivel, accion);
		}
		
		if(hiloActual==hiloReporte)
		{
			try 
			{
				Persistencia.generarReporte(archivoDestino, usuario, seleccion);
			} 
			catch (PermisoDenegadoException e) 
			{
				generarLog("No se pudo guardar el reporte",2,
						"No se poseen los privilegios suficientes para crear el reporte");
			}
		}
		
		if(hiloActual==hiloCargarFarmacia)
		{
			iniciarConexion();
			solicitarModelo();
			leerModelo();
			limpiarDatos();
			establecerDatos();
		}
	}
	
	
	
	/*
	 * Métodos relacionados con el control
	 * de la clase principal en el modelo	
	 */

	//Método get farmacia
	public Farmacia getFarmacia()
	{
		return this.farmacia;
	}
	
	//Obtiene la cantidad de medicamentos en el modelo
	public int getCantidadMedicamentos()
	{
		return farmacia.getMedicamentos().size();
	}
	
	//Obtiene la cantidad de pacientes en el modelo
	public int getCantidadPacientes()
	{
		return farmacia.getPacientes().size();
	}
	
	//Obtener un arraylist con la lista de medicamentos
	public ArrayList<String> getListaMedicamentos()
	{
		//Declarar un ArrayList para la lista de medicamentos
		ArrayList<String> medicamentos=new ArrayList<String>();
		//Establecer los elementos del arraylist
		for (int contador=0;contador<farmacia.getMedicamentos().size();contador++)
		{
			medicamentos.add(farmacia.getNombreMedicamento(contador));
		}
		return medicamentos;
	}
	
	//Retorna todos los medicamentos
	public ArrayList<Medicamento> getMedicamentos()
	{
		return farmacia.getMedicamentos();
	}
	
	/*
	 * Métodos para recuperar datos de los medicamentos
	 */
	public String getNombreMedicamento(int posicion)
	{
		return this.farmacia.getNombreMedicamento(posicion);
	}
	public String getCodigoMedicamento(int posicion) {
		return this.farmacia.getCodigoMedicamento(posicion);
	}
	public String getPresentacionMedicamento(int posicion) {
		return this.farmacia.getPresentacionMedicamento(posicion);
	}
	public String getViaAdministracionMedicamento(int posicion) {
		return this.farmacia.getViaAdministracionMedicamento(posicion);
	}
	public String getConcentracionMedicamento(int posicion) {
		return this.farmacia.getConcentracionMedicamento(posicion);
	}
	public String getDosisMedicamento(int posicion) {
		return this.farmacia.getDosisMedicamento(posicion);
	}
	
	
	/*
	 * Métodos para crear un nuevo medicamento
	 */
	public void crearMedicamento(String nombre, String presentacion, String viaAdm, String codigo, String dosis, String concentracion) throws CamposVaciosException, CodigoMedicamentoDuplicadoException
	{
		//Añadir un nuevo medicamento
		this.farmacia.addMedicamento(nombre, presentacion, viaAdm, codigo, dosis, concentracion);
	}
	
	/*
	 * Métodos para establecer datos
	 * nuevos a un medicamento
	 */
	public void editarMedicamento(String nombre, String presentacion, String viaAdm, String codigo, String dosis, String concentracion, int posicion) throws CamposVaciosException, CodigoMedicamentoDuplicadoException
	{
		this.farmacia.editarMedicamento(nombre, presentacion, viaAdm, codigo, dosis, concentracion, posicion);
	}
	
	/*
	 * Métodos para eliminar un medicamento
	 */
	public void eliminarMedicamento(int posicion)
	{
		this.farmacia.eliminarMedicamento(posicion);
	}
	
	//Verifica si un medicamento tiene relación con las transacciones
	public void verificarRelacionMedicamento(int seleccion) throws ObjetoRelacionadoException
	{
		farmacia.verificarRelacionMedicamento(seleccion);
	}
	
	//Verifica si un paciente tiene relación con las transacciones
	public void verificarRelacionPaciente(int seleccion) throws ObjetoRelacionadoException
	{
		farmacia.verificarRelacionPaciente(seleccion);
	}
	
	/*
	 * Métodos para obtener los datos de los pacientes
	 */
	
	//Método para obtener la lista de nombres de pacientes
	public ArrayList<String> getListaPacientes()
	{
		//Crear un arraylist para retornar
		ArrayList<String>listaPacientes=new ArrayList<String>();
		//Recuperar los nombres de pacientes desde la farmacia
		for(int contador=0; contador<farmacia.getPacientes().size();contador++)
		{
			listaPacientes.add(farmacia.getNombrePaciente(contador));
		}
		//Retornar la lista
		return listaPacientes;
	}
	public String getNombrePaciente(int posicion)
	{
		return this.farmacia.getNombrePaciente(posicion);
	}
	public String getCorreoPaciente(int posicion)
	{
		return this.farmacia.getCorreoPaciente(posicion);
	}
	public String getCedulaPaciente(int posicion)
	{
		return this.farmacia.getCedulaPaciente(posicion);
	}
	public String getCodigoOrdenPaciente(int posicion)
	{
		return this.farmacia.getCodigoOrdenPaciente(posicion);
	}
	public String getVigenciaOrdenPaciente(int posicion)
	{
		return this.farmacia.getVigenciaOrdenMedicaPaciente(posicion);
	}
	public int getNumeroDispensacionesPaciente(int posicion)
	{
		return this.farmacia.getNumeroDispensacionesPaciente(posicion);
	}
	
	public ArrayList<Paciente> getPacientes()
	{
		return farmacia.getPacientes();
	}
	
	public void incrementarDispensaciones(int posicion)
	{
		this.farmacia.incrementarDispensacionPaciente(posicion);
	}
	/*
	 * Método para crear un nuevo paciente
	 */
	public void crearPaciente(String nombre, String correo, String cedula, String codigo, String vigencia) throws CamposVaciosException, CedulaPacienteDuplicadaException, CodigoOrdenMedicaDuplicadoException
	{
		this.farmacia.addPaciente(nombre, correo, cedula, codigo, vigencia);
	}
	
	/*
	 * Método para editar un paciente
	 */
	public void editarPaciente(String nombre, String correo, String cedula, String codigo, String vigencia, int posicion) throws CamposVaciosException, CedulaPacienteDuplicadaException, CodigoOrdenMedicaDuplicadoException
	{
		this.farmacia.editarPaciente(nombre, correo, cedula, codigo, vigencia, posicion);
	}
	
	/*
	 * Método para eliminar un paciente
	 */
	public void eliminarPaciente(int posicion)
	{
		this.farmacia.eliminarPaciente(posicion);
	}
	
	/*
	 * Métodos relacionados a recuperar información sobre las 
	 * órdenes médicas de los pacientes
	 */
	public String getCodigoOrdenMedicaPaciente(int posicion)
	{
		return this.farmacia.getCodigoOrdenMedicaPaciente(posicion);
	}
	public String getVigenciaOrdenMedicaPaciente(int posicion)
	{
		return this.farmacia.getVigenciaOrdenMedicaPaciente(posicion);
	}
	
	
	/*
	 * Métodos relacionados con recuperar información 
	 * de la clase Empleado en el modelo
	 */
	public String getNombreEmpleado()
	{
		return this.farmacia.getNombreEmpleado();
	}
	public String getCorreoEmpleado()
	{
		return this.farmacia.getCorreoEmpleado();
	}
	public String getCedulaEmpleado()
	{
		return this.farmacia.getCedulaEmpleado();
	}
	public String getCargoEmpleado()
	{
		return this.farmacia.getCargoEmpleado();
	}
	//Método para establecer y verificar los nombres del empleado
	public void setDispensario(String nombre, String correo, String cedula, String cargo) throws CamposVaciosException
	{
		this.farmacia.setDispensario(nombre, correo, cedula, cargo);;
	}
	
	public Empleado getDispensario()
	{
		return farmacia.getDispensario();
	}
	
	/*
	 * Métodos relacionados con la clase
	 * dispensación medicamento
	 */
	public void crearDispensacion(int posicionMedicamento, int posicionPaciente) throws LimiteDispensacionesException
	{
		farmacia.addDispensacion(posicionMedicamento, posicionPaciente);
	}
	
	public ArrayList<String> getListaDispensaciones()
	{
		return this.farmacia.getListaDispensaciones();
	}
	
	public ArrayList<DispensacionMedicamento> getDispensaciones()
	{
		return farmacia.getDispensaciones();
	}
	
	//Relacionados con la dispensación
	public String getCorreoPacienteDispensacion(int posicion)
	{
		return farmacia.getCorreoPacienteDispensacion(posicion);
	}
	public String getCodigoOrdenPacienteDispensacion(int posicion)
	{
		return farmacia.getCodigoOrdenPacienteDispensacion(posicion);
	}
	public String getVigenciaOrdenPacienteDispensacion(int posicion)
	{
		return farmacia.getVigenciaOrdenPacienteDispensacion(posicion);
	}
	public String getPresentacionMedicamentoDispensacion(int posicion)
	{
		return farmacia.getPresentacionmedicamentoDispensacion(posicion);
	}
	public String getViaAdministracionMedicamentoDispensacion(int posicion)
	{
		return farmacia.getViaAdministracionMedicamentoDispensacion(posicion);
	}
	public String getConcentracionMedicamentoDispensacion(int posicion)
	{
		return farmacia.getConcentracionMedicamentoDispensacion(posicion);
	}
	public String getDosisMedicamentoDispensacion(int posicion)
	{
		return farmacia.getDosisMedicamentoDispensacion(posicion);
	}
	public String getCorreoDispensarioDispensacion(int posicion)
	{
		return farmacia.getCorreoDispensarioDispensacion(posicion);
	}
	public String getCargoDispensarioDispensacion(int posicion)
	{
		return farmacia.getCargoDispensacionDispensacion(posicion);
	}
	public String getNombreDispensarioDispensacion(int posicion)
	{
		return farmacia.getNombreDispensarioDispensacion(posicion);
	}
	public String getCedulaDispensarioDispensacion(int posicion)
	{
		return farmacia.getCedulaDispensarioDispensacion(posicion);
	}
	
	public static DataInputStream getEntradaNotificaciones ()
	{
		return entradaNotificaciones;
	}

}
