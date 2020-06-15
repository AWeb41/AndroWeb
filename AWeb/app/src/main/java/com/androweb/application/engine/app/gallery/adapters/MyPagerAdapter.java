package com.androweb.application.engine.app.gallery.adapters;

import com.androweb.application.R;
import com.androweb.application.engine.Constants;
import com.androweb.application.engine.app.gallery.models.Medium;
import com.androweb.application.engine.app.gallery.activities.ViewPagerActivity;
import com.androweb.application.engine.app.gallery.fragments.ViewPagerFragment;
import com.androweb.application.engine.app.gallery.fragments.VideoFragment;
import com.androweb.application.engine.app.gallery.fragments.PhotoFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyPagerAdapter extends FragmentStatePagerAdapter {
    private final List<Medium> mMedia;
    private final Map<Integer, ViewPagerFragment> mFragments;
    private final ViewPagerActivity mActivity;

    public MyPagerAdapter(ViewPagerActivity act, FragmentManager fm, List<Medium> media) {
        super(fm);
        mActivity = act;
        mMedia = media;
        mFragments = new HashMap<>();
    }

    @Override
    public int getCount() {
        return mMedia.size();
    }

    @Override
    public Fragment getItem(int position) {
        final Medium medium = mMedia.get(position);
        final Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.MEDIUM, medium);
        ViewPagerFragment fragment;

        if (medium.getIsVideo()) {
            fragment = new VideoFragment();
        } else {
            fragment = new PhotoFragment();
        }

        mFragments.put(position, fragment);
        fragment.setArguments(bundle);
        fragment.setListener(mActivity);
        return fragment;
    }

    public void itemDragged(int pos) {
        if (mFragments.get(pos) != null) {
            mFragments.get(pos).itemDragged();
        }
    }

    public void updateUiVisibility(boolean isFullscreen, int pos) {
        for (int i = -1; i <= 1; i++) {
            final ViewPagerFragment fragment = mFragments.get(pos + i);
            if (fragment != null) {
                fragment.systemUiVisibilityChanged(isFullscreen);
            }
        }
    }

    public void updateItems(List<Medium> newPaths) {
        mMedia.clear();
        mMedia.addAll(newPaths);
        notifyDataSetChanged();
    }
}
