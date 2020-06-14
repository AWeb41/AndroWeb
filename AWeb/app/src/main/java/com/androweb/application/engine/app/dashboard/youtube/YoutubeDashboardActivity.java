package com.androweb.application.engine.app.dashboard.youtube;

import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;
import com.google.android.youtube.player.YouTubeIntents;


import com.androweb.application.R;
import com.androweb.application.engine.app.dashboard.youtube.adapter.DemoArrayAdapter;
import com.androweb.application.engine.app.dashboard.youtube.adapter.DemoListViewItem;
import com.androweb.application.engine.view.menu.DrawerAdapter;
import com.androweb.application.engine.view.menu.DrawerItem;
import com.androweb.application.engine.view.menu.SimpleItem;
import com.androweb.application.engine.view.menu.SpaceItem;
import com.androweb.application.engine.app.profile.AsepMoFragment;

public class YoutubeDashboardActivity extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener
{

	private Toolbar mToolbar;
	private static final int POS_VIDEOME = 0;
    private static final int POS_PLAYLIST = 1;
    private static final int POS_USER = 2;
    private static final int POS_SEARCH = 3;
    private static final int POS_UPLOAD = 4;
	private static final int POS_DOWNLOAD = 5;
	private static final int POS_HOME = 7;

	private String[] screenTitles;
    private Drawable[] screenIcons;
	ProgressBar asw_progress;
    private SlidingRootNav slidingRootNav;
	public static void start(Context c)
	{
		Intent intent = new Intent(c, YoutubeDashboardActivity.class);
        c.startActivity(intent);
	}

	private static final int SELECT_VIDEO_REQUEST = 1000;
	public void onYoutubeUpload()
	{
		Intent intent = new Intent(Intent.ACTION_PICK, null).setType("video/*");
		intent.putExtra(DeveloperKey.EXTRA_LOCAL_ONLY, true);
		startActivityForResult(intent, SELECT_VIDEO_REQUEST);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		// TODO: Implement this method
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK)
		{
			switch (requestCode)
			{
				case SELECT_VIDEO_REQUEST:
					if (YouTubeIntents.canResolveUserIntent(this))
					{
						Intent intent = YouTubeIntents.createUploadIntent(this, data.getData());
						startActivity(intent);
					}
					break;
			}
		}
	}

	@Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_dashboard_youtube);

        mToolbar = (Toolbar)findViewById(R.id.toolbar);
		setSupportActionBar(mToolbar);

        slidingRootNav = new SlidingRootNavBuilder(this)
			.withToolbarMenuToggle(mToolbar)
			.withMenuOpened(false)
			.withContentClickableWhenMenuOpened(false)
			.withSavedState(savedInstanceState)
			.withMenuLayout(R.layout.menu_dashboard_youtube)
			.inject();

        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();

        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(
													  createItemFor(POS_VIDEOME).setChecked(true),
													  createItemFor(POS_PLAYLIST),
													  createItemFor(POS_USER),
													  createItemFor(POS_SEARCH),
													  createItemFor(POS_UPLOAD),
													  createItemFor(POS_DOWNLOAD),
													  new SpaceItem(48),
													  createItemFor(POS_HOME)));
        adapter.setListener(this);

        RecyclerView list = findViewById(R.id.list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        adapter.setSelected(POS_VIDEOME);
    }

    @Override
    public void onItemSelected(int position)
	{
		if (position == POS_VIDEOME)
		{
			mToolbar.setTitle("Channel Me");
			Fragment mVideo = AsepMoFragment.createFor(DeveloperKey.CHANNEL_YOUTUBE_URL);
			showFragment(mVideo);
		}
		if (position == POS_PLAYLIST)
		{
			mToolbar.setTitle("Playlist");
			showFragment(new YoutubePlaylistFragment());
		}
		if (position == POS_USER)
		{
			mToolbar.setTitle("User");
			//showFragment(new ProfileFragment());
			if (YouTubeIntents.canResolveUserIntent(this))
			{
				Intent intent = YouTubeIntents.createUserIntent(this, DeveloperKey.USER_ID);
				startActivity(intent);
			}
		}
		if (position == POS_SEARCH)
		{
			mToolbar.setTitle("Search");
			if (YouTubeIntents.canResolveUserIntent(this))
			{
				Intent intent = YouTubeIntents.createSearchIntent(this, DeveloperKey.USER_ID);
				startActivity(intent);
			}
		}
		if (position == POS_UPLOAD)
		{
			mToolbar.setTitle("Upload");
			onYoutubeUpload();
		}
		if (position == POS_DOWNLOAD)
		{
			mToolbar.setTitle("Download");
			Fragment mVideo = AsepMoFragment.createFor(DeveloperKey.Y2MATE_PAGE_URL);
			showFragment(mVideo);
		}
        if (position == POS_HOME)
		{
			mToolbar.setTitle("Back To Home");
			new android.os.Handler().postDelayed(new Runnable(){
					@Override
					public void run()
					{
						YoutubeDashboardActivity.this.finish();
					}
				}, 1200);
        }
        slidingRootNav.closeMenu();
        //Fragment selectedScreen = CenteredTextFragment.createFor(screenTitles[position]);
        //showFragment(selectedScreen);
    }

    public void showFragment(Fragment fragment)
	{
        getSupportFragmentManager().beginTransaction()
			.replace(R.id.container, fragment)
			.commit();
    }

    private DrawerItem createItemFor(int position)
	{
        return new SimpleItem(screenIcons[position], screenTitles[position])
			.withIconTint(color(R.color.textColorSecondary))
			.withTextTint(color(R.color.textColorPrimary))
			.withSelectedIconTint(color(R.color.colorAccent))
			.withSelectedTextTint(color(R.color.colorAccent));
    }

    private String[] loadScreenTitles()
	{
        return getResources().getStringArray(R.array.ld_youtubeDashboardTitles);
    }

    private Drawable[] loadScreenIcons()
	{
        TypedArray ta = getResources().obtainTypedArray(R.array.ld_youtubeDashboardIcons);
        Drawable[] icons = new Drawable[ta.length()];
        for (int i = 0; i < ta.length(); i++)
		{
            int id = ta.getResourceId(i, 0);
            if (id != 0)
			{
                icons[i] = ContextCompat.getDrawable(this, id);
            }
        }
        ta.recycle();
        return icons;
    }

    @ColorInt
    private int color(@ColorRes int res)
	{
        return ContextCompat.getColor(this, res);
    }

	public boolean isIntentTypeEnabled(IntentType type)
	{
		switch (type)
		{
			case PLAY_VIDEO:
				return YouTubeIntents.canResolvePlayVideoIntent(this);
			case OPEN_PLAYLIST:
				return YouTubeIntents.canResolveOpenPlaylistIntent(this);
			case PLAY_PLAYLIST:
				return YouTubeIntents.canResolvePlayPlaylistIntent(this);
			case OPEN_SEARCH:
				return YouTubeIntents.canResolveSearchIntent(this);
			case OPEN_USER:
				return YouTubeIntents.canResolveUserIntent(this);
			case UPLOAD_VIDEO:
				return YouTubeIntents.canResolveUploadIntent(this);
		}

		return false;
	}

	public boolean isEnabled(IntentType type)
	{
		return isIntentTypeEnabled(type);
	}

	private enum IntentType
	{
		PLAY_VIDEO,
		OPEN_PLAYLIST,
		PLAY_PLAYLIST,
		OPEN_USER,
		OPEN_SEARCH,
		UPLOAD_VIDEO;
	}

	private final class IntentItem implements DemoListViewItem
	{

		public final String title;
		public final IntentType type;

		public IntentItem(String title, IntentType type)
		{
			this.title = title;
			this.type = type;
		}

		@Override
		public String getTitle()
		{
			return title;
		}

		@Override
		public boolean isEnabled()
		{
			return isIntentTypeEnabled(type);
		}

		@Override
		public String getDisabledText()
		{
			return getString(R.string.intent_disabled);
		}

	}
}
