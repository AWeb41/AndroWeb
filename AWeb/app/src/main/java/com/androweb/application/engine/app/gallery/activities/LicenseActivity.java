package com.androweb.application.engine.app.gallery.activities;

import com.androweb.application.R;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class LicenseActivity extends SimpleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);
        findViewById(R.id.license_butterknife_title);
		findViewById(R.id.license_photoview_title);
    }

   
	public void butterKnifeClicked() {
        openUrl(R.string.butterknife_url);
    }

    public void photoViewClicked() {
        openUrl(R.string.photoview_url);
    }

    private void openUrl(int id) {
        final String url = getResources().getString(id);
        final Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }
}
