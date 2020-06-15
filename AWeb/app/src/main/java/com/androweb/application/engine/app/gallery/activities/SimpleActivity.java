package com.androweb.application.engine.app.gallery.activities;

import com.androweb.application.R;
import com.androweb.application.engine.Engine.Config;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class SimpleActivity extends AppCompatActivity {
    protected Config mConfig;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mConfig = Config.newInstance(getApplicationContext());
        int theme = mConfig.getIsDarkTheme() ? R.style.AppTheme_Dark : R.style.AppTheme;
        if (this instanceof ViewPagerActivity || this instanceof PhotoActivity || this instanceof VideoActivity) {
            theme = mConfig.getIsDarkTheme() ? R.style.FullScreenTheme_Dark : R.style.FullScreenTheme;
        }
        setTheme(theme);
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
