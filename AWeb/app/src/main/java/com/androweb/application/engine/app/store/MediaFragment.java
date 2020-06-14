package com.androweb.application.engine.app.store;

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
import com.androweb.application.engine.app.gallery.VideoFragment;
import com.androweb.application.engine.app.dashboard.youtube.YoutubeDashboardActivity;

public class MediaFragment extends Fragment implements View.OnClickListener
{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// TODO: Implement this method
		return inflater.inflate(R.layout.fragment_application_store, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onViewCreated(view, savedInstanceState);
		setHasOptionsMenu(true);
		view.findViewById(R.id.bankcardId2).setOnClickListener(this);
	
	}

	
	@Override
	public void onClick(View v)
	{
		// TODO: Implement this method
		switch(v.getId()){
			case R.id.bankcardId2:
				ApplicationActivity mApp = (ApplicationActivity)getActivity();
				mApp.showFragment(new VideoFragment());
				break;
		}
	}
}
