package be.cegeka.android.ShouldrTap;

public abstract class Shoulder<T extends AbstractEvent> {

	private Class<T> classType;

	public Shoulder(Class<T> classType) {
		this.classType = classType;
	}

	public Class<T> getDesiredClass() {
		return classType;
	}

	public void notifyShoulder(Object a) {
		T b = (getDesiredClass().cast(a));
		update(b);

		// if (b.getException() == null) {
		// update(b);
		// } else {
		// fail(b);
		// }
	}

	public abstract void update(T a);

	// Mogelijkheid fail-methode voor afhandeling exceptions VS apart error
	// event per exception
	// Voorlopig gekozen voor apart error event
	// public void fail(T e) {
	// e.getException().printStackTrace();
	// }
}
