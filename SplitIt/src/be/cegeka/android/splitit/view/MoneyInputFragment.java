package be.cegeka.android.splitit.view;

import static be.cegeka.android.splitit.domain.PeopleModel.getPeopleModel;
import java.util.Observable;
import java.util.Observer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import be.cegeka.android.splitit.R;


public class MoneyInputFragment extends Fragment implements Observer
{
	View rootView;


	public MoneyInputFragment()
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
		View rootView = inflater.inflate(R.layout.fragment_money_input, container, false);
		this.rootView = rootView;
		setPersonNames();
		return rootView;
	}


	private void setPersonNames()
	{
		TextView person1Label = (TextView) rootView.findViewById(R.id.person_1);
		person1Label.setText(getPeopleModel().getPerson1().toString());

		TextView person2Label = (TextView) rootView.findViewById(R.id.person_2);
		person2Label.setText(getPeopleModel().getPerson2().toString());
	}


//	private void addListeners()
//	{
//		ImageView addToPerson1Button = (ImageView) rootView.findViewById(R.id.buttonAdd1);
//		addToPerson1Button.set
//
//		ImageView addToPerson2Button = (ImageView) rootView.findViewById(R.id.buttonAdd2);
//		;
//	}


	@Override
	public void update(Observable observable, Object data)
	{
		setPersonNames();
	}

}
