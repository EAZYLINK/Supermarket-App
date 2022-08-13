package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.mysql.cj.protocol.Resultset;

import DBConnection.DBHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.PasswordField;

public class LoginController implements Initializable{
	 @FXML
	    private Button clearBtn;

	    @FXML
	    private Label label_password;

	    @FXML
	    private Label label_username;

	    @FXML
	    private Button loginBtn;

	    @FXML
	    private PasswordField passwordField;

	    @FXML
	    private ComboBox<String> roleBox;

	    @FXML
	    private TextField usernameField;
	    private DBHandler handler;
	    private Connection connection;
	    private ResultSet rs;
	    private Statement st;
	    
	    String role;
	    @FXML
	    void clear(ActionEvent event) {

	    }

	    @FXML
	    void exitClick(MouseEvent event) {

	    }

	    @FXML
	    void login(ActionEvent event) {
	 
	    	
	    if(role == "Seller") {
	    	
	    	String query = "SELECT * FROM mysupermarket.sellertable WHERE name ='"+usernameField.getText()+"' AND password = '"+passwordField.getText()+"'";
	    	connection = handler.getConnection();
	    	
	    	try {
	    		
	    		st = connection.createStatement();
	    		rs = st.executeQuery(query);
	    		
	    		if(rs.next()) {
	    			Parent root = FXMLLoader.load(getClass().getResource("/FXML/Billing.fxml"));
	    			Stage stage = new Stage();
	    			Scene scene = new Scene(root);
	    			stage.setScene(scene);
	    			stage.show();
	    			loginBtn.getScene().getWindow().hide();
	    		} else
	    		{
	    			Alert alert = new Alert(Alert.AlertType.ERROR);
	    			alert.setHeaderText(null);
	    			alert.setContentText("You have entered Invalid Credentials");
	    			alert.showAndWait();
	    		}
	    		
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    	}
	    }
	    
	    else {
	 String query = "SELECT * FROM mysupermarket.admintable WHERE name ='"+usernameField.getText()+"' AND password = '"+passwordField.getText()+"'";
		    	connection = handler.getConnection();
		    	
		    	try {
		    		
		    		st = connection.createStatement();
		    		rs = st.executeQuery(query);
		    		
		    		if(rs.next()) {
		    			Parent root = FXMLLoader.load(getClass().getResource("/FXML/UpdateAdmin.fxml"));
		    			Stage stage = new Stage();
		    			Scene scene = new Scene(root);
		    			stage.setScene(scene);
		    			stage.show();
		    			loginBtn.getScene().getWindow().hide();
		    		} else
		    		{
		    			Alert alert = new Alert(Alert.AlertType.ERROR);
		    			alert.setHeaderText(null);
		    			alert.setContentText("You have entered Invalid Credentials");
		    			alert.showAndWait();
		    		}
		    		
		    	} catch (Exception e) {
		    		e.printStackTrace();
		    	}
	    	
	    }
	    }

	    @FXML
	    void selectRole(ActionEvent event) {
	   role = roleBox.getSelectionModel().getSelectedItem().toString();
	    }

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObservableList<String> roleList = FXCollections.observableArrayList("Admin", "Seller");
		roleBox.setItems(roleList);
		
		handler = new DBHandler();
	}
	
	
}
