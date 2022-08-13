package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

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
import application.Seller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.ComboBox;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class SellerController implements Initializable{
	@FXML
	private TextField idField;
	@FXML
	private TextField nameField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private ComboBox<String> genderBox;
	@FXML
	private Button addBtn;
	@FXML
	private Button deleteBtn;
	@FXML
	private Button clearBtn;
	@FXML
	private Button editBtn;
	@FXML
	private TableView<Seller> table;
	@FXML
	private TableColumn<Seller, Integer> ID;
	@FXML
	private TableColumn<Seller, String> name;
	@FXML
	private TableColumn<Seller, String> password;
	@FXML
	private TableColumn<Seller, String> gender;
	Seller seller = new Seller();
	int[] num = new int[4];
	
	//Declare DBHandler
	private DBHandler handler;
				
		//declare prepared statement
	private PreparedStatement pst;
	private Connection connection;
	private Statement st;
	private ResultSet rs;
	ObservableList<Seller> data;
	// Event Listener on ComboBox[#genderBox].onAction
	@FXML
	public void selectGender(ActionEvent event) {
	}
	
	//Create a contains method
	 public static boolean contains(TableView<Seller> tableData, String obj){
			
			for(Seller item: tableData.getItems())
			    if (item.getID().equals(obj) || item.getName().equals(obj))
			        return true;
    return false;
}
	// Event Listener on Button[#addBtn].onAction
	@FXML
	public void add(ActionEvent event) throws NumberFormatException, SQLException {
		if(nameField.getText().isEmpty() || idField.getText().isEmpty() || passwordField.getText().isEmpty() 
				|| genderBox.getSelectionModel().getSelectedItem().isEmpty()) {
		
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Some fields are missing");
			alert.showAndWait();
		}
		else if(contains(table, idField.getText()) || contains(table, nameField.getText())) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Seller already exist");
			alert.showAndWait();
		}
		else{ 
			
			String insert = "INSERT INTO sellertable (ID, name, password, gender)"
	    		+ "VALUES(?, ?, ?, ?)";
				connection = handler.getConnection();
				
				System.out.println("Connected to DataBase");
		try {
					pst = connection.prepareStatement(insert);
					
				} catch (SQLException e) {
				
					e.printStackTrace();
				}
			
		try {
				pst.setInt(1, Integer.valueOf(idField.getText()));
				pst.setString(2, nameField.getText());
				pst.setString(3, passwordField.getText());
				pst.setString(4, genderBox.getSelectionModel().getSelectedItem());
				
				pst.executeUpdate();
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setHeaderText(null);
				alert.setContentText("Seller Added Successfully");
				alert.showAndWait();
				connection.close();
				
			// set contructor to display the added seller on the tableview
				selectSeller();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	// The method below adds the input of mysql table to the TableView
	public void selectSeller() {
		data =FXCollections.observableArrayList();
		try {
			connection = handler.getConnection();
			st =connection.createStatement();
			rs = st.executeQuery("SELECT * FROM mysupermarket.sellertable");
			while (rs.next()) {                
                data.add(new Seller(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }
		
		} catch (SQLException e) {
			
		}
		
		ID.setCellValueFactory(new PropertyValueFactory<Seller, Integer>("ID"));
	       name.setCellValueFactory(new PropertyValueFactory<Seller, String>("name"));
	       password.setCellValueFactory(new PropertyValueFactory<Seller, String>("password"));
	       gender.setCellValueFactory(new PropertyValueFactory<Seller, String>("gender"));
	       table.setItems(null); 
	      table.setItems(data);
	}
	
	// Event Listener on Button[#deleteBtn].onAction
	@FXML
	void mouseClick(MouseEvent event) {
		
	//defines actions performed when a given row is selected
	seller = table.getSelectionModel().getSelectedItem();
	idField.setText(seller.getID().toString());
	nameField.setText(seller.getName());
	passwordField.setText(seller.getPassword());
	    }
	
	@FXML
	public void delete(ActionEvent event) {
		if(idField.getText().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Enter Seller ID to be deleted");
			alert.showAndWait();
		} 
		
		else {
			
			try {
			
			connection = handler.getConnection();	
			String Query = "DELETE FROM mysupermarket.sellertable WHERE ID='"+idField.getText()+"'";
			Statement st = connection.createStatement();
			st.executeUpdate(Query);
			selectSeller();
			
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setHeaderText(null);
			alert.setContentText("Seller Deleted successfully");
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
		passwordField.setText("");
	}
	// Event Listener on Button[#exitBtn].onAction
	
	@FXML
	public void edit(ActionEvent event) {
if(idField.getText().isEmpty() || nameField.getText().isEmpty() || passwordField.getText().isEmpty()) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setHeaderText(null);
		alert.setContentText("Missing Information");
		alert.showAndWait();
	}
	
	else {

		
		try {
			connection = handler.getConnection();
			String Query = "UPDATE mysupermarket.sellertable SET name='"+nameField.getText() +"' "+", password='"+passwordField.getText()+"' "+", gender='"+genderBox.getSelectionModel().getSelectedItem()+"' "+" WHERE ID="+idField.getText();
		Statement st = connection.createStatement();
			st.executeUpdate(Query);
			
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setHeaderText(null);
			alert.setContentText("Seller Upddated");
			alert.showAndWait();
			selectSeller();
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
	}
	
	 @FXML
	    void logout(MouseEvent event) {
		 Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/FXML/Login.fxml"));
			Stage stage = new Stage();
		 Scene scene = new Scene(root);
		 stage.setScene(scene);
		 stage.show();
		 addBtn.getScene().getWindow().hide();

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
			deleteBtn.getScene().getWindow().hide();
			
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
			deleteBtn.getScene().getWindow().hide();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	   }
	 
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		handler = new DBHandler();
		ObservableList<String> genderList = FXCollections.observableArrayList("Male", "Female");
		genderBox.setItems(genderList);
		selectSeller();
	}
}
