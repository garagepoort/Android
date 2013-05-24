package be.cegeka.memento.view;

import static be.cegeka.memento.view.Toast.showBlueToast;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import be.cegeka.android.ShouldrTap.Shoulder;
import be.cegeka.memento.R;
import be.cegeka.memento.events.TagEvent;
import be.cegeka.memento.events.TagsListUpdatedEvent;
import be.cegeka.memento.model.TagsModel;
import be.cegeka.memento.presenter.Presenter;
import be.cegeka.memento.shoulders.ErrorShoulder;


public class TagsListActivity extends Activity
{
	private Presenter presenter;
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


	private void initialize()
	{
		presenter = new Presenter();
		tagShoulder = new TagShoulder();
		presenter.addShoulder(tagShoulder);
		errorShoulder = new ErrorShoulder(this);
		presenter.addShoulder(errorShoulder);
		tagsListShoulder = new TagsListShoulder();
		listViewTags = (ListView) findViewById(R.id.listViewTags);
		TagsModel.getInstance().addShoulder(tagsListShoulder);
		listViewTags.setOnItemLongClickListener(new OnItemLongClickListener()
		{
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, final View view, int arg2, long arg3)
			{
				DialogCreator.showOptionsDialog(getString(R.string.dialog_delete_tag_title), getString(R.string.dialog_delete_tag_message), getResources().getDrawable(android.R.drawable.ic_delete), TagsListActivity.this, new DialogOKedListener<Boolean>()
				{
					@Override
					public void okayed(Boolean input)
					{
						if (input)
						{
							presenter.deleteTag(((TextView) view).getText().toString());
						}
					}
				});
				return false;
			}
		});
		presenter.getTags();
	}


	public void addToTagClicked(View view)
	{
		DialogCreator.showEditTextDialog(this, new DialogOKedListener<String>()
		{
			@Override
			public void okayed(String input)
			{
				toast = showBlueToast(TagsListActivity.this, getString(R.string.toast_add_to_tag_trying));
				presenter.addToTag(input);
			}
		});
	}


	public void addTagClicked(View view)
	{
		DialogCreator.showEditTextDialog(this, new DialogOKedListener<String>()
		{
			@Override
			public void okayed(String input)
			{
				toast = showBlueToast(TagsListActivity.this, getString(R.string.toast_add_tag_trying));
				presenter.addTag(input);
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
			presenter.getTags();
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
			ListAdapter adapter = new ArrayAdapter<String>(TagsListActivity.this, android.R.layout.simple_list_item_1, event.getData());
			listViewTags.setAdapter(adapter);
		}
	}


	@Override
	protected void onDestroy()
	{
		presenter.removeShoulder(tagShoulder);
		presenter.removeShoulder(errorShoulder);
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
		switch (item.getItemId())
		{
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
