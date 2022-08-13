package application;

public class Item {
	
	private String item;
	private double price;

	public Item() {
		this.item = "";
		this.price = 0.0;
	}

	public Item(String item, double price) {
		super();
		this.item = item;
		this.price = price;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	
}
