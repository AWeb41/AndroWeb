package com.androweb.application.engine.app.media;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;

import com.androweb.application.R;
import com.androweb.application.ApplicationActivity;
import com.androweb.application.engine.app.dashboard.youtube.YoutubeDashboardActivity;

public class MediaFragment extends Fragment implements View.OnClickListener
{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// TODO: Implement this method
		return inflater.inflate(R.layout.fragment_application_media, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onViewCreated(view, savedInstanceState);
		setHasOptionsMenu(true);
		view.findViewById(R.id.bankcardId).setOnClickListener(this);
		view.findViewById(R.id.bankcardId1).setOnClickListener(this);
		view.findViewById(R.id.bankcardId2).setOnClickListener(this);
		view.findViewById(R.id.bankcardId3).setOnClickListener(this);
		
	}

	
	@Override
	public void onClick(View v)
	{
		// TODO: Implement this method
		switch(v.getId()){
			case R.id.bankcardId:
				ApplicationActivity mPhoto = (ApplicationActivity)getActivity();
				mPhoto.showFragment(new PhotoFragment());
				break;
			case R.id.bankcardId1:
				ApplicationActivity mAudio = (ApplicationActivity)getActivity();
				mAudio.showFragment(new AudioFragment());
				break;
			case R.id.bankcardId2:
				ApplicationActivity mVideo = (ApplicationActivity)getActivity();
				mVideo.showFragment(new VideoFragment());
				break;
			case R.id.bankcardId3:
				ApplicationActivity mEbook = (ApplicationActivity)getActivity();
				mEbook.showFragment(new EbookFragment());
				break;	
		}
	}
}
