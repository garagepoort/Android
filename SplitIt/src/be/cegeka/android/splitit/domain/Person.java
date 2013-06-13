package be.cegeka.android.splitit.domain;

public class Person
{
	private String naam;
	private double amountSpent;


	public Person(String naam)
	{
		this.naam = naam;
	}


	public void changeNaam(String naam)
	{
		this.naam = naam;
	}


	public void addAmount(double amount)
	{
		amountSpent += amount;
	}


	public double getAmount()
	{
		return this.amountSpent;
	}


	public void resetAmount()
	{
		this.amountSpent = 0;
	}


	@Override
	public boolean equals(Object o)
	{
		if (o instanceof Person)
		{
			return ((Person) o).toString().equals(this.toString());
		}
		return false;
	}


	@Override
	public String toString()
	{
		return this.naam;
	}


	public double calculateDebt(double amount)
	{
		if (amountSpent > amount)
		{
			return 0;
		}

		return (amount - amountSpent) / 2;
	}
}
