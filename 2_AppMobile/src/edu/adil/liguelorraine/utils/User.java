package edu.adil.liguelorraine.utils;

public class User {

	private int    account_id = -1   ,
				   status     = -1   ;
	
	private String account_firstname = null ,
			       account_lastname  = null ;
	
	public User() {  } ;
	
	public void display(){
		System.out.println(this.account_firstname + " " + this.account_lastname + " has id " + this.account_id );
	}

	public int getAccount_id() {
		return account_id;
	}

	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getAccount_firstname() {
		return account_firstname;
	}

	public void setAccount_firstname(String account_firstname) {
		this.account_firstname = account_firstname;
	}

	public String getAccount_lastname() {
		return account_lastname;
	}

	public void setAccount_lastname(String account_lastname) {
		this.account_lastname = account_lastname;
	} ;
	
};