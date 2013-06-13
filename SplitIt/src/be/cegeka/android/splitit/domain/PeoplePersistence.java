package be.cegeka.android.splitit.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.content.SharedPreferences;


public class PeoplePersistence
{
	private Context context;


	public PeoplePersistence(Context context)
	{
		this.context = context;
	}


	public void savePeople(Person person1, Person person2) throws IOException
	{
		deletePeople();

		SharedPreferences settings = context.getSharedPreferences("personpersist", 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("amount_person_1", Double.toString(person1.getAmount()));
		editor.putString("naam_person_1", person1.toString());
		editor.putString("amount_person_2", Double.toString(person2.getAmount()));
		editor.putString("naam_person_2", person2.toString());
		editor.commit();
	}


	public List<Person> loadPersons()
	{
		SharedPreferences settings = context.getSharedPreferences("personpersist", 0);
		if (!(settings.contains("amount_person_1") || settings.contains("amount_person_2") || settings.contains("naam_person_1") || settings.contains("naam_person_2")))
		{
			return null;
		}

		String naamPerson1 = settings.getString("naam_person_1", "");
		String amountPerson1Str = settings.getString("amount_person_1", "");
		double amountPerson1 = Double.parseDouble(amountPerson1Str);

		String naamPerson2 = settings.getString("naam_person_2", "");
		String amountPerson2Str = settings.getString("amount_person_2", "");
		double amountPerson2 = Double.parseDouble(amountPerson2Str);

		Person person1 = new Person(naamPerson1);
		person1.addAmount(amountPerson1);

		Person person2 = new Person(naamPerson2);
		person2.addAmount(amountPerson2);

		List<Person> people = new ArrayList<Person>();
		people.add(person1);
		people.add(person2);
		return people;
	}


	public void deletePeople() throws IOException
	{
		SharedPreferences settings = context.getSharedPreferences("personpersist", 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.clear();
		editor.commit();
	}
}
