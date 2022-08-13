package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;

public class HomePageController implements Initializable{

	@FXML
	private ProgressBar progressBar;
	 @FXML
	private Button continueBtn;
	 @FXML
	private ProgressIndicator indicator;
	
	 @FXML
	    void proceed(ActionEvent event) throws IOException {

	 try {
         for (int i = 0; i <=100; i++) {
             Thread.sleep(40);
         progressBar.setProgress(i);
        indicator.setProgress(i);
         }
     } catch (Exception e) {

     }
	 
	 Stage stage = new Stage();
	 Parent root = FXMLLoader.load(getClass().getResource("/FXML/Login.fxml"));
	 Scene scene = new Scene(root);
	 stage.setScene(scene);
	 stage.show();
	 continueBtn.getScene().getWindow().hide();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
			
		
	}



	
}
