/**
 * Mateo Estrada Ramirez
 * Oct 19, 2020 8:50:27 AM
 * Used to create and show JavaFX alerts
 */
package co.edu.uniquindio.farmacia.application;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class AlertUtil 
{
	//Shows an alert window according to the user preferences
	public static void showAlert (int level, int buttonType, String message, String title)
	{
		Alert alert=new Alert(null);
		switch(level)
		{
			case 1:
				switch (buttonType)
				{
				case 1:
					alert = new Alert(AlertType.NONE,message ,ButtonType.OK);
				break;
					
				case 2:
					alert = new Alert(AlertType.NONE,message ,ButtonType.CLOSE);
				break;
					
				case 3:
					alert = new Alert(AlertType.NONE,message ,ButtonType.CANCEL);
				break;
				
				case 4: 
					alert= new Alert(AlertType.NONE,message, ButtonType.FINISH);
				break;
				}
			break;
			
			case 2: 
				switch (buttonType)
				{
				case 1:
					alert = new Alert(AlertType.INFORMATION,message ,ButtonType.OK);
				break;
					
				case 2:
					alert = new Alert(AlertType.INFORMATION,message ,ButtonType.CLOSE);
				break;
					
				case 3:
					alert = new Alert(AlertType.INFORMATION,message ,ButtonType.CANCEL);
				break;
				case 4: 
					alert= new Alert(AlertType.INFORMATION,message, ButtonType.FINISH);
				break;
				}
			break;
			
			case 3:
				switch (buttonType)
				{
				case 1:
					alert = new Alert(AlertType.ERROR,message ,ButtonType.OK);
				break;
					
				case 2:
					alert = new Alert(AlertType.ERROR,message ,ButtonType.CLOSE);
				break;
					
				case 3:
					alert = new Alert(AlertType.ERROR,message ,ButtonType.CANCEL);
				break;
				case 4: 
					alert= new Alert(AlertType.ERROR,message, ButtonType.FINISH);
				break;
				}
			break;
		}
		alert.setTitle(title);
		alert.showAndWait();
	}
	
	//Generates a confirmation dialog
	public static Alert generateConfirmationAlert(String title, String content)
	{
		Alert alert= new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setContentText(content);
		alert.getButtonTypes().clear();
		alert.getButtonTypes().add(ButtonType.YES);
		alert.getButtonTypes().add(ButtonType.NO);
		
		return alert;
	}
}
