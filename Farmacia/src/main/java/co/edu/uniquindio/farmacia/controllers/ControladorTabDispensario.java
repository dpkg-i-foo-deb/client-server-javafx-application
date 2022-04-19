/**
 * Mateo Estrada Ramirez
 * Oct 20, 2020 8:35:38 PM
 * Utilizado para manejar los eventos que occuren
 * en la pestaña del dispensario
 */
package co.edu.uniquindio.farmacia.controllers;

import co.edu.uniquindio.farmacia.exceptions.CamposVaciosException;
import co.edu.uniquindio.farmacia.application.AlertUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ControladorTabDispensario 
{
	//Para acceder al singleton
	private SingletonFarmacia singletonFarmacia;
	
	//Para los text field
	@FXML private TextField nombre;
	@FXML private TextField cedula;
	@FXML private TextField correo;
	@FXML private TextField cargo;
	
	//Para el botón de modificar
	@FXML private Button modificar;
	
	public void initialize()
	{
		singletonFarmacia=SingletonFarmacia.getInstance();
		establecerDatos();
	}
	
	//Establece los datos del dispensario actual
	public void establecerDatos()
	{
		nombre.setText(singletonFarmacia.getNombreEmpleado());
		cedula.setText(singletonFarmacia.getCedulaEmpleado());
		correo.setText(singletonFarmacia.getCorreoEmpleado());
		cargo.setText(singletonFarmacia.getCargoEmpleado());
	}
	
	//Desbloquea los campos del dispensario
	@FXML public void desbloquearCampos()
	{
		nombre.setDisable(false);
		cedula.setDisable(false);
		correo.setDisable(false);
		cargo.setDisable(false);
		modificar.setDisable(false);
		
		singletonFarmacia.generarLog(ControladorVistaPrincipal.getUsuario(),
				2, "Desbloqueó los campos del dispensario");
	}
	
	//Bloquea los campos del dispensario
	public void bloquearCampos()
	{
		nombre.setDisable(true);
		cedula.setDisable(true);
		correo.setDisable(true);
		cargo.setDisable(true);
		modificar.setDisable(true);
	}
	
	//Edita el dispensario
	@FXML public void editarDispensario()
	{
		String nombre, cedula, correo, cargo;
		
		nombre=this.nombre.getText();
		cedula=this.cedula.getText();
		correo=this.correo.getText();
		cargo=this.cargo.getText();
		try {
			singletonFarmacia.setDispensario(nombre, correo, cedula, cargo);
			singletonFarmacia.generarLog(ControladorVistaPrincipal.getUsuario(),3, "Modificó un dispensario");
			singletonFarmacia.guardarFarmacia();
			establecerDatos();
		} 
		catch (CamposVaciosException e) 
		{
			singletonFarmacia.generarLog(ControladorVistaPrincipal.getUsuario(),
					2,"Trató de editar un dispensario dejando campos vacíos");
			AlertUtil.showAlert(3,1,e.getMessage(), "No se pudo editar el dispensario");
		}
		finally
		{
			bloquearCampos();
		}
	}
	
}
