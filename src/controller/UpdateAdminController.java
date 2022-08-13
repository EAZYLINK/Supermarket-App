package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ResourceBundle;

import DBConnection.DBHandler;
import javafx.event.ActionEvent;

import javafx.scene.input.MouseEvent;

import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class UpdateAdminController implements Initializable{
	@FXML
	private GridPane grid;
	@FXML
	private TextField nameField;
	@FXML
	private TextField passwordField;
	@FXML
	private Button updateBtn;
	@FXML
	private Button clearBtn;
	 
	private DBHandler handler;
	private Connection connection;

	// Event Listener on Button[#updateBtn].onAction
	@FXML
	public void update(ActionEvent event) {
	
		if(nameField.getText().isEmpty() || passwordField.getText().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Missing Information");
			alert.showAndWait();
		}
		
		else {

			try {
				
				connection = handler.getConnection();
				String Query = "UPDATE mysupermarket.admintable SET name='"+nameField.getText() +"' "+", password='"+passwordField.getText() +"' "+" WHERE ID="+1;
			Statement st = connection.createStatement();
				st.executeUpdate(Query);
				
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setHeaderText(null);
				alert.setContentText("Admin Upddated");
				alert.showAndWait();
		//		selectCategory();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
	}
	// Event Listener on Button[#clearBtn].onAction
	@FXML
	public void clear(ActionEvent event) {
		nameField.setText("");
		passwordField.setText("");
	}
	
	@FXML
    void logout(MouseEvent event) {
	 
	try {
		Parent root = FXMLLoader.load(getClass().getResource("/FXML/Login.fxml"));
		Stage stage = new Stage();
	 Scene scene = new Scene(root);
	 stage.setScene(scene);
	 stage.show();
	 updateBtn.getScene().getWindow().hide();

	} catch (IOException e) {
		
		e.printStackTrace();
	}
	 
    }
	
	@FXML
    void seller(MouseEvent event) {
	 
	try {
	Parent root = FXMLLoader.load(getClass().getResource("/FXML/Seller.fxml"));
		Stage stage = new Stage();
	 Scene scene = new Scene(root);
	 stage.setScene(scene);
	 stage.show();
	 updateBtn.getScene().getWindow().hide();

	} catch (IOException e) {
		
		e.printStackTrace();
	}
	}
	
	@FXML
    void product(MouseEvent event) {
	 
	try {
		Parent root = FXMLLoader.load(getClass().getResource("/FXML/Product.fxml"));
		Stage stage = new Stage();
	 Scene scene = new Scene(root);
	 stage.setScene(scene);
	 stage.show();
	 updateBtn.getScene().getWindow().hide();

	} catch (IOException e) {
		
		e.printStackTrace();
	}
	 
    }

	@FXML
    void category(MouseEvent event) {
	 
	try {
		Parent root = FXMLLoader.load(getClass().getResource("/FXML/Category.fxml"));
		Stage stage = new Stage();
	 Scene scene = new Scene(root);
	 stage.setScene(scene);
	 stage.show();
	 updateBtn.getScene().getWindow().hide();

	} catch (IOException e) {
		
		e.printStackTrace();
	}
	 
    }
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		handler = new DBHandler();
		
	}
}
