package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import DBConnection.DBHandler;
import application.Category;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.TableView;

public class CategoryController implements Initializable{
	@FXML
	private TextField idField;
	@FXML
	private TextField nameField;
	@FXML
	private TextField descriptionField;
	@FXML
	private Button addBtn;
	@FXML
	private Button deleteBtn;
	@FXML
	private Button clearBtn;
	@FXML
	private Button editBtn;
	@FXML
	private TableView <Category>table;

	 @FXML
	 private TableColumn<Category, String> ID;
	 @FXML
	 private TableColumn<Category, String> name;
	 @FXML
	 private TableColumn<Category, String> description;
	Category cat = new Category();
	//Declare DBHandler
		private DBHandler handler;
	//declare prepared statement
		private PreparedStatement pst;
		private Connection connection;
		private Statement st;
		private ResultSet rs;
		ObservableList<Category> data;
		// Event Listener on ComboBox[#genderBox].onAction

	
		//This method is used to activate the contains method
		 public static boolean contains(TableView<Category> tableData, String obj){
		
					for(Category item: tableData.getItems())
					    if (item.getID().equals(obj) || item.getName().equals(obj))
					        return true;
		        return false;
		    }
		 
	 // Event Listener on Button[#addBtn].onAction
	@FXML
	public void add(ActionEvent event) {
		
		if(nameField.getText().isEmpty() || idField.getText().isEmpty() || descriptionField.getText().isEmpty()) {
		
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Some fields are missing");
			alert.showAndWait();
		} else if(contains(table, idField.getText()) || contains(table, nameField.getText())) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Category already exist");
			alert.showAndWait();
		}
		else{ 
			
			String insert = "INSERT INTO categorytable (ID, name, description)"
	    		+ "VALUES(?, ?, ? )";
				connection = handler.getConnection();
		try {
					pst = connection.prepareStatement(insert);
					
				} catch (SQLException e) {
				
					e.printStackTrace();
				}
			
		try {
				pst.setInt(1, Integer.valueOf(idField.getText()));
				pst.setString(2, nameField.getText());
				pst.setString(3, descriptionField.getText());
				
				pst.executeUpdate();
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setHeaderText(null);
				alert.setContentText("Category Added Successfully");
				alert.showAndWait();
				connection.close();
			// set contructor to display the added seller on the tableview
				selectCategory();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	private void selectCategory() {
		data =FXCollections.observableArrayList();
		try {
			connection = handler.getConnection();
			st =connection.createStatement();
			rs = st.executeQuery("SELECT * FROM mysupermarket.categorytable");
			while (rs.next()) {                
                data.add(new Category(rs.getString(1), rs.getString(2), rs.getString(3)));         
            }
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
			ID.setCellValueFactory(new PropertyValueFactory<Category, String>("ID"));
	       name.setCellValueFactory(new PropertyValueFactory<Category, String>("name"));
	       description.setCellValueFactory(new PropertyValueFactory<Category, String>("description"));
	       table.setItems(null); 
	      table.setItems(data);
		
	}
	// Event Listener on Button[#deleteBtn].onAction
	@FXML
	public void delete(ActionEvent event) {
		if(idField.getText().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Enter Category ID to be deleted");
			alert.showAndWait();
		} 
		
		else {
			
			try {
			
			connection = handler.getConnection();	
			String categoryID = idField.getText();
			String Query = "DELETE FROM mysupermarket.categorytable WHERE ID="+categoryID;
			Statement st = connection.createStatement();
			st.executeUpdate(Query);
			selectCategory();
			
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setHeaderText(null);
			alert.setContentText("Category Deleted successfully");
			alert.showAndWait();
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	// Event Listener on Button[#clearBtn].onAction
	@FXML
	public void clear(ActionEvent event) {
		idField.setText("");
		nameField.setText("");
		descriptionField.setText("");
		
	}
	
	  @FXML
	    void mouseClick(MouseEvent event) {
		 cat = table.getSelectionModel().getSelectedItem();
		idField.setText(cat.getID().toString());
		nameField.setText(cat.getName());
		descriptionField.setText(cat.getDescription());
	    }
	  
	// Event Listener on Button[#extBtn].onAction
	@FXML
	public void edit(ActionEvent event) {
		if(idField.getText().isEmpty() || nameField.getText().isEmpty() || descriptionField.getText().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Missing Information");
			alert.showAndWait();
		}
		
		else {

			
			try {
				
				String Query = "UPDATE mysupermarket.categorytable SET name='"+nameField.getText() +"' "+", description='"+descriptionField.getText() +"' "+" WHERE ID="+idField.getText();
			Statement st = connection.createStatement();
				st.executeUpdate(Query);
				
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setHeaderText(null);
				alert.setContentText("Category Upddated");
				alert.showAndWait();
				selectCategory();
			} catch (Exception e) {
				e.printStackTrace();
			}
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
		deleteBtn.getScene().getWindow().hide();
		
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
			deleteBtn.getScene().getWindow().hide();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
   }
   

	@FXML public void logout(MouseEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/FXML/Login.fxml"));
			Stage stage = new Stage();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			deleteBtn.getScene().getWindow().hide();
		deleteBtn.getScene().getWindow().hide();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		handler = new DBHandler();
		selectCategory();
	}
}
