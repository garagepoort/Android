package be.cegeka.android.splitit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class MoneyInputFragment extends Fragment {

	public MoneyInputFragment() {
	}

	public void addMoneyToPerson1(View view) {
		Toast.makeText(view.getContext(), "added to 1", Toast.LENGTH_LONG).show();
	}
	
	public void addMoneyToPerson2(View view) {
		Toast.makeText(view.getContext(), "added to 2", Toast.LENGTH_LONG).show();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_money_input, container, false);
		return rootView;
	}

}
