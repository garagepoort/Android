package be.cegeka.android.splitit.domain;

import java.util.Observable;


public class PeopleModel extends Observable
{
	private static final PeopleModel INSTANCE = new PeopleModel();
	private Person person1;
	private Person person2;


	private PeopleModel()
	{
	}


	public static PeopleModel getPeopleModel()
	{
		return INSTANCE;
	}


	public boolean arePeopleSet()
	{
		return person1 != null && person2 != null;
	}


	public void setPeople(Person person1, Person person2)
	{
		if (person1 != null && person2 != null)
		{
			this.person1 = person1;
			this.person2 = person2;
		}

		setChanged();
		notifyObservers();
	}


	public void addAmountToPerson1(double amount)
	{
		person1.addAmount(amount);

		setChanged();
		notifyObservers();
	}


	public void addAmountToPerson2(double amount)
	{
		person2.addAmount(amount);

		setChanged();
		notifyObservers();
	}


	public Person getPerson1()
	{
		return this.person1;
	}


	public Person getPerson2()
	{
		return this.person2;
	}


	public Person getDebtPerson()
	{
		if (person1.getAmount() < person2.getAmount())
		{
			return person1;
		}

		return person2;
	}


	public Person getCreditPerson()
	{
		if (person1.getAmount() > person2.getAmount())
		{
			return person1;
		}

		return person2;
	}


	public double getDebt()
	{
		return getDebtPerson().calculateDebt(getCreditPerson().getAmount());
	}


	public String getDebtAsString()
	{
		return String.format("%.2f", getDebtPerson().calculateDebt(getCreditPerson().getAmount()));
	}


	public void resetAmounts()
	{
		person1.resetAmount();
		person2.resetAmount();

		setChanged();
		notifyObservers();
	}
}
