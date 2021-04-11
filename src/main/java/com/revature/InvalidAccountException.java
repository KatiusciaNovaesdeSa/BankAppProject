package com.revature;

public class InvalidAccountException extends Exception{

	private static final long serialVersionUID = 1L;

	public InvalidAccountException() {
		super("Inavlid Account Type Selected");

 }
}