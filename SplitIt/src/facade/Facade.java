package facade;

import static be.cegeka.android.splitit.domain.PeopleModel.getPeopleModel;
import java.io.IOException;
import android.app.Activity;
import android.widget.Toast;
import be.cegeka.android.splitit.domain.PeoplePersistence;
import be.cegeka.android.splitit.domain.Person;


public class Facade
{
	private Activity activity;
	private PeoplePersistence peoplePersistence;


	public Facade(Activity activity)
	{
		this.activity = activity;
		peoplePersistence = new PeoplePersistence(activity.getApplicationContext());
	}


	public void launch()
	{
		if (!getPeopleModel().arePeopleSet())
		{
			if (peoplePersistence.loadPersons() == null)
			{
				getPeopleModel().setPeople(new Person("Hannes"), new Person("David"));
			}
			else
			{
				getPeopleModel().setPeople(peoplePersistence.loadPersons().get(0), peoplePersistence.loadPersons().get(1));
			}
		}
	}


	public void savePeople(Person person1, Person person2) throws IOException
	{
		peoplePersistence.savePeople(person1, person2);
		getPeopleModel().setPeople(person1, person2);
	}


	public void resetAmounts() throws IOException
	{
		getPeopleModel().resetAmounts();
		save();
		Toast.makeText(activity.getApplicationContext(), "reset successful", Toast.LENGTH_SHORT).show();
	}


	public void addMoneyToPerson1(double amount) throws IOException
	{
		getPeopleModel().addAmountToPerson1(amount);
		save();
	}


	public void addMoneyToPerson2(double amount) throws IOException
	{
		getPeopleModel().addAmountToPerson2(amount);
		save();
	}


	private void save() throws IOException
	{
		peoplePersistence.savePeople(getPeopleModel().getPerson1(), getPeopleModel().getPerson2());
	}
}
