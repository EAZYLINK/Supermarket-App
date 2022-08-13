package application;

public class Seller {
	private Integer ID;
	private String name;
	private String password;
	private String gender;
	
	public Seller() {
	 
	this.ID = 0;
	this.name = "";
	this.password = "";
	this.gender = "";
		
	}
	
	public Seller(int ID, String name,String password, String gender) {
		 
	this.ID = ID;
	this.name =name;
	this.password = password;
	this.gender = gender;
		
	}
	

	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		this.ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

}
