package com.revature.model;

public class Account {
	private int accID;
	private int accType;
	private int ownerID;
	private double balance;

	public Account() {
	}

	public Account(int accID, int accType, int ownerID, int balance) {
		super();
		this.accID = accID;
		this.accType = accType;
		this.ownerID = ownerID;
		this.balance = balance;

	}

	public int getAccID() {
		return accID;
	}

	public void setAccID(int accID) {
		this.accID = accID;
	}

	public int getAccType() {
		return accType;
	}

	public void setAccType(int accType) {
		this.accType = accType;
	}

	public int getOwnerID() {
		return ownerID;
	}

	public void setOwnerID(int ownerID) {
		this.ownerID = ownerID;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "First [accID" + accID + "accType" + accType + "ownerID" + ownerID + "balance" + balance + "]";
	}
}
