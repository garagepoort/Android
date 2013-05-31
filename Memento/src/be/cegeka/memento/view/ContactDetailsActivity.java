package be.cegeka.memento.view;

import static be.cegeka.memento.view.Toast.showBlueToast;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import be.cegeka.memento.R;
import be.cegeka.memento.entities.Contact;
import be.cegeka.memento.exceptions.ContactException;
import be.cegeka.memento.facade.Facade;


public class ContactDetailsActivity extends Activity
{
	private Facade facade;
	private EditText naam;
	private EditText email;
	private EditText tel;
	private Contact contact;


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_details);
		setupActionBar();
		initialize();
		initializeViews();
		fillViews();
	}


	private void initialize()
	{
		facade = new Facade(this);
	}


	private void initializeViews()
	{
		naam = (EditText) findViewById(R.id.editText_naam);
		email = (EditText) findViewById(R.id.editText_email);
		tel = (EditText) findViewById(R.id.editText_tel);
	}


	private void fillViews()
	{
		contact = facade.getPersoonlijkContact();
		if (contact != null)
		{
			naam.setText(contact.getNaam());
			email.setText(contact.getEmail());
			tel.setText(contact.getTel());
			setTitle(contact.getNaam());
		}
	}


	public void saveContact(View view)
	{
		try
		{
			if (contact != null)
			{
				contact.setNaam(naam.getText().toString());
				contact.setEmail(email.getText().toString());
				contact.setTel(tel.getText().toString());
			}
			else
			{
				contact = new Contact(naam.getText().toString(), email.getText().toString(), tel.getText().toString());
			}
			facade.saveContact(contact);
			showBlueToast(this, getString(R.string.toast_confirmation_contact_saved));
			this.finish();
		}
		catch (ContactException e)
		{
			e.printStackTrace();
			DialogCreator.showErrorDialog(e.getMessage(), this);
		}
	}


	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar()
	{
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		{
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.contact_details, menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
