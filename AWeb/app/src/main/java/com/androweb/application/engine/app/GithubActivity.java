package com.androweb.application.engine.app;

import com.androweb.application.R;
import com.androweb.application.engine.app.chrome.Shared;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class GithubActivity extends AppCompatActivity 
{
	private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_engine_github);
		mToolbar = (Toolbar)findViewById(R.id.toolbar);
		setSupportActionBar(mToolbar);
		mToolbar.setTitle("Download");
			
    }
	
}
