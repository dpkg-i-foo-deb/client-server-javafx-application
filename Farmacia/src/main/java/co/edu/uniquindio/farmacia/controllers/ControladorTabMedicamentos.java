/**
 * Mateo Estrada Ramirez
 * Oct 18, 2020 5:11:28 PM
 * Clase utilizada para controlar los eventos
 * de la pestaña medicamentos
 */
package co.edu.uniquindio.farmacia.controllers;


import co.edu.uniquindio.farmacia.exceptions.CamposVaciosException;
import co.edu.uniquindio.farmacia.exceptions.CodigoMedicamentoDuplicadoException;
import co.edu.uniquindio.farmacia.exceptions.ObjetoRelacionadoException;
import co.edu.uniquindio.farmacia.exceptions.SeleccionIncorrectaException;
import co.edu.uniquindio.farmacia.model.Medicamento;

import java.util.Optional;

import co.edu.uniquindio.farmacia.application.AlertUtil;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ControladorTabMedicamentos 
{
	//Para acceder al modelo
	private SingletonFarmacia singletonFarmacia;
	
	//Para el tableview
	@FXML private TableView<Medicamento> tablaMedicamentos;
	@FXML private TableColumn<Medicamento, String> nombre;
	@FXML private TableColumn<Medicamento,String> codigo;
	@FXML private TableColumn <Medicamento, String> presentacion;
	@FXML private TableColumn <Medicamento, String> viaAdministracion;
	@FXML private TableColumn <Medicamento, String> concentracion;
	@FXML private TableColumn <Medicamento, String> dosis;
	
	//Para los text field en la interfaz
	@FXML private TextField tNombre;
	@FXML private TextField tCodigo;
	@FXML private TextField tPresentacion;
	@FXML private TextField tViaAdministracion;
	@FXML private TextField tConcentracion;
	@FXML private TextField tDosis;
	
	//Para búsqueda de objetos
	@FXML private TextField busqueda;
	
	
	public void initialize()
	{
		singletonFarmacia=SingletonFarmacia.getInstance();
		establecerDatos();

	}
	
	//Establece el tableview
	public void establecerDatos()
	{
		tablaMedicamentos.setItems(singletonFarmacia.getDatosMedicamentos());
		establecerColumnas();
	}
	
	//Establece los datos de las columnas
	public void establecerColumnas()
	{
		nombre.setCellValueFactory(cellData -> cellData.getValue().getNombreProperty());
		codigo.setCellValueFactory(cellData -> cellData.getValue().getCodigoProperty());
		presentacion.setCellValueFactory(cellData -> cellData.getValue().getPresentacionProperty());
		viaAdministracion.setCellValueFactory(cellData -> cellData.getValue().getViaAdministracionProperty());
		concentracion.setCellValueFactory(cellData -> cellData.getValue().getConcentracionProperty());
		dosis.setCellValueFactory(cellData -> cellData.getValue().getDosisProperty());
	}
	
	//Establece todos los datos de los textfield de acuerdo a una selección
	@FXML public void establecerTextFields()
	{
		int seleccion;
		try
		{
			seleccion=tablaMedicamentos.getSelectionModel().getSelectedIndex();
			verificarSeleccion(seleccion);
			
			tNombre.setText(singletonFarmacia.getNombreMedicamento(seleccion));
			tCodigo.setText(singletonFarmacia.getCodigoMedicamento(seleccion));
			tPresentacion.setText(singletonFarmacia.getPresentacionMedicamento(seleccion));
			tViaAdministracion.setText(singletonFarmacia.getViaAdministracionMedicamento(seleccion));
			tConcentracion.setText(singletonFarmacia.getConcentracionMedicamento(seleccion));
			tDosis.setText(singletonFarmacia.getDosisMedicamento(seleccion));
		}
		catch(SeleccionIncorrectaException e)
		{
			AlertUtil.showAlert(3, 1,e.getMessage(), "Selección incorrecta");
			singletonFarmacia.generarLog(ControladorVistaPrincipal.getUsuario(), 
					1, "Realizó una selección incorrecta");
		}

	}
	
	//Limpia los datos en los textfield
	public void limpiarDatos()
	{
		tNombre.clear();
		tCodigo.clear();
		tPresentacion.clear();
		tConcentracion.clear();
		tViaAdministracion.clear();
		tDosis.clear();
	}
	
	//Verifica la selección realizada en un widget
	public void verificarSeleccion(int seleccion)
	{
		if(seleccion==-1)
		{
			throw new SeleccionIncorrectaException();
		}
	}
	
	//Busca y selecciona un elemento de la tabla
	@FXML public void buscar_Seleccionar()
	{
		ObservableList<Medicamento> medicamentos=tablaMedicamentos.getItems();
		for(int contador=0; contador<medicamentos.size();contador++)
		{
			if(medicamentos.get(contador).getCodigo().equals(busqueda.getText()))
			{
				tablaMedicamentos.getSelectionModel().select(medicamentos.get(contador));
				tablaMedicamentos.scrollTo(medicamentos.get(contador));
			}
		}
	}
	
	//Crea un nuevo medicamento
	@FXML public void crearMedicamento()
	{
		String nombre, codigo, presentacion, viaAdministracion, concentracion, dosis;
		
		nombre=tNombre.getText();
		codigo=tCodigo.getText();
		presentacion=tPresentacion.getText();
		viaAdministracion=tViaAdministracion.getText();
		concentracion=tConcentracion.getText();
		dosis=tDosis.getText();
		
		try {
			singletonFarmacia.crearMedicamento(nombre, presentacion, viaAdministracion, codigo, 
					dosis, concentracion);
			establecerDatos();
			singletonFarmacia.generarLog(ControladorVistaPrincipal.getUsuario(),
					2, "Creó un medicamento");
			singletonFarmacia.guardarFarmacia();
		} 
		catch (CamposVaciosException e) 
		{
			AlertUtil.showAlert(3, 1, e.getMessage(),
					"No se pudo crear el medicamento.");
			singletonFarmacia.generarLog(ControladorVistaPrincipal.getUsuario(),
					1, "Trató de crear un medicamento dejando campos vacíos");
		} 
		catch (CodigoMedicamentoDuplicadoException e) 
		{
			AlertUtil.showAlert(3, 1, e.getMessage(),
					"No se pudo crear el medicamento.");
			singletonFarmacia.generarLog(ControladorVistaPrincipal.getUsuario(), 
					2, "Trató de crear un medicamento con el código duplicado");
		}
		finally
		{
			establecerDatos();
			limpiarDatos();
		}
	}
	
	//Edita los datos de un medicamento
	@FXML public void editarMedicamento()
	{
		String nombre, codigo, presentacion, viaAdministracion
				,concentracion, dosis;
		int seleccion;
		
		seleccion=tablaMedicamentos.getSelectionModel().getSelectedIndex();
		try
		{
			verificarSeleccion(seleccion);
			
			nombre=tNombre.getText();
			codigo=tCodigo.getText();
			presentacion=tPresentacion.getText();
			viaAdministracion=tViaAdministracion.getText();
			concentracion=tConcentracion.getText();
			dosis=tDosis.getText();
			
			singletonFarmacia.editarMedicamento(nombre, presentacion, viaAdministracion, codigo, dosis, concentracion, seleccion);
			singletonFarmacia.generarLog(ControladorVistaPrincipal.getUsuario(),
					1, "Editó un medicamento");
			establecerDatos();
			limpiarDatos();
			singletonFarmacia.guardarFarmacia();
		}
		catch(SeleccionIncorrectaException e)
		{
			AlertUtil.showAlert(3,1,e.getMessage(),
					"No se pudo editar el medicamento");
			singletonFarmacia.generarLog(ControladorVistaPrincipal.getUsuario(),
					1, "Realizó una selección incorrecta");
		} 
		catch (CamposVaciosException e)
		{
			AlertUtil.showAlert(3,1,e.getMessage(),
					"No se pudo editar el medicamento");
			singletonFarmacia.generarLog(ControladorVistaPrincipal.getUsuario(),
					1, "Trató de editar un medicamento dejando campos vacíos");
		} 
		catch (CodigoMedicamentoDuplicadoException e) 
		{
			AlertUtil.showAlert(3,1,e.getMessage(), "No se pudo crear el medicamento");
			singletonFarmacia.generarLog(ControladorVistaPrincipal.getUsuario(),
					2, "Trató de editar un medicamento con el código duplicado");
		}

	}
	
	//Elimina un medicamento
	@FXML public void eliminarMedicamento()
	{
		int posicion;
		Alert alerta= AlertUtil.generateConfirmationAlert("Confirmación",
				"¿Realmente desea eliminar el medicamento?");
		Optional<ButtonType> resultado;
		posicion=tablaMedicamentos.getSelectionModel().getSelectedIndex();
		try
		{
			verificarSeleccion(posicion);
			resultado=alerta.showAndWait();
			if(resultado.get()==ButtonType.YES)
			{
				singletonFarmacia.verificarRelacionMedicamento(posicion);
				singletonFarmacia.eliminarMedicamento(posicion);
				singletonFarmacia.generarLog(ControladorVistaPrincipal.getUsuario(),
						2, "Eliminó un medicamento");
				singletonFarmacia.guardarFarmacia();
			}

		}
		catch(SeleccionIncorrectaException e)
		{
			singletonFarmacia.generarLog(ControladorVistaPrincipal.getUsuario(),
					1, "Trató de eliminar un medicamento sin haberlo seleccionado");
			AlertUtil.showAlert(3, 1,e.getMessage(),"Selección incorrecta.");
		} 
		catch (ObjetoRelacionadoException e) 
		{
			AlertUtil.showAlert(3,1, e.getMessage(),"Error al eliminar");
			singletonFarmacia.generarLog(ControladorVistaPrincipal.getUsuario(),
					3,"Trató de eliminar un medicamento relacionado con una transacción");
		}
		finally
		{
			limpiarDatos();
			establecerDatos();
		}
	}
}
