package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import DBConnection.DBHandler;
import application.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;

public class BillingController implements Initializable{
	@FXML
	private TextField idField;
	@FXML
	private TextField nameField;
	@FXML
	private TextField quantityField;
	@FXML
	private TextField priceField;
	@FXML
	private Button addBtn;
	@FXML
	private Button printBtn;
	@FXML
	private Button clearBtn;
	@FXML
	private ComboBox<String> filterBox;
	 @FXML
	    private Button filterBtn;
	@FXML
	private Button refreshBtn;
	@FXML
	private TableView<Product>table;
	@FXML
	private TableColumn<Product, Integer> ID;
	@FXML
	private TableColumn<Product, String> name;
	@FXML
	private TableColumn<Product, Integer> quantity;
	@FXML
	private TableColumn<Product, Integer> price;
	@FXML
	private TableColumn<Product, String> category;
	@FXML
	private Label grandTotalField;
	@FXML
	private TextArea txtArea;
	Product product = new Product();
	
	//Declare DBHandler
	private DBHandler handler;
	private Connection connection;
	private Statement st;
	private ResultSet rs;
	
	ObservableList<Product> data;
	// Event Listener on Button[#addBtn].onAction
	
	Double uPrice, uTotal, grandTotal=0.0;
	Integer availableQuantity, newQuantity;
	int i;
	// Event Listener on TableView[#table].onMouseClicked
	@FXML
	public void mouseClick(MouseEvent event) {
		product = table.getSelectionModel().getSelectedItem();
	    idField.setText(product.getID().toString());
	    nameField.setText(product.getName());
	    availableQuantity = Integer.valueOf(product.getQuantity());
	  // quantityField.setText(product.getQuantity().toString());
	    priceField.setText(product.getPrice().toString());
	}
	
	@FXML
	public void add(ActionEvent event) {
	
		if(nameField.getText().isEmpty() || idField.getText().isEmpty() || quantityField.getText().isEmpty() 
    			|| priceField.getText().isEmpty()) {
    		
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Some fields are missing");
			alert.showAndWait();
		}
		else if(availableQuantity<=Integer.valueOf(quantityField.getText())) {
			
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Not enough in Stock");
			alert.showAndWait();
		}
		else
		{ 
		    uPrice = Double.valueOf(product.getPrice().toString());
		    uTotal = uPrice * Integer.valueOf(quantityField.getText());
		    grandTotal = grandTotal + uTotal;
		    grandTotalField.setText("N" + grandTotal);
			
		i++;
		if(i==1) {
			txtArea.setText(txtArea.getText()+ "\t                ********SHOPPING POINT********\n" + "\t NUM        PRODUCT        QUANTITY        PRICE                TOTAL\n\t" + "        "+i + "        "+nameField.getText()+"            "+quantityField.getText()+"                        "+uPrice+"        "+"        "+uTotal+"\n\t");
		}
			
		else {
			txtArea.setText(txtArea.getText()+ "        "+i + "        "+nameField.getText()+"            "+quantityField.getText()+"                        "+uPrice+"        "+"        "+uTotal+"\n\t");
	
		}
		
		updateProduct();
	}
	}
	
	public void updateProduct() {
		try {
			newQuantity = availableQuantity - Integer.valueOf(quantityField.getText().toString());
			String Query = "UPDATE mysupermarket.producttable SET quantity="+newQuantity+" "+" WHERE ID="+idField.getText();
		Statement st = connection.createStatement();
			st.executeUpdate(Query);
			
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setHeaderText(null);
			alert.setContentText("Item added to shopping list Upddated");
			alert.showAndWait();
			selectProduct();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	 private void selectProduct() {
			data =FXCollections.observableArrayList();
			try { 
				connection = handler.getConnection();
				st =connection.createStatement();
				rs = st.executeQuery("SELECT * FROM mysupermarket.producttable");
				while (rs.next()) {                
	                data.add(new Product(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getString(5)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
				ID.setCellValueFactory(new PropertyValueFactory<Product, Integer>("ID"));
		       name.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
		       quantity.setCellValueFactory(new PropertyValueFactory<Product, Integer>("quantity"));
		       price.setCellValueFactory(new PropertyValueFactory<Product, Integer>("price"));
		       category.setCellValueFactory(new PropertyValueFactory<Product, String>("category"));
		       table.setItems(null); 
		      table.setItems(data);	
		}

	// Event Listener on Button[#editBtn].onAction
	@FXML
	public void print(ActionEvent event) {
		try{
			
			PrinterJob job =PrinterJob.createPrinterJob();
			if(job==null) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText(null);
				alert.setContentText("There is no job to Print");
			}
			
			boolean proceed = job.showPrintDialog(printBtn.getScene().getWindow());
			
			if(proceed) {
				
				boolean printed = job.printPage(txtArea);
				if(printed) {
					job.endJob();
				} else
				{
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText(null);
				alert.setContentText("Fail");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// Event Listener on Button[#clearBtn].onAction
	@FXML
	public void clear(ActionEvent event) {
		idField.setText("");
		nameField.setText("");
		quantityField.setText("");
		priceField.setText("");
	}
	// Event Listener on Button[#refreshBtn].onAction
	@FXML
	public void refresh(ActionEvent event) {
		selectProduct();
		
	}
	
	
	  @FXML
	    void filterAction(ActionEvent event) {
				data =FXCollections.observableArrayList();
				try {
					connection = handler.getConnection();
					st =connection.createStatement();
					rs = st.executeQuery("SELECT * FROM mysupermarket.producttable WHERE category='"+filterBox.getSelectionModel().getSelectedItem().toString()+"'");
					while (rs.next()) {                
		                data.add(new Product(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getString(5)));
		            }
				
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
					ID.setCellValueFactory(new PropertyValueFactory<Product, Integer>("ID"));
			       name.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
			       quantity.setCellValueFactory(new PropertyValueFactory<Product, Integer>("quantity"));
			       price.setCellValueFactory(new PropertyValueFactory<Product, Integer>("price"));
			       category.setCellValueFactory(new PropertyValueFactory<Product, String>("category"));
			       table.setItems(null); 
			      table.setItems(data);
	    }
	  
	  public void fillCategory() {
		  try {
		         ObservableList<String> outer = FXCollections.observableArrayList();
		         connection = handler.getConnection();
		         String sql;
		         sql = "select *from mysupermarket.categorytable";
		         st = connection.createStatement();
		         rs = st.executeQuery(sql);
		         while (rs.next()) {
		        	 ObservableList<String> inner = FXCollections.observableArrayList();
		            inner.add(rs.getString("name"));
		            outer.addAll(inner);
		            
		            filterBox.setItems(outer);
		         }
		  } catch (Exception e) {
			  e.printStackTrace();
		  }
	  }
	// Event Listener on Label.onMouseClicked
	
	@FXML public void logout(MouseEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/FXML/Login.fxml"));
			Stage stage = new Stage();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			clearBtn.getScene().getWindow().hide();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		handler =new DBHandler();
		fillCategory();
		selectProduct();
	}
}
