package com.androweb.application.engine.app.gallery.activities;

import com.androweb.application.R;

import android.os.Bundle;

public class PhotoActivity extends PhotoVideoActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mIsVideo = false;
        super.onCreate(savedInstanceState);
    }
}
