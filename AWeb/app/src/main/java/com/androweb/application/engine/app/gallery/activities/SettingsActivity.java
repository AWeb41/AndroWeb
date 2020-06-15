package com.androweb.application.engine.app.gallery.activities;

import com.androweb.application.R;
import com.androweb.application.engine.Engine.Config;

import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;

public class SettingsActivity extends SimpleActivity {
    private SwitchCompat mDarkThemeSwitch;
    private SwitchCompat mSameSortingSwitch;

    private static Config mConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
		
        mConfig = Config.newInstance(getApplicationContext());
        mDarkThemeSwitch = (SwitchCompat)findViewById(R.id.settings_dark_theme);
		mDarkThemeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
				@Override
				public void onCheckedChanged(CompoundButton button, boolean isChecked){
					if(isChecked){
						handleDarkTheme();
					}else{
						mDarkThemeSwitch.setChecked(false);
						mConfig.setIsDarkTheme(false);	
						restartActivity();
					}
				}
			});
		mSameSortingSwitch = (SwitchCompat)findViewById(R.id.settings_same_sorting);
		mSameSortingSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton button, boolean isChecked){
				if(isChecked){
					handleSameSorting();
				}else{
					mSameSortingSwitch.setChecked(false);
					mConfig.setIsSameSorting(false);			
				}
			}
		});
		//setupDarkTheme();
        //setupSameSorting();
    }

    private void setupDarkTheme() {
        mDarkThemeSwitch.setChecked(mConfig.getIsDarkTheme());
    }

    private void setupSameSorting() {
        mSameSortingSwitch.setChecked(mConfig.getIsSameSorting());
    }

    public void handleDarkTheme() {
        mDarkThemeSwitch.setChecked(!mDarkThemeSwitch.isChecked());
        mConfig.setIsDarkTheme(mDarkThemeSwitch.isChecked());
        restartActivity();
    }

    public void handleSameSorting() {
        mSameSortingSwitch.setChecked(!mSameSortingSwitch.isChecked());
        mConfig.setIsSameSorting(mSameSortingSwitch.isChecked());
    }

    private void restartActivity() {
        TaskStackBuilder.create(getApplicationContext()).addNextIntentWithParentStack(getIntent()).startActivities();
    }
}
