/**
 * Mateo Estrada Ramirez
 * 16/09/2020
 * Clase donde se encuentra el método main y se inicia
 * JavaFX
 */
//Especificar el paquete
package co.edu.uniquindio.farmacia.application;


import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import co.edu.uniquindio.farmacia.application.MainApp;
import co.edu.uniquindio.farmacia.controllers.SingletonFarmacia;
import co.edu.uniquindio.farmacia.exceptions.ServidorInactivoException;
import co.edu.uniquindio.farmacia.persistencia.Persistencia;
//Imports de FX
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class MainApp extends Application
{
	private static Scene scene;
	private static Stage stage;
	private static final String HOST="EliteBook-8470p";
	//Constructor de la clase
	public MainApp()
	{
		//Obtener la instancia del Singleton
		SingletonFarmacia.getInstance();
	}

	public static void main(String[] args) 
	{
		//Generar un inicio en el log
		Persistencia.guardarRegistroLog("Inicio del programa",1, "Se ha iniciado el programa");
		try 
		{
			verificarServidor();
			//Llamar a launch para iniciar JavaFX
			launch(args);
		} 
		catch (ServidorInactivoException e) 
		{
			Persistencia.guardarRegistroLog("No se pudo iniciar la aplicación",3,
					"El servidor está inactivo");
			Platform.exit();
		}

	}
	
	//Inicia JavaFX
	@Override
	public void start(Stage primaryStage)
	{
		try
		{
			scene= new Scene(loadFXML("InicioSesion"));
			primaryStage.setScene(scene);
			primaryStage.setTitle("Servicios Especializados, Inicio de Sesión");
			
			Image icono = new Image(MainApp.class.getResourceAsStream("pill.png"));
			primaryStage.getIcons().add(icono);
			stage=primaryStage;
			//Mostrar el "escenario"
			primaryStage.show();
			
		}
		catch (Exception e)
		{
			Persistencia.guardarRegistroLog("No se pudo iniciar la interfaz"
					+" gráfica",3, e.getMessage());	
			e.printStackTrace();
		}
	}
	
	//Establece el nodo raíz
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }
    
    //Carga un archivo fxml
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    
    public static void adaptar()
    {
    	stage.sizeToScene();
    	stage.centerOnScreen();
    }
    
    public static void setTituloVentana(String titulo)
    {
    	stage.setTitle(titulo);
    }
    
    public static Stage getEscenario()
    {
    	return stage;
    }
	
    //Verifica si el servidor está activo
    public static void verificarServidor() throws ServidorInactivoException
    {
    	try(Socket socketVerificacion= new Socket(HOST,8083))
    	{
    		
    	} 
    	catch (UnknownHostException e) 
    	{
			Persistencia.guardarRegistroLog("No fue posible conectar al servidor",3,
					"El host es inválido");
		} 
    	catch (IOException e) 
    	{
			throw new ServidorInactivoException();
		}
    }
    
	/*
	 * Sobre-escribe el método stop...
	 * Esto con el objetivo de salvar datos antes de una salida
	 * ya sea accionada por el botón salir o forzada
	 */
	@Override
	public void stop()
	{
		try
		{
			Persistencia.guardarRegistroLog("Tratando de guardar datos",2,
					"Se están guardando los datos");
			SingletonFarmacia.getInstance().guardarFarmacia();
			SingletonFarmacia.getInstance().cerrarPrograma();
			System.exit(0);
		}
		catch(Exception e)
		{
			Persistencia.guardarRegistroLog("No se guardaron los datos",3,
					"No fue posible guardar los datos");
		}
		
	}
}
