package application;

public class Product {
	private Integer ID;
	private String name;
	private Integer quantity;
	private Integer price;
	private String category;
	
	public Product() {
	
		this.ID = 0;
		this.name = "";
		this.quantity = 0;
		this.price = 0;
		this.category = "";
	}

	public Product(int ID, String name, int quantity, int price, String category) {
		this.ID = ID;
		this.name = name;
		this.quantity = quantity;
		this.price = price;
		this.category = category;
	}
	
	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
