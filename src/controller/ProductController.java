package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import DBConnection.DBHandler;
import application.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ProductController implements Initializable{
	 @FXML
	    private TableColumn<Product, Integer> ID;
	 @FXML
	    private TableColumn<Product, String> category;

	 @FXML
	    private ComboBox<String> categoryBox;
	 @FXML
	    private Button addBtn; 

	    @FXML
	    private Button clearBtn;

	    @FXML
	    private Button deleteBtn;

	    @FXML
	    private Button editBtn;

	    @FXML
	    private TextField idField;
	    @FXML
		private TableView<Product> table;
	    @FXML
	    private TableColumn<Product, String> name;
	    @FXML
	    private TextField nameField;
	    @FXML
	    private TableColumn<Product, Integer> price;
	    @FXML
	    private TextField priceField;
	    @FXML
	    private TableColumn<Product, Integer> quantity;
	    @FXML
	    private TextField quantityField;
	    Product product = new Product();
	    private DBHandler handler;
	    private Connection connection;
	    private PreparedStatement pst;
	    private Statement st;
	    private ResultSet rs;
	    ObservableList<Product> data;
	    @FXML
	    void add(ActionEvent event) {
	    
	    	if(nameField.getText().isEmpty() || idField.getText().isEmpty() || quantityField.getText().isEmpty() 
	    			|| priceField.getText().isEmpty() || categoryBox.getSelectionModel().getSelectedItem().isEmpty()) {
	    		
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText(null);
				alert.setContentText("Some fields are missing");
				alert.showAndWait();
			} else if(contains(table, idField.getText()) || contains(table, nameField.getText())) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText(null);
				alert.setContentText("Product already exist");
				alert.showAndWait();
			}
			else{ 
				
				String insert = "INSERT INTO producttable (ID, name, quantity, price, category)"
		    		+ "VALUES(?, ?, ?, ?, ?)";
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
					pst.setInt(3, Integer.valueOf(quantityField.getText()));
					pst.setInt(4, Integer.valueOf(priceField.getText()));
					pst.setString(5, categoryBox.getSelectionModel().getSelectedItem());
					
					pst.executeUpdate();
					Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
					alert.setHeaderText(null);
					alert.setContentText("Product Added Successfully");
					alert.showAndWait();
					connection.close();
					
				// set contructor to display the added seller on the tableview
					selectProduct();
					clear(event);
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

	    }
	    
	    private boolean contains(TableView<Product> tabledata, String obj) {

			for(Product item: tabledata.getItems())
			    if (item.getID().equals(obj) || item.getName().equals(obj))
			        return true;
			return false;
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

	    @FXML
	    void clear(ActionEvent event) {
	    	idField.setText("");
			nameField.setText("");
			quantityField.setText("");
			priceField.setText("");
	    }
	    
	    @FXML
	    void mouseClick(MouseEvent event) {
	    product = table.getSelectionModel().getSelectedItem();
	    idField.setText(product.getID().toString());
	    nameField.setText(product.getName());
	    quantityField.setText(product.getQuantity().toString());
	    priceField.setText(product.getPrice().toString());
	    }
	    
	    @FXML
	    void delete(ActionEvent event) {
	    
	    	if(idField.getText().isEmpty()) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText(null);
				alert.setContentText("Enter Product ID to be deleted");
				alert.showAndWait();
			} 
			
			else {
				
				try {
				
				connection = handler.getConnection();	
				String productID = idField.getText();
				String Query = "DELETE FROM mysupermarket.producttable WHERE ID="+productID;
				Statement st = connection.createStatement();
				st.executeUpdate(Query);
				selectProduct();
				
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setHeaderText(null);
				alert.setContentText("Product Deleted successfully");
				alert.showAndWait();
				
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
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
			            
			            categoryBox.setItems(outer);
			         }
			  } catch (Exception e) {
				  e.printStackTrace();
			  }
		  }
		  
	    @FXML
	    void edit(ActionEvent event) {
	    
	    	if(idField.getText().isEmpty() || nameField.getText().isEmpty() || quantityField.getText().isEmpty()
	    			|| priceField.getText().isEmpty()) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText(null);
				alert.setContentText("Missing Information");
				alert.showAndWait();
			}
			
			else {

				try {
					
					String Query = "UPDATE mysupermarket.producttable SET name='"+nameField.getText() +"' "+", quantity='"+quantityField.getText() +"' "+", price='"+priceField.getText() +"' "+", category='"+categoryBox.getSelectionModel().getSelectedItem() +"' "+" WHERE ID="+idField.getText();
				Statement st = connection.createStatement();
					st.executeUpdate(Query);
					
					Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
					alert.setHeaderText(null);
					alert.setContentText("Product Upddated");
					alert.showAndWait();
					selectProduct();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} 
	    }

	    
	    @FXML
	    void logout(MouseEvent event) {
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
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		handler = new DBHandler();
		selectProduct();
		fillCategory();
	}

}
