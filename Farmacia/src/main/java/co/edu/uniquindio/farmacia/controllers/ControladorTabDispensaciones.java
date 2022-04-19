/**
 * Mateo Estrada Ramirez
 * Oct 21, 2020 9:10:19 PM
 * Utilizado para controlar los eventos
 * de la pestaña dispensaciones
 */
package co.edu.uniquindio.farmacia.controllers;

import co.edu.uniquindio.farmacia.exceptions.LimiteDispensacionesException;
import co.edu.uniquindio.farmacia.exceptions.SeleccionIncorrectaException;
import co.edu.uniquindio.farmacia.model.DispensacionMedicamento;
import co.edu.uniquindio.farmacia.model.Medicamento;
import co.edu.uniquindio.farmacia.model.Paciente;
import co.edu.uniquindio.farmacia.application.AlertUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ControladorTabDispensaciones 
{
	//Para acceder al modelo
	SingletonFarmacia singletonFarmacia;
	
	//Para el tableview de dispensaciones
	@FXML private TableView<DispensacionMedicamento>tablaDispensaciones;
	@FXML private TableColumn<DispensacionMedicamento, String> nombrePacienteDispensacion;
	@FXML private TableColumn<DispensacionMedicamento, String> cedulaPacienteDispensacion;
	@FXML private TableColumn<DispensacionMedicamento, String> nombreDispensarioDispensacion;
	@FXML private TableColumn<DispensacionMedicamento, String> cedulaDispensarioDispensacion;
	@FXML private TableColumn<DispensacionMedicamento, String> nombreMedicamentoDispensacion;
	@FXML private TableColumn<DispensacionMedicamento, String> codigoMedicamentoDispensacion;
	
	
	//Para el tableview de pacientes
	@FXML private TableView<Paciente> tablaPacientes;
	@FXML private TableColumn <Paciente, String> nombrePaciente;
	@FXML private TableColumn <Paciente, String> cedulaPaciente;
	@FXML private TableColumn <Paciente, String> correoPaciente;
	@FXML private TableColumn <Paciente, String> codigoOrdenPaciente;
	@FXML private TableColumn <Paciente, String> vigenciaOrdenPaciente;
	
	//Para los text field de la interfaz  relñacionados con el paciente
	@FXML private Label tCorreoPaciente;
	@FXML private Label tCodigoOrdenPaciente;
	@FXML private Label tVigenciaOrdenPaciente;
	
	//Para el tableview de medicamentos
	@FXML private TableView<Medicamento> tablaMedicamentos;
	@FXML private TableColumn<Medicamento, String> nombreMedicamento;
	@FXML private TableColumn<Medicamento,String> codigoMedicamento;
	@FXML private TableColumn <Medicamento, String> presentacionMedicamento;
	@FXML private TableColumn <Medicamento, String> viaAdministracionMedicamento;
	@FXML private TableColumn <Medicamento, String> concentracionMedicamento;
	@FXML private TableColumn <Medicamento, String> dosisMedicamento;
	
	//Para los text field en la interfaz relacionados con medicamentos
	@FXML private Label tPresentacionMedicamento;
	@FXML private Label tViaAdministracionMedicamento;
	@FXML private Label tConcentracionMedicamento;
	@FXML private Label tDosisMedicamento;
	
	//Para los text field en la interfaz relacionados con el dispensario
	@FXML private Label tCorreoDispensario;
	@FXML private Label tCargoDispensario;
	@FXML private Label tNombreDispensarioActual;
	@FXML private Label tCedulaDispensarioActual;
	@FXML private Label tCorreoDispensarioActual;
	@FXML private Label tCargoDispensarioActual;
	
	public void initialize()
	{
		singletonFarmacia=SingletonFarmacia.getInstance();
		establecerDatos();
		establecerLabelsDispensarioActual();
	}
	
	public void verificarSeleccion(int seleccion) throws SeleccionIncorrectaException
	{
		if(seleccion==-1)
		{
			throw new SeleccionIncorrectaException();
		}
	}
	
	public void establecerDatos()
	{
		tablaPacientes.setItems(singletonFarmacia.getDatosPacientes());
		tablaMedicamentos.setItems(singletonFarmacia.getDatosMedicamentos());
		tablaDispensaciones.setItems(singletonFarmacia.getDatosDipensaciones());
		establecerColumnas();
	}
	
	public void establecerColumnas()
	{
		nombrePaciente.setCellValueFactory(cellData -> cellData.getValue().getNombreProperty());
		cedulaPaciente.setCellValueFactory(cellData -> cellData.getValue().getCedulaProperty());
		correoPaciente.setCellValueFactory(cellData -> cellData.getValue().getCorreoProperty());
		codigoOrdenPaciente.setCellValueFactory(cellData -> cellData.getValue().getCodigoOrdenMedicaProperty());
		vigenciaOrdenPaciente.setCellValueFactory(cellData -> cellData.getValue().getVigenciaOrdenMedicaProperty());
		
		nombreMedicamento.setCellValueFactory(cellData -> cellData.getValue().getNombreProperty());
		codigoMedicamento.setCellValueFactory(cellData -> cellData.getValue().getCodigoProperty());
		presentacionMedicamento.setCellValueFactory(cellData -> cellData.getValue().getPresentacionProperty());
		viaAdministracionMedicamento.setCellValueFactory(cellData -> cellData.getValue().getViaAdministracionProperty());
		concentracionMedicamento.setCellValueFactory(cellData -> cellData.getValue().getConcentracionProperty());
		dosisMedicamento.setCellValueFactory(cellData -> cellData.getValue().getDosisProperty());
		
		nombrePacienteDispensacion.setCellValueFactory(cellData -> cellData.getValue().getNombrePacienteProperty());
		cedulaPacienteDispensacion.setCellValueFactory(cellData -> cellData.getValue().getCedulaPacienteProperty());
		nombreMedicamentoDispensacion.setCellValueFactory(cellData -> cellData.getValue().getNombreMedicamentoProperty());
		codigoMedicamentoDispensacion.setCellValueFactory(cellData -> cellData.getValue().getCodigoMedicamentoProperty());
		
		nombreDispensarioDispensacion.setCellValueFactory(cellData -> cellData.getValue().getNombreEmpleadoProperty());
		cedulaDispensarioDispensacion.setCellValueFactory(cellData -> cellData.getValue().getCedulaEmpleadoProperty());
	}
	
	public void establecerLabelsDispensarioActual()
	{
		tNombreDispensarioActual.setText(singletonFarmacia.getNombreEmpleado());
		tCedulaDispensarioActual.setText(singletonFarmacia.getCedulaEmpleado());
		tCorreoDispensarioActual.setText(singletonFarmacia.getCorreoEmpleado());
		tCargoDispensarioActual.setText(singletonFarmacia.getCargoEmpleado());
	}
	
	@FXML public void establecerLabelsDispensacion()
	{
		int seleccion;
		seleccion=tablaDispensaciones.getSelectionModel().getSelectedIndex();
		
		try
		{
			verificarSeleccion(seleccion);
			
			tCorreoPaciente.setText(singletonFarmacia.getCorreoPacienteDispensacion(seleccion));
			tCodigoOrdenPaciente.setText(singletonFarmacia.getCodigoOrdenPacienteDispensacion(seleccion));
			tVigenciaOrdenPaciente.setText(singletonFarmacia.getVigenciaOrdenPacienteDispensacion(seleccion));
			
			tPresentacionMedicamento.setText(singletonFarmacia.getPresentacionMedicamentoDispensacion(seleccion));
			tConcentracionMedicamento.setText(singletonFarmacia.getConcentracionMedicamentoDispensacion(seleccion));
			tDosisMedicamento.setText(singletonFarmacia.getDosisMedicamentoDispensacion(seleccion));
			tCorreoDispensario.setText(singletonFarmacia.getCorreoDispensarioDispensacion(seleccion));
			tCargoDispensario.setText(singletonFarmacia.getCargoDispensarioDispensacion(seleccion));
			tViaAdministracionMedicamento.setText(singletonFarmacia.getViaAdministracionMedicamento(seleccion));
		}
		catch (SeleccionIncorrectaException e)
		{
			AlertUtil.showAlert(3,1,e.getMessage(), "Selección Incorrecta");
			singletonFarmacia.generarLog(ControladorVistaPrincipal.getUsuario(),
					1, "Realizó una selección incorrecta");
		}
	}
	
	@FXML public void crearDispensacion()
	{
		int seleccionPaciente, seleccionMedicamento;
		seleccionPaciente=tablaPacientes.getSelectionModel().getSelectedIndex();
		seleccionMedicamento=tablaMedicamentos.getSelectionModel().getSelectedIndex();
		
		try
		{
			verificarSeleccion(seleccionPaciente);
			verificarSeleccion(seleccionMedicamento);
			singletonFarmacia.crearDispensacion(seleccionMedicamento, seleccionPaciente);
			
			singletonFarmacia.generarLog(ControladorVistaPrincipal.getUsuario(),2,"Dispensó un medicamento");
			singletonFarmacia.guardarFarmacia();
		}
		catch (SeleccionIncorrectaException e)
		{
			AlertUtil.showAlert(3,1,e.getMessage(),"Selección incorrecta");
			singletonFarmacia.generarLog(ControladorVistaPrincipal.getUsuario(),1,"Realizó una selección incorrecta");
		} 
		catch (LimiteDispensacionesException e) 
		{
			AlertUtil.showAlert(3,1,e.getMessage(),"No se pudo crear la dispensación");
			singletonFarmacia.generarLog(ControladorVistaPrincipal.getUsuario(),
					3, "Trató de dispensar a un paciente que alcanzó el límite");
		}
		finally
		{
			establecerDatos();
		}
	}
	

}
