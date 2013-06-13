package be.cegeka.android.ShouldrTap;

public abstract class Shoulder<T extends AbstractEvent<?>>
{

	private Class<T> classType;


	public Shoulder(Class<T> classType)
	{
		this.classType = classType;
	}


	public Class<T> getDesiredClass()
	{
		return classType;
	}


	public void notifyShoulder(Object a)
	{
		T b = (getDesiredClass().cast(a));
		update(b);
	}


	public abstract void update(T a);
}
