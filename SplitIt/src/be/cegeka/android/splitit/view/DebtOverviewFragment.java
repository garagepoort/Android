package be.cegeka.android.splitit.view;

import static be.cegeka.android.splitit.domain.PeopleModel.getPeopleModel;
import java.util.Observable;
import java.util.Observer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import be.cegeka.android.splitit.R;


public class DebtOverviewFragment extends Fragment implements Observer
{
	View rootView;
	private TextView debtPerson;
	private TextView creditPerson;
	private TextView debtAmount;
	private ImageView arrow;


	public DebtOverviewFragment()
	{
	}


	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getPeopleModel().addObserver(this);
	}


	@Override
	public void onDestroy()
	{
		getPeopleModel().deleteObserver(this);
		super.onDestroy();
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.fragment_debt_overview, container, false);
		this.rootView = rootView;
		initializeViews();
		setViews();
		return rootView;
	}


	@Override
	public void update(Observable observable, Object data)
	{
		setViews();
	}


	private void initializeViews()
	{
		debtPerson = (TextView) rootView.findViewById(R.id.debt_person);
		creditPerson = (TextView) rootView.findViewById(R.id.credit_person);
		debtAmount = (TextView) rootView.findViewById(R.id.debt_amount);
		arrow = (ImageView) rootView.findViewById(R.id.arrow);
	}


	private void setViews()
	{
		if (getPeopleModel().getDebt() == 0)
		{
			debtPerson.setVisibility(View.GONE);
			creditPerson.setVisibility(View.GONE);
			arrow.setVisibility(View.GONE);
		}
		else
		{
			debtPerson.setVisibility(View.VISIBLE);
			creditPerson.setVisibility(View.VISIBLE);
			arrow.setVisibility(View.VISIBLE);

			debtPerson.setText(getPeopleModel().getDebtPerson().toString());
			creditPerson.setText(getPeopleModel().getCreditPerson().toString());
		}

		debtAmount.setText("€ " + getPeopleModel().getDebtAsString());
	}

}
