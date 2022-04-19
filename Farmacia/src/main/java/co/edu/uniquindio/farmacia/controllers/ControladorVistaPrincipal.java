/**
 * Mateo Estrada Ramirez
 * Oct 18, 2020 4:48:38 PM
 * Utilizada para controlar los eventos 
 * de la vista principal
 */
package co.edu.uniquindio.farmacia.controllers;

import java.io.File;
import java.io.IOException;
import co.edu.uniquindio.farmacia.application.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class ControladorVistaPrincipal 
{	
	private SingletonFarmacia singletonFarmacia;
	//Para mostrar el nombre de usuario en la ventana
	@FXML private Label usuario;
	private static String nombreUsuario;
	private static boolean usuarioAdministrador;
	
	//Para el botón de salir
	@FXML private Button salida;
	
		
	//Para el panel de pestañas
	@FXML private TabPane tabPanePrincipal;
	
	//Para todas las pestañas
	@FXML private Tab tabMedicamentos;
	@FXML private Tab tabPacientes;
	@FXML private Tab tabDispensario;
	@FXML private Tab tabDispensacion;
	
	//Para el file chooser 
	private FileChooser fileChooser;
	
	//Inicializa widgets
	public void initialize()
	{
		singletonFarmacia=SingletonFarmacia.getInstance();
		usuario.setText(nombreUsuario);
		if(!usuarioAdministrador)
		{
			removerTabs();
		}
		
		configurarFileChooser();
		
	}
	
	//Establece el nombre del usuario en la cadena
	public static void setUsuario(String usuario)
	{
		nombreUsuario=usuario;
		
	}
	
	public static String getUsuario()
	{
		return nombreUsuario;
	}
	
	//Establece y obtiene datos relacionados con el rol de usuario
	public static void setRolUsuario(boolean rol)
	{
		usuarioAdministrador=rol;
	}
	
	public static boolean getRolUsuario()
	{
		return usuarioAdministrador;
	}
	
	//Configura el file chooser
	private void configurarFileChooser()
	{	
		File directorioInicial= new File(System.getProperty("user.home"));
		fileChooser=new FileChooser();
		fileChooser.setTitle("Elija una ubicación para exportar");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Archivo de texto plano",".txt"));
		fileChooser.setInitialDirectory(directorioInicial);
		fileChooser.setInitialFileName("Datos-Exportados.txt");
		
	}
	
	//Exporta todos los datos a un archivo de texto plano
	@FXML public void exportarDatos()
	{
		int seleccion=1;
		File archivoDestino=fileChooser.showSaveDialog(MainApp.getEscenario());
		Tab tabSeleccionado = tabPanePrincipal.getSelectionModel().getSelectedItem();
		
		//La selección se hace de acuerdo a la pestaña seleccionada
		if(tabSeleccionado==tabMedicamentos)
		{
			seleccion=1;
		}
		if(tabSeleccionado==tabPacientes)
		{
			seleccion=2;
		}
		if(tabSeleccionado==tabDispensario)
		{
			seleccion=3;
		}
		if(tabSeleccionado==tabDispensacion)
		{
			seleccion=4;
		}
		
		singletonFarmacia.generarReporte(archivoDestino, getUsuario(), seleccion);
	}
	
	//Remueve tabs no necesarios para quien no se administrador
	public void removerTabs()
	{
		tabPanePrincipal.getTabs().remove(tabMedicamentos);
		tabPanePrincipal.getTabs().remove(tabPacientes);
		tabPanePrincipal.getTabs().remove(tabDispensario);
	}
	
	//Añade los tab necesarios para el administrador
	public void agregarTabs()
	{
		tabPanePrincipal.getTabs().add(tabMedicamentos);
	}
	
	//Vuelve al login
	@FXML public void cerrarSesion() throws IOException
	{
		MainApp.setRoot("InicioSesion");
		MainApp.adaptar();
		MainApp.setTituloVentana("Servicios Especializados - Iniciar Sesión");
	}
	
}