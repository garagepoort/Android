package be.cegeka.memento.domain.exception;

@SuppressWarnings("serial")
public class TechnicalException extends Exception {

	public TechnicalException() {
		// TODO Auto-generated constructor stub
	}

	public TechnicalException(String detailMessage) {
		super(detailMessage);
	}

	public TechnicalException(Throwable throwable) {
		super(throwable);
	}

	public TechnicalException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

}
