package com.androweb.application.engine.app.chrome;

import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsSession;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.customtabs.CustomTabsCallback;

import com.androweb.application.R;
import com.androweb.application.engine.app.GithubActivity;
import com.androweb.application.engine.app.chrome.CustomTabActivityHelper;
import com.androweb.application.engine.app.chrome.shared.*;

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
	
	private static PendingIntent zrockGithub(Activity mContext)
	{
        Intent actionIntent = new Intent(mContext, GithubActivity.class);
        return PendingIntent.getActivity(mContext, 0, actionIntent, 0);
    }
}
