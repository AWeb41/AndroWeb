package com.androweb.application.engine.app.dashboard.youtube;

import com.google.android.youtube.player.YouTubeIntents;

import com.androweb.application.R;
import com.androweb.application.engine.app.dashboard.youtube.adapter.DemoArrayAdapter;
import com.androweb.application.engine.app.dashboard.youtube.adapter.DemoListViewItem;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.androweb.application.ApplicationActivity;


public class YoutubeDashboard extends Fragment implements OnItemClickListener 
{

	private List<DemoListViewItem> intentItems;
	

	private View rootView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// TODO: Implement this method
		rootView = inflater.inflate(R.layout.fragment_application_dashboard_youtube, container, false);
		return rootView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onViewCreated(view, savedInstanceState);
		
		intentItems = new ArrayList<DemoListViewItem>();
		intentItems.add(new IntentItem("Play Video", IntentType.PLAY_VIDEO));
		intentItems.add(new IntentItem("Open Playlist", IntentType.OPEN_PLAYLIST));
		intentItems.add(new IntentItem("Play Playlist", IntentType.PLAY_PLAYLIST));
		intentItems.add(new IntentItem("Open User", IntentType.OPEN_USER));
		intentItems.add(new IntentItem("Open Search Results", IntentType.OPEN_SEARCH));
		intentItems.add(new IntentItem("Upload Video", IntentType.UPLOAD_VIDEO));

		ListView listView = (ListView) view.findViewById(R.id.intent_list);
		DemoArrayAdapter adapter = new DemoArrayAdapter(getActivity(), R.layout.list_item_youtube, intentItems);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);

		TextView youTubeVersionText = (TextView) view.findViewById(R.id.youtube_version_text);
		String version = YouTubeIntents.getInstalledYouTubeVersionName(getActivity());
		if (version != null) {
			String text = String.format(getString(R.string.youtube_currently_installed), version);
			youTubeVersionText.setText(text);
		} else {
			youTubeVersionText.setText(getString(R.string.youtube_not_installed));
		}
	}

	public boolean isIntentTypeEnabled(IntentType type) {
		switch (type) {
			case PLAY_VIDEO:
				return YouTubeIntents.canResolvePlayVideoIntent(getActivity());
			case OPEN_PLAYLIST:
				return YouTubeIntents.canResolveOpenPlaylistIntent(getActivity());
			case PLAY_PLAYLIST:
				return YouTubeIntents.canResolvePlayPlaylistIntent(getActivity());
			case OPEN_SEARCH:
				return YouTubeIntents.canResolveSearchIntent(getActivity());
			case OPEN_USER:
				return YouTubeIntents.canResolveUserIntent(getActivity());
			case UPLOAD_VIDEO:
				return YouTubeIntents.canResolveUploadIntent(getActivity());
		}

		return false;
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		// TODO: Implement this method
		IntentItem clickedIntentItem = (IntentItem) intentItems.get(position);

		Intent intent;
		switch (clickedIntentItem.type) {
			case PLAY_VIDEO:
				intent = YouTubeIntents.createPlayVideoIntentWithOptions(getActivity(), DeveloperKey.VIDEO_ID, true, false);
				startActivity(intent);
				break;
			case OPEN_PLAYLIST:
				//intent = YouTubeIntents.createOpenPlaylistIntent(getActivity(), DeveloperKey.PLAYLIST_ID);
				//startActivity(intent);
				ApplicationActivity mApp = (ApplicationActivity)getActivity();
				mApp.onYoutubePlaylist();
				break;
			case PLAY_PLAYLIST:
				intent = YouTubeIntents.createPlayPlaylistIntent(getActivity(), DeveloperKey.PLAYLIST_ID);
				startActivity(intent);
				break;
			case OPEN_SEARCH:
				intent = YouTubeIntents.createSearchIntent(getActivity(), DeveloperKey.USER_ID);
				startActivity(intent);
				break;
			case OPEN_USER:
				intent = YouTubeIntents.createUserIntent(getActivity(), DeveloperKey.USER_ID);
				startActivity(intent);
				break;
			case UPLOAD_VIDEO:
				// This will load a picker view in the users' gallery.
				// The upload activity is started in the function onActivityResult.
				//intent = new Intent(Intent.ACTION_PICK, null).setType("video/*");
				//intent.putExtra(DeveloperKey.EXTRA_LOCAL_ONLY, true);
				//startActivityForResult(intent, SELECT_VIDEO_REQUEST);
				YoutubeDashboardActivity mUpload = (YoutubeDashboardActivity)getActivity();
				mUpload.onYoutubeUpload();
				break;
		}
	}
	
	private enum IntentType {
		PLAY_VIDEO,
		OPEN_PLAYLIST,
		PLAY_PLAYLIST,
		OPEN_USER,
		OPEN_SEARCH,
		UPLOAD_VIDEO;
	}

	private final class IntentItem implements DemoListViewItem {

		public final String title;
		public final IntentType type;

		public IntentItem(String title, IntentType type) {
			this.title = title;
			this.type = type;
		}

		@Override
		public String getTitle() {
			return title;
		}

		@Override
		public boolean isEnabled() {
			return isIntentTypeEnabled(type);
		}

		@Override
		public String getDisabledText() {
			return getString(R.string.intent_disabled);
		}

	}
}
