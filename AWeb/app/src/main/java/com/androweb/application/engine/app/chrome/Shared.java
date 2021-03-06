package com.androweb.application.engine.app.chrome;

import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsSession;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.customtabs.CustomTabsCallback;

import com.androweb.application.R;
import com.androweb.application.engine.app.chrome.BrowserActivity;
import com.androweb.application.engine.app.chrome.CustomTabActivityHelper;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.graphics.Color;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class Shared
{
//Permission variables
	public static boolean ASWP_JSCRIPT     = true;     //enable JavaScript for webview
	public static boolean ASWP_FUPLOAD     = true;     //upload file from webview
	public static boolean ASWP_CAMUPLOAD   = true;     //enable upload from camera for photos
	public static boolean ASWP_ONLYCAM		 = false;	 //incase you want only camera files to upload
 	public static boolean ASWP_MULFILE     = false;    //upload multiple files in webview
	public static boolean ASWP_LOCATION    = true;     //track GPS locations
	public static boolean ASWP_RATINGS     = true;     //show ratings dialog; auto configured, edit method get_rating() for customizations
	public static boolean ASWP_PBAR        = true;     //show progress bar in app
	public static boolean ASWP_ZOOM        = false;    //zoom control for webpages view
	public static boolean ASWP_SFORM       = false;    //save form cache and auto-fill information
	public static boolean ASWP_OFFLINE     = false;    //whether the loading webpages are offline or online
	public static boolean ASWP_EXTURL      = true;     //open external url with default browser instead of app webview

	//Configuration variables
	public static String ASWV_URL          = "https://aweb41.github.io/XTerminal/"; //complete URL of your website or webpage
	public static String ASWV_F_TYPE       = "*/*";  //to upload any file type using "*/*"; check file type references for more

	//Rating system variables
	public static int ASWR_DAYS            = 3;        //after how many days of usage would you like to show the dialoge
	public static int ASWR_TIMES           = 10;       //overall request launch times being ignored
	public static int ASWR_INTERVAL        = 2;        //reminding users to rate after days interval
	
	public static void setLink(Activity mContext , String Url)
	{
		CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
		intentBuilder.setToolbarColor(R.color.colorPrimary);

		//Generally you do not want to decode bitmaps in the UI thread.
		String shareLabel = mContext.getString(R.string.label_action_share);
		Bitmap icon = BitmapFactory.decodeResource(mContext.getResources(),android.R.drawable.ic_menu_share);
		PendingIntent pendingIntent = createPendingIntent(mContext);
		intentBuilder.setActionButton(icon, shareLabel, pendingIntent);
		

		String menuItemTitle = mContext.getString(R.string.menu_item_title);
		PendingIntent menuItemPendingIntent = zrockGithub(mContext);
		intentBuilder.addMenuItem(menuItemTitle, menuItemPendingIntent);

		intentBuilder.setShowTitle(true);
		intentBuilder.setCloseButtonIcon(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_arrow_back));
		intentBuilder.setStartAnimations(mContext, R.anim.slide_in_right, R.anim.slide_out_left);
		intentBuilder.setExitAnimations(mContext, android.R.anim.slide_in_left, android.R.anim.slide_out_right);

		CustomTabActivityHelper.openCustomTab(mContext, intentBuilder.build(), Uri.parse(Url), new WebviewFallback());
    }

    private static PendingIntent createPendingIntent(Activity mContext)
	{
        Intent actionIntent = new Intent(mContext, ShareBroadcastReceiver.class);
        return PendingIntent.getBroadcast(mContext, 0, actionIntent, 0);
    }
	
	private static final String url = "https://github.com/AWeb41/";
	private static PendingIntent zrockGithub(Activity mContext)
	{
        Intent actionIntent = new Intent(mContext, BrowserActivity.class);
		actionIntent.putExtra("url", url);
        return PendingIntent.getActivity(mContext, 0, actionIntent, 0);
    }
}
