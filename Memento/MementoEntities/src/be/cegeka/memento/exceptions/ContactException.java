package be.cegeka.memento.exceptions;

@SuppressWarnings("serial")
public class ContactException extends Exception {

	public ContactException() {
		super();
	}

	public ContactException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public ContactException(String message) {
		super(message);
	}

	public ContactException(Throwable throwable) {
		super(throwable);
	}

}
