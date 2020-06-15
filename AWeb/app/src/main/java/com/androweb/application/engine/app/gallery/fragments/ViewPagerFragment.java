package com.androweb.application.engine.app.gallery.fragments;

import com.androweb.application.R;

import android.support.v4.app.Fragment;

public abstract class ViewPagerFragment extends Fragment {
    protected FragmentClickListener mListener;

    public void setListener(FragmentClickListener listener) {
        mListener = listener;
    }

    public abstract void itemDragged();

    public abstract void systemUiVisibilityChanged(boolean toFullscreen);

    public interface FragmentClickListener {
        void fragmentClicked();
    }
}
