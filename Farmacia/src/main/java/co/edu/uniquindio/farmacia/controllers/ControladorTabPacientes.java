/**
 * Mateo Estrada Ramirez
 * Oct 19, 2020 10:46:22 AM
 * Utilizado para controlar los eventos 
 * de la pestaña pacientes
 */
package co.edu.uniquindio.farmacia.controllers;

import co.edu.uniquindio.farmacia.exceptions.CamposVaciosException;
import co.edu.uniquindio.farmacia.exceptions.CedulaPacienteDuplicadaException;
import co.edu.uniquindio.farmacia.exceptions.CodigoOrdenMedicaDuplicadoException;
import co.edu.uniquindio.farmacia.exceptions.ObjetoRelacionadoException;
import co.edu.uniquindio.farmacia.exceptions.SeleccionIncorrectaException;
import co.edu.uniquindio.farmacia.model.Paciente;

import java.util.Optional;

import co.edu.uniquindio.farmacia.application.AlertUtil;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ControladorTabPacientes 
{
	//Para acceder al modelo
	private SingletonFarmacia singletonFarmacia;
	
	//Para el tableview
	@FXML private TableView <Paciente> tablaPacientes;
	@FXML private TableColumn <Paciente, String> nombre;
	@FXML private TableColumn <Paciente, String> cedula;
	@FXML private TableColumn <Paciente, String> correo;
	@FXML private TableColumn <Paciente, String> codigoOrden;
	@FXML private TableColumn <Paciente, String> vigenciaOrden;
	
	//Para los text field de la interfaz
	@FXML private TextField tNombre;
	@FXML private TextField tCedula;
	@FXML private TextField tCorreo;
	@FXML private TextField tCodigoOrden;
	@FXML private TextField tVigenciaOrden;
	
	//Para la búsqueda en la tabla
	@FXML private TextField busqueda;
	
	public void initialize()
	{
		singletonFarmacia= SingletonFarmacia.getInstance();
		establecerDatos();
	}
	
	//Establece todos los datos del tableview
	public void establecerDatos()
	{
		tablaPacientes.setItems(singletonFarmacia.getDatosPacientes());
		establecerColumnas();
	}
	
	//Establece las columnas del tableview
	public void establecerColumnas()
	{
		nombre.setCellValueFactory(cellData -> cellData.getValue().getNombreProperty());
		cedula.setCellValueFactory(cellData -> cellData.getValue().getCedulaProperty());
		correo.setCellValueFactory(cellData -> cellData.getValue().getCorreoProperty());
		codigoOrden.setCellValueFactory(cellData -> cellData.getValue().getCodigoOrdenMedicaProperty());
		vigenciaOrden.setCellValueFactory(cellData -> cellData.getValue().getVigenciaOrdenMedicaProperty());
	}

	//Limpia los datos de los text field
	public void limpiarDatos()
	{
		tNombre.clear();
		tCedula.clear();
		tCorreo.clear();
		tCodigoOrden.clear();
		tVigenciaOrden.clear();
	}
	
	//Establece todos los datos de los text field de acuerdo a una selección
	@FXML public void establecerTextFields()
	{
		int seleccion;
		try
		{
			seleccion=tablaPacientes.getSelectionModel().getSelectedIndex();
			verificarSeleccion(seleccion);
			
			tNombre.setText(singletonFarmacia.getNombrePaciente(seleccion));
			tCedula.setText(singletonFarmacia.getCedulaPaciente(seleccion));
			tCorreo.setText(singletonFarmacia.getCorreoPaciente(seleccion));
			tCodigoOrden.setText(singletonFarmacia.getCodigoOrdenMedicaPaciente(seleccion));
			tVigenciaOrden.setText(singletonFarmacia.getVigenciaOrdenMedicaPaciente(seleccion));
		}
		catch (SeleccionIncorrectaException e)
		{
			AlertUtil.showAlert(3,1,e.getMessage(),"Selección Incorrecta");
			singletonFarmacia.generarLog(ControladorVistaPrincipal.getUsuario(),
					1, "Realizó una selección incorrecta");
		}
	}
	
	//Busca y selecciona un paciente
	@FXML public void buscar_Seleccionar()
	{
		ObservableList<Paciente> pacientes=tablaPacientes.getItems();
		
		for(int contador=0;contador<pacientes.size();contador++)
		{
			if(pacientes.get(contador).getCedula().equals(busqueda.getText()))
			{
				tablaPacientes.getSelectionModel().select(pacientes.get(contador));
				tablaPacientes.scrollTo(pacientes.get(contador));
			}
		}
	}
	
	//Verifica una selección realizada 
	public void verificarSeleccion(int seleccion)
	{
		if(seleccion==-1)
		{
			throw new SeleccionIncorrectaException ();
		}
	}
	
	//Crea un nuevo paciente
	@FXML public void crearPaciente()
	{
		String nombre, cedula, correo, codigoOrden,vigenciaOrden;
		
		nombre=tNombre.getText();
		cedula=tCedula.getText();
		correo=tCorreo.getText();
		codigoOrden=tCodigoOrden.getText();
		vigenciaOrden=tVigenciaOrden.getText();
		
		try
		{
			singletonFarmacia.crearPaciente(nombre, correo, cedula, codigoOrden, vigenciaOrden);
			establecerDatos();
			singletonFarmacia.generarLog(ControladorVistaPrincipal.getUsuario(),
					2, "Registró un paciente");
			singletonFarmacia.guardarFarmacia();
		}
		catch(CamposVaciosException e)
		{
			AlertUtil.showAlert(3,1, e.getMessage(), "No se pudo crear el paciente");
			singletonFarmacia.generarLog(ControladorVistaPrincipal.getUsuario(),
					1, "Trató de registrar un paciente dejando campos vacíos");
		}
		catch(CedulaPacienteDuplicadaException e)
		{
			AlertUtil.showAlert(3,1,e.getMessage(),"No fue posible crear el paciente");
			singletonFarmacia.generarLog(ControladorVistaPrincipal.getUsuario(),
					2,"Trató de crear un paciente con una cédula ya existente");
		}
		catch(CodigoOrdenMedicaDuplicadoException e)
		{
			AlertUtil.showAlert(3,1,e.getMessage(),"No fue posible crear el paciente");
			singletonFarmacia.generarLog(ControladorVistaPrincipal.getUsuario(),
					2, "Trató de crear un paciente con un código de órden duplicado");
		}
		finally
		{
			limpiarDatos();
			establecerDatos();
		}
	}
	
	//Edita los datos de un paciente
	@FXML public void editarPaciente()
	{
		String nombre, cedula, correo, codigoOrden, vigenciaOrden;
		int seleccion;
		
		seleccion=tablaPacientes.getSelectionModel().getSelectedIndex();
		try
		{
			verificarSeleccion(seleccion);
			
			nombre=tNombre.getText();
			cedula=tCedula.getText();
			correo=tCorreo.getText();
			codigoOrden=tCodigoOrden.getText();
			vigenciaOrden=tVigenciaOrden.getText();
			
			singletonFarmacia.editarPaciente(nombre, correo, cedula, codigoOrden, vigenciaOrden, seleccion);
			singletonFarmacia.generarLog(ControladorVistaPrincipal.getUsuario(),
					2, "Editó un paciente");
			singletonFarmacia.guardarFarmacia();
		}
		catch(SeleccionIncorrectaException e)
		{
			AlertUtil.showAlert(3,1,e.getMessage(),"Selección incorrecta");
			singletonFarmacia.generarLog(ControladorVistaPrincipal.getUsuario(),
					1, "Realizó una selección incorrecta");
		}
		catch(CamposVaciosException e)
		{
			AlertUtil.showAlert(3,1,e.getMessage(),"No se pudo editar el paciente");
			singletonFarmacia.generarLog(ControladorVistaPrincipal.getUsuario(),
					2,"Trató de editar un paciente dejando campos vacíos");
		}
		catch(CodigoOrdenMedicaDuplicadoException e)
		{
			AlertUtil.showAlert(3,1,
					"No puede utilizar el mismo código de orden en dos"
					+ "\npacientes. Este incidente será reportado.","No se pudo editar el paciente");
			singletonFarmacia.generarLog(ControladorVistaPrincipal.getUsuario(),
					3,"Trató de editar la información de un paciente utilizando el código de órden de otro");
		}
		catch(CedulaPacienteDuplicadaException e)
		{
			AlertUtil.showAlert(3,1,e.getMessage(),"No fue posible crear el paciente");
			singletonFarmacia.generarLog(ControladorVistaPrincipal.getUsuario(),
					2,"Trató de crear un paciente con una cédula ya existente");
		}
		finally
		{
			establecerDatos();
			limpiarDatos();
		}
	}
	
	//Elimina un paciente
	@FXML public void eliminarPaciente()
	{
		Alert alerta=AlertUtil.generateConfirmationAlert("Confirmación",
				"¿Realmente desea eliminar el paciente?");
		Optional<ButtonType> resultado;
		int seleccion;
		seleccion=tablaPacientes.getSelectionModel().getSelectedIndex();
		try
		{
			verificarSeleccion(seleccion);
			resultado=alerta.showAndWait();
			if(resultado.get()==ButtonType.YES)
			{
				singletonFarmacia.verificarRelacionPaciente(seleccion);
				singletonFarmacia.eliminarPaciente(seleccion);
				singletonFarmacia.generarLog(ControladorVistaPrincipal.getUsuario(),
						2,"Eliminó un paciente");
				
				singletonFarmacia.guardarFarmacia();
			}
		}
		catch (SeleccionIncorrectaException e)
		{
			singletonFarmacia.generarLog(ControladorVistaPrincipal.getUsuario(),
					1, "Realizó una selección incorrecta");
			AlertUtil.showAlert(3,1,e.getMessage(),"No se pudo eliminar el paciente");
		} catch (ObjetoRelacionadoException e)
		{
			AlertUtil.showAlert(3,1,e.getMessage(),
					"Error al eliminar");
			singletonFarmacia.generarLog(ControladorVistaPrincipal.getUsuario(),
					3, "Trató de eliminar un paciente relacionado a una dispensación");
		}
		finally
		{
			establecerDatos();
			limpiarDatos();
		}
	}
	
	
}
