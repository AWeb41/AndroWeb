package com.androweb.application.engine.app.gallery;

import com.androweb.application.R;
import com.androweb.application.engine.app.dashboard.youtube.YoutubeDashboardActivity;

import android.support.v4.app.ListFragment;
import android.support.v7.widget.CardView;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class VideoFragment extends ListFragment
{
	SimpleCursorAdapter adapter;
	final Uri MediaBaseUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
	final String ColumnName_ID = MediaStore.Video.Media._ID;
	final String ColumnName_TITLE = MediaStore.Video.Media.TITLE;
	final String ColumnName_DURATION = MediaStore.Video.Media.DURATION;

	@Override
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		// TODO: Implement this method
		super.onListItemClick(l, v, position, id);
		Cursor cursor = adapter.getCursor();
		cursor.moveToPosition(position);

		String _id = cursor.getString(cursor.getColumnIndex(ColumnName_ID));
		
		int intID = cursor.getInt(cursor.getColumnIndexOrThrow(ColumnName_ID));  

		Uri playableUri = Uri.withAppendedPath(MediaBaseUri, _id);

    }

	@Override
	public void onStart()
	{
		// TODO: Implement this method
		super.onStart();
		String[] from = {
			MediaStore.MediaColumns.TITLE	
		};
        int[] to = {
			android.R.id.text1};

        Cursor cursor = getActivity().managedQuery(
			MediaBaseUri, 
			null, 
			null, 
			null, 
			ColumnName_TITLE);

        adapter = new MyCursorAdapter(getActivity(),
									  R.layout.cm_yt_list_items, cursor, from, to);
        setListAdapter(adapter);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// TODO: Implement this method
		setHasOptionsMenu(true);
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		// TODO: Implement this method
		super.onCreateOptionsMenu(menu, inflater);
		menu.add("Youtube")
			.setIcon(R.drawable.ic_youtube_player)
			.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
				@Override
				public boolean onMenuItemClick(MenuItem item)
				{

		            //showFragment(new YoutubePlaylistFragment());
					YoutubeDashboardActivity.start(getActivity());
					return false;
				}
			}).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

	}
	
	public class MyCursorAdapter extends SimpleCursorAdapter{
		public MyCursorAdapter(Context context, int layout, Cursor c,
							   String[] from, int[] to) {
			super(context, layout, c, from, to);
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			LayoutInflater inflater=getActivity().getLayoutInflater();
			View row = inflater.inflate(R.layout.cm_yt_list_items, parent, false);
			
			
			ImageView icon = (ImageView)row.findViewById(R.id.video_thumbnail_image_view);
            row.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						

					}
				});

			
            CardView cardView = (CardView) row.findViewById(R.id.card_view);
			cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            cardView.setRadius(8);


            TextView title = (TextView) row.findViewById(R.id.video_title_label);
            title.setTextColor(Color.BLACK);
            title.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {

					}
				});

			TextView published = (TextView) row.findViewById(R.id.video_duration_label);       
			published.setTextColor(Color.GRAY);
			
			Cursor cursor = adapter.getCursor();
			cursor.moveToPosition(position);

			//Title
			String titleVideo = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));
			title.setText(titleVideo);
			//Duration
			//String durationVideo = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
			published.setText("PreView");
			//Thumbnail
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inDither = false;
			options.inPreferredConfig = Bitmap.Config.ARGB_8888;
			Bitmap bitmapThumb = MediaStore.Video.Thumbnails.getThumbnail(getActivity().getContentResolver(),
				cursor.getInt(cursor.getColumnIndexOrThrow(ColumnName_ID)),
				Images.Thumbnails.MICRO_KIND,
				options);

			icon.setImageBitmap(bitmapThumb);

			return row;
		}
	}
}
