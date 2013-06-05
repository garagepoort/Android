package be.cegeka.memento.view;

import static be.cegeka.memento.domain.utilities.IPConfigurator.configureIPAddress;
import static be.cegeka.memento.view.Toast.showBlueToast;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import be.cegeka.android.ShouldrTap.Shoulder;
import be.cegeka.memento.R;
import be.cegeka.memento.domain.events.TagEvent;
import be.cegeka.memento.domain.events.TagsListUpdatedEvent;
import be.cegeka.memento.domain.shoulders.ErrorShoulder;
import be.cegeka.memento.domain.utilities.Group;
import be.cegeka.memento.facade.Facade;
import be.cegeka.memento.model.TagsModel;


public class TagsListActivity extends Activity
{
	private Facade facade;
	private TagsListShoulder tagsListShoulder;
	private TagShoulder tagShoulder;
	private ErrorShoulder errorShoulder;
	private ListView listViewTags;
	private Toast toast;


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tags_list);
		setupActionBar();
		initialize();
	}


	@SuppressLint("ShowToast")
	private void initialize()
	{
		facade = new Facade(this);
		toast = Toast.makeText(this, "", 0);

		tagShoulder = new TagShoulder();
		facade.addShoulder(tagShoulder);

		errorShoulder = new ErrorShoulder(this);
		facade.addShoulder(errorShoulder);

		tagsListShoulder = new TagsListShoulder();
		listViewTags = (ListView) findViewById(R.id.listViewTagsList);
		TagsModel.getInstance().addShoulder(tagsListShoulder);

		listViewTags.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			{
				String tag = ((Group) listViewTags.getItemAtPosition(arg2)).getTag();
				Intent intent = new Intent(TagsListActivity.this, QRCodeActivity.class);
				intent.putExtra("TAG", tag);
				startActivity(intent);
			}
		});

		listViewTags.setOnItemLongClickListener(new OnItemLongClickListener()
		{
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, final View view, final int index, long arg3)
			{
				DialogCreator.showOptionsDialog(getString(R.string.dialog_delete_tag_title), getString(R.string.dialog_delete_tag_message), getResources().getDrawable(android.R.drawable.ic_delete), TagsListActivity.this, new DialogOKedListener<Boolean>()
				{
					@Override
					public void okayed(Boolean input)
					{
						if (input)
						{
							toast = showBlueToast(TagsListActivity.this, getString(R.string.toast_delete_tag_trying));
							facade.deleteTag(((Group) listViewTags.getItemAtPosition(index)).getTag());
						}
					}
				});
				return false;
			}
		});

		ListAdapter adapter = new ArrayAdapter<Group>(TagsListActivity.this, android.R.layout.simple_list_item_1, TagsModel.getInstance().getGroups());
		listViewTags.setAdapter(adapter);
	}


	public void addToTagClicked(final View view)
	{
		DialogCreator.showEditTextDialog(this, getString(R.string.dialog_send_to_tag_title), getString(R.string.dialog_send_to_tag_message), new DialogOKedListener<String>()
		{
			@Override
			public void okayed(String input)
			{
				addToTag(view, input);
			}
		});
	}


	public void addTagClicked(final View view)
	{
		DialogCreator.showEditTextDialog(this, getString(R.string.dialog_send_to_tag_title), getString(R.string.dialog_send_to_tag_message), new DialogOKedListener<String>()
		{
			@Override
			public void okayed(String input)
			{
				addTag(view, input, true);
			}
		});
	}


	@SuppressLint("NewApi")
	private class TagShoulder extends Shoulder<TagEvent>
	{
		public TagShoulder()
		{
			super(TagEvent.class);
		}


		@Override
		public void update(TagEvent event)
		{
			toast.cancel();
			showBlueToast(TagsListActivity.this, event.getData());
			facade.getTags();
		}
	}


	private class TagsListShoulder extends Shoulder<TagsListUpdatedEvent>
	{
		public TagsListShoulder()
		{
			super(TagsListUpdatedEvent.class);
		}


		@Override
		public void update(TagsListUpdatedEvent event)
		{
			ListAdapter adapter = new ArrayAdapter<Group>(TagsListActivity.this, android.R.layout.simple_list_item_1, event.getData());
			listViewTags.setAdapter(adapter);
		}
	}


	@Override
	protected void onDestroy()
	{
		facade.removeShoulder(tagShoulder);
		facade.removeShoulder(errorShoulder);
		TagsModel.getInstance().removeShoulder(tagsListShoulder);
		super.onDestroy();
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
		getMenuInflater().inflate(R.menu.tagslist, menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if (item.getItemId() == R.id.action_settings)
		{
			configureIPAddress(this, item);
		}
		return super.onOptionsItemSelected(item);
	}


	private void addTag(final View view, String input, boolean showInputDialogWhenInvalid)
	{
		if (facade.isValidTag(input))
		{
			toast = showBlueToast(TagsListActivity.this, getString(R.string.toast_add_tag_trying));
			facade.addTag(input);
		}
		else
		{
			if (showInputDialogWhenInvalid)
			{
				addTagClicked(view);
			}
			toast.cancel();
			toast = showBlueToast(TagsListActivity.this, getString(R.string.toast_tag_invalid_input));
		}
	}


	private void addToTag(final View view, String input)
	{
		if (facade.isValidTag(input))
		{
			toast = showBlueToast(TagsListActivity.this, getString(R.string.toast_add_to_tag_trying));
			facade.addToTag(input);
		}
		else
		{
			toast.cancel();
			toast = showBlueToast(TagsListActivity.this, getString(R.string.toast_tag_invalid_input));
			addToTagClicked(view);
		}
	}
}
