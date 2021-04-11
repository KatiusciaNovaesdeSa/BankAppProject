package com.revature.model;

public class User {
	private String firstName;
	private String lastName;
	private int userID;
	private String pws;  //MUDAR
	private String usrName;

	public User() {
	}

	public User(String firstName, String lastName, String usrName, String pws) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.usrName = usrName;
		this.pws = pws;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsrName() {
		return usrName;
	}

	public void setUsrName(String usrName) {
		this.usrName = usrName;
	}

	public String getPws() {
		return pws;
	}

	public void setPws(String pws) {
		this.pws = pws;
	}

	@Override
	public String toString() {
		return "User [ firstName=" + firstName + ", lastName=" + lastName + " Username = " + usrName + ", pws = " + pws
				+ "]";
	}
}
