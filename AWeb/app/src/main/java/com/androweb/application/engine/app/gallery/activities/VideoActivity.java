package com.androweb.application.engine.app.gallery.activities;

import com.androweb.application.R;

import android.os.Bundle;

public class VideoActivity extends PhotoVideoActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mIsVideo = true;
        super.onCreate(savedInstanceState);
    }
}
