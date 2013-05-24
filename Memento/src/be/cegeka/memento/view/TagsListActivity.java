package be.cegeka.memento.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import be.cegeka.android.ShouldrTap.Shoulder;
import be.cegeka.memento.R;
import be.cegeka.memento.model.TagsListUpdatedEvent;
import be.cegeka.memento.model.TagsModel;
import be.cegeka.memento.presenter.Presenter;

public class TagsListActivity extends Activity {

	private Presenter presenter;
	private TagsListShoulder tagsListShoulder;
	private ListView listViewTags;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tags_list);
		setupActionBar();
		
		tagsListShoulder = new TagsListShoulder();
		listViewTags = (ListView) findViewById(R.id.listViewTags);
		
		TagsModel.getInstance().addShoulder(tagsListShoulder);
		
		presenter = new Presenter();
		presenter.getTags();
	}
	
	 
	@Override
	protected void onDestroy() {
		TagsModel.getInstance().removeShoulder(tagsListShoulder);
		super.onDestroy();
	}


	private class TagsListShoulder extends Shoulder<TagsListUpdatedEvent>{

		public TagsListShoulder() {
			super(TagsListUpdatedEvent.class);
		}

		@Override
		public void update(TagsListUpdatedEvent event) {
			ListAdapter adapter = new ArrayAdapter<String>(TagsListActivity.this, android.R.layout.simple_list_item_1, event.getData());
			listViewTags.setAdapter(adapter);
		}
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.tagslist, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home :
				NavUtils.navigateUpFromSameTask(this);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
