package be.cegeka.android.splitit.view;

import static be.cegeka.android.splitit.domain.PeopleModel.getPeopleModel;
import static be.cegeka.android.splitit.view.DialogCreator.showConfirmationDialog;
import static be.cegeka.android.splitit.view.DialogCreator.showErrorDialog;
import static be.cegeka.android.splitit.view.DialogCreator.showSetPeopleDialog;
import java.io.IOException;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;
import be.cegeka.android.splitit.R;
import facade.Facade;


public class MainActivity extends FragmentActivity
{
	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;
	MoneyInputFragment moneyInputFragment;
	DebtOverviewFragment deptOverviewFragment;
	Facade facade;
	private EditText person1InputField;
	private EditText person2InputField;


	public void addMoneyToPerson1(View view)
	{
		person1InputField = (EditText) moneyInputFragment.getView().findViewById(R.id.add_amount_p1);
		double amount = 0;
		try
		{
			amount = Double.parseDouble(person1InputField.getText().toString());
			try
			{
				facade.addMoneyToPerson1(amount);
			}
			catch (IOException e)
			{
				e.printStackTrace();
				showErrorDialog(this);
			}
			person1InputField.setText("");
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(person1InputField.getWindowToken(), 0);
			Toast.makeText(view.getContext(), "€ " + String.format("%.2f", amount) + " was added to " + getPeopleModel().getPerson1().toString(), Toast.LENGTH_LONG).show();
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
		}
	}


	public void addMoneyToPerson2(View view)
	{
		person2InputField = (EditText) moneyInputFragment.getView().findViewById(R.id.add_amount_p2);
		double amount = 0;
		try
		{
			amount = Double.parseDouble(person2InputField.getText().toString());
			try
			{
				facade.addMoneyToPerson2(amount);
			}
			catch (IOException e)
			{
				e.printStackTrace();
				showErrorDialog(this);
			}
			person2InputField.setText("");
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(person2InputField.getWindowToken(), 0);
			Toast.makeText(view.getContext(), "€ " + String.format("%.2f", amount) + " was added to " + getPeopleModel().getPerson2().toString(), Toast.LENGTH_LONG).show();
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
		}
	}


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		facade = new Facade(this);
		facade.launch();
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if (item.getItemId() == R.id.action_settings)
		{
			goToSettings(null);
		}
		if (item.getItemId() == R.id.reset_amounts)
		{
			resetAmounts(null);
		}
		return super.onOptionsItemSelected(item);
	}


	public void resetAmounts(View view)
	{
		showConfirmationDialog(this);
	}


	public void goToSettings(View view)
	{
		showSetPeopleDialog(this);
	}


	public class SectionsPagerAdapter extends FragmentPagerAdapter
	{
		public SectionsPagerAdapter(FragmentManager fm)
		{
			super(fm);
		}


		@Override
		public Fragment getItem(int position)
		{
			if (position == 0)
			{
				moneyInputFragment = new MoneyInputFragment();
				return moneyInputFragment;
			}
			else
			{
				deptOverviewFragment = new DebtOverviewFragment();
				return deptOverviewFragment;
			}
		}


		@Override
		public int getCount()
		{
			return 2;
		}


		@Override
		public CharSequence getPageTitle(int position)
		{
			switch (position)
			{
			case 0:
				return "Money Input";
			case 1:
				return "Debt Overview";
			}
			return null;
		}
	}
}
