package com.cegeka.cascadingcallstest.model;

import java.io.Serializable;

public class Contact implements Serializable{

	private static final long serialVersionUID = -252327402198118050L;
	private String number;
	private String name;

	public Contact(String number, String name) {
		setNumber(number);
		setName(name);
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString(){
		return getName() + ";" + getNumber(); 
	}

	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Contact)){
			return false;
		}
		Contact c = (Contact) o;
		return (getName().equals(c.getName()) && getNumber().equals(c.getNumber()));
	}

}
