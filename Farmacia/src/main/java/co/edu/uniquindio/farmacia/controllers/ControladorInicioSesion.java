/**
 * Mateo Estrada Ramirez
 * 14/10/2020
 * Clase utilizada para controlar
 * los eventos de la vista fxml InicioSesion
 */
package co.edu.uniquindio.farmacia.controllers;

import java.io.IOException;

import co.edu.uniquindio.farmacia.application.MainApp;
import co.edu.uniquindio.farmacia.exceptions.VerificacionCredencialesFallidaException;
import co.edu.uniquindio.farmacia.application.AlertUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControladorInicioSesion 
{
	//Para acceder al singleton
	private SingletonFarmacia singletonFarmacia;
	
	
	//Atributos para verificar credenciales
	@FXML
	private TextField usuario;
	@FXML
	private PasswordField pass;
	private String [] credenciales;
	
	//Atributos para la vista principal
	@FXML
	private Stage vistaPrincipal;
	@FXML
	private Button inicioSesion;
	
	//Utilizado para inicializar datos a través de fxml
	@FXML
	public void initialize()
	{
		singletonFarmacia=SingletonFarmacia.getInstance();
		vistaPrincipal=new Stage();
		credenciales=new String[2];
	}
	
//	//Verifica datos e inicia la sesión
//	@FXML
//	public void iniciarSesion() throws IOException
//	{
//		//Si es exitoso, mostrar la ventana principal
//		try
//		{
//			singletonFarmacia.iniciarSesion(usuario.getText(), pass.getText());
//			ControladorVistaPrincipal.setUsuario(usuario.getText());
//			ControladorVistaPrincipal.setRolUsuario(singletonFarmacia.getRolUsuario(usuario.getText()));
//			mostrarVistaPrincipal();
//		}
//		catch (VerificacionCredencialesFallidaException e)
//		{
//			mostrarError(e.getMessage());
//		}
//	}
	
	@FXML public void iniciarSesion() throws IOException
	{
		try
		{
			credenciales[0]=usuario.getText();
			credenciales[1]=pass.getText();
			singletonFarmacia.iniciarSesion(credenciales);
			
			singletonFarmacia.generarLog("Usuario: "+usuario.getText(),1,"Inicio de sesión");
			ControladorVistaPrincipal.setUsuario(usuario.getText());
			ControladorVistaPrincipal.setRolUsuario(singletonFarmacia.getRolUsuario());
			mostrarVistaPrincipal();
		}
		catch (VerificacionCredencialesFallidaException e)
		{
			AlertUtil.showAlert(3,1, e.getMessage(), "Error al iniciar sesión");
		}
	}
	
	//Utilizado para mostrar la vista principal
	private void mostrarVistaPrincipal () throws IOException
	{
		MainApp.setRoot("VistaPrincipal");
		MainApp.setTituloVentana("Servicios Especializados - Dispensación");
		MainApp.adaptar();
	}
	
	//Mostrar ventana con un diálogo de error
	public void mostrarError(String error)
	{
		AlertUtil.showAlert(3, 1,"Usuario o contraseña incorrectos",
				"Fallo al iniciar sesión");
	}
}
