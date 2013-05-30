package be.cegeka.memento.entities;

import java.io.Serializable;

import be.cegeka.memento.exceptions.ContactException;

public class Contact implements Comparable, Serializable{

	private static final long serialVersionUID = 1L;
	private long id;
	private String naam;
	private String email;
	private String tel;

	public Contact() {
	}

	public Contact(String naam, String email, String tel) throws ContactException {
		setNaam(naam);
		setEmail(email);
		setTel(tel);
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setNaam(String naam) throws ContactException {
				if (naam == null || naam.isEmpty())
					throw new ContactException("naam is null or empty");

		this.naam = naam;
	}

	public void setEmail(String email) throws ContactException {
		if (email == null || email.isEmpty()) {
			this.email = "";

		}
		else if (!email.contains("@")) {
			throw new ContactException("email has invalid structure");
		}
		else {
			this.email = email;
		}
	}

	public void setTel(String tel) throws ContactException {
		if (tel == null || tel.isEmpty())
			throw new ContactException("tel is null or empty");

		this.tel = tel;
	}

	public long getId() {
		return id;
	}

	public String getNaam() {
		return naam;
	}

	public String getEmail() {
		return email;
	}

	public String getTel() {
		return tel;
	}

	@Override
	public String toString() {
		return naam + " " + " (" + id + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((naam == null) ? 0 : naam.hashCode());
		result = prime * result + ((tel == null) ? 0 : tel.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contact other = (Contact) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id != other.id)
			return false;
		if (naam == null) {
			if (other.naam != null)
				return false;
		} else if (!naam.equals(other.naam))
			return false;
		if (tel == null) {
			if (other.tel != null)
				return false;
		} else if (!tel.equals(other.tel))
			return false;
		return true;
	}

	@Override
	public int compareTo(Object other) {
		if(other instanceof Contact){
			return this.toString().compareTo(other.toString());
		}
		return -1;
	}


}
