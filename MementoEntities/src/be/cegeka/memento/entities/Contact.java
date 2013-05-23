package be.cegeka.memento.entities;

import java.io.Serializable;

import be.cegeka.memento.exceptions.ContactException;

public class Contact implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private long id;
	private String naam;
	private String voornaam;
	private String email;
	private String tel;

	public Contact() {
	}

	public Contact(String naam, String voornaam, String email, String tel) throws ContactException {
		setNaam(naam);
		setVoornaam(voornaam);
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

	public void setVoornaam(String voornaam) throws ContactException {
		if (voornaam == null || voornaam.isEmpty())
			throw new ContactException("voornaam is null or empty");

		this.voornaam = voornaam;
	}

	public void setEmail(String email) throws ContactException {
		if (email == null || email.isEmpty())
			throw new ContactException("email is null or empty");

		if (!email.contains("@"))
			throw new ContactException("email has invalid structure");

		this.email = email;
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

	public String getVoornaam() {
		return voornaam;
	}

	public String getEmail() {
		return email;
	}

	public String getTel() {
		return tel;
	}
}
