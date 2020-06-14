package com.androweb.application;

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

import java.util.Arrays;


import com.androweb.application.engine.app.chrome.ChromeActivity;
import com.androweb.application.engine.app.chrome.AWebActivity;
import com.androweb.application.engine.app.dashboard.DashboardFragment;
import com.androweb.application.engine.app.profile.ProfileFragment;
import com.androweb.application.engine.app.profile.AsepMoFragment;
import com.androweb.application.engine.app.message.MessageFragment;
import com.androweb.application.engine.app.store.StoreFragment;
import com.androweb.application.engine.app.gallery.GalleryActivity;
import com.androweb.application.engine.app.chrome.Shared;
import com.androweb.application.engine.view.menu.DrawerAdapter;
import com.androweb.application.engine.view.menu.DrawerItem;
import com.androweb.application.engine.view.menu.SimpleItem;
import com.androweb.application.engine.view.menu.SpaceItem;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;
import com.androweb.application.engine.app.chrome.BrowserActivity;
import com.androweb.application.engine.app.chrome.Utility;
import android.widget.Toast;
import com.androweb.application.engine.app.dashboard.CM_youtubePlaylist;


public class ApplicationActivity extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener
{

    private static final int POS_DASHBOARD = 0;
    private static final int POS_ACCOUNT = 1;
    private static final int POS_MESSAGES = 2;
    private static final int POS_CART = 3;
    private static final int POS_LOGOUT = 5;
	static boolean ASWP_PBAR = true;

    private String[] screenTitles;
    private Drawable[] screenIcons;
	ProgressBar asw_progress;
    private SlidingRootNav slidingRootNav;
	public static void start(Context c)
	{
		Intent intent = new Intent(c, ApplicationActivity.class);
        c.startActivity(intent);
	}

	public static void github(Activity mContext)
	{
		String urlGithub = "https://aweb41.github.io/AsepMo/";
		Shared.setLink(mContext , urlGithub);
	}

	String urlOpen = "https://aweb41.github.io/AsepMo/";
	public static void openInAppBrowser(Context c, String url)
	{
        Intent intent = new Intent(c, BrowserActivity.class);
        intent.putExtra("url", url);
        c.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        slidingRootNav = new SlidingRootNavBuilder(this)
			.withToolbarMenuToggle(toolbar)
			.withMenuOpened(false)
			.withContentClickableWhenMenuOpened(false)
			.withSavedState(savedInstanceState)
			.withMenuLayout(R.layout.menu_left_drawer)
			.inject();

        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();

        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(
													  createItemFor(POS_DASHBOARD).setChecked(true),
													  createItemFor(POS_ACCOUNT),
													  createItemFor(POS_MESSAGES),
													  createItemFor(POS_CART),
													  new SpaceItem(48),
													  createItemFor(POS_LOGOUT)));
        adapter.setListener(this);

        RecyclerView list = findViewById(R.id.list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        adapter.setSelected(POS_DASHBOARD);
    }

    @Override
    public void onItemSelected(int position)
	{
		if (position == POS_DASHBOARD)
		{
			showFragment(new AsepMoFragment());
		}
		if (position == POS_ACCOUNT)
		{
			//showFragment(new ProfileFragment());
			openInAppBrowser(ApplicationActivity.this, urlOpen);
		}
		if (position == POS_MESSAGES)
		{
			showFragment(new MessageFragment());
		}
		if (position == POS_CART)
		{
			showFragment(new StoreFragment());
		}
        if (position == POS_LOGOUT)
		{
            finish();
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
        return getResources().getStringArray(R.array.ld_activityScreenTitles);
    }

    private Drawable[] loadScreenIcons()
	{
        TypedArray ta = getResources().obtainTypedArray(R.array.ld_activityScreenIcons);
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

	public AsepMoFragment getFolderFragment()
	{
		Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
		if (fragment instanceof AsepMoFragment)
			return (AsepMoFragment) fragment;
		else return null;

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// TODO: Implement this method
		menu.add("Info")
			.setIcon(R.drawable.ic_youtube_player)
			.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
				@Override
				public boolean onMenuItemClick(MenuItem item)
				{
					
		            showFragment(new CM_youtubePlaylist());
					return false;
				}
			}).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// TODO: Implement this method

		return super.onOptionsItemSelected(item);
	}


	@Override
	public void onBackPressed()
	{

		// TODO: Implement this method
		if (slidingRootNav.isMenuOpened())
		{
			slidingRootNav.closeMenu();
		}
		canGoBack();
	}
	
	public void canGoBack()
	{
		AsepMoFragment fragment = (AsepMoFragment)getSupportFragmentManager().findFragmentById(R.id.container);
		if (fragment.canGoBack())
		{
			fragment.goBack();
		}
	}


}
