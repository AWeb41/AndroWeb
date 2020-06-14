package com.androweb.application.engine.app.dashboard;

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
import com.androweb.application.engine.app.profile.AsepMoFragment;
import com.androweb.application.engine.app.gallery.VideoFragment;
import com.androweb.application.engine.app.dashboard.youtube.YoutubeDashboardActivity;
import com.androweb.application.engine.app.dashboard.youtube.DeveloperKey;

public class DashboardFragment extends Fragment implements View.OnClickListener
{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// TODO: Implement this method
		return inflater.inflate(R.layout.fragment_application_dashboard, container, false);
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
		
	}

	@Override
	public void onClick(View v)
	{
		// TODO: Implement this method
		switch(v.getId()){
			case R.id.bankcardId:
				ApplicationActivity mAWeb = (ApplicationActivity)getActivity();
				mAWeb.openInAppBrowser(getActivity(),DeveloperKey.AWEB_PAGE_URL);
				break;
			case R.id.bankcardId1:
				ApplicationActivity mAsepMo = (ApplicationActivity)getActivity();
				mAsepMo.openInAppBrowser(getActivity(),DeveloperKey.ASEPMO_PAGE_URL);
				break;
			case R.id.bankcardId2:
				ApplicationActivity mApp = (ApplicationActivity)getActivity();
				mApp.openInAppBrowser(getActivity(), DeveloperKey.ASEPMO_PAGE_URL);
				break;
		}
	}
}
