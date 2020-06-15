package com.androweb.application.engine.app.gallery.activities;

import com.androweb.application.R;
import com.androweb.application.engine.Engine.Config;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;

public class AboutActivity extends SimpleActivity {
    private TextView mCopyright;
    private TextView mEmailTV;
    private View mRateUs,mInvite,mFacebook,mGPlus;

    private static Resources mRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        
		mCopyright = (TextView)findViewById(R.id.about_copyright);
		mEmailTV = (TextView)findViewById(R.id.about_email);
		mRateUs = findViewById(R.id.about_rate_us);
		mInvite = findViewById(R.id.about_invite);
		mFacebook = findViewById(R.id.about_facebook);
		mGPlus = findViewById(R.id.about_gplus);
		
        mRes = getResources();

        setupEmail();
        setupCopyright();
        setupRateUs();
    }

    private void setupEmail() {
        final String email = mRes.getString(R.string.email);
        final String appName = mRes.getString(R.string.app_name);
        final String href = "<a href=\"mailto:" + email + "?subject=" + appName + "\">" + email + "</a>";
        mEmailTV.setText(Html.fromHtml(href));
        mEmailTV.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void setupCopyright() {
        final String versionName = "com.simplemobiletools.gallery";
        final int year = Calendar.getInstance().get(Calendar.YEAR);
        final String copyrightText = String.format(mRes.getString(R.string.copyright), versionName, year);
        mCopyright.setText(copyrightText);
    }

    private void setupRateUs() {
        if (Config.newInstance(getApplicationContext()).getIsFirstRun()) {
            mRateUs.setVisibility(View.GONE);
        }
    }

  
    public void inviteFriend() {
        final Intent intent = new Intent();
        final String text = String.format(getString(R.string.share_text), getString(R.string.app_name), getStoreUrl());
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.setType("text/plain");
        startActivity(Intent.createChooser(intent, getString(R.string.invite_via)));
    }

    
    public void rateUsClicked() {
        final Uri uri = Uri.parse("market://details?id=" + getPackageName());
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, uri));
        } catch (ActivityNotFoundException ignored) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getStoreUrl())));
        }
    }

    
    public void licenseClicked() {
        final Intent intent = new Intent(getApplicationContext(), LicenseActivity.class);
        startActivity(intent);
    }

   
    public void facebookClicked() {
        String link = "https://www.facebook.com/simplemobiletools";
        try {
            getPackageManager().getPackageInfo("com.facebook.katana", 0);
            link = "fb://page/150270895341774";
        } catch (Exception ignored) {
        }
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
    }

   
    public void googlePlusClicked() {
        final String link = "https://plus.google.com/communities/104880861558693868382";
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
    }

    private String getStoreUrl() {
        return "https://play.google.com/store/apps/details?id=" + getPackageName();
    }
}
