package application;

import java.util.HashMap;

public class Category {
	
private String ID;
private String name;
private String description;

	public Category() {
		this.ID="";
		this.name = "";
		this.description = "";
	}
	public Category(String ID, String name, String description) {
		this.ID=ID;
		this.name = name;
		this.description = description;
	}
	
	public String getID() {
		return ID;
	}
	
	public void setID(String ID) {
		this.ID = ID;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	public HashMap<String, Integer> fillCategory() {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		return map;
	}
	
}
