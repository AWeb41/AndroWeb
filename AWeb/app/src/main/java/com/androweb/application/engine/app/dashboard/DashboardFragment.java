package com.androweb.application.engine.app.dashboard;

import com.androweb.application.R;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;


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
		view.findViewById(R.id.bankcardId2).setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		// TODO: Implement this method
	}

	
}
