package com.androweb.application.engine.app.chrome;

import com.androweb.application.R;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.view.MenuItem;
import android.view.View;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.List;


/**
 * Created by Ravi Tamada on 28/05/16.
 * www.androidhive.info
 */
public class Utility {

    public static boolean isSameDomain(String url, String url1) {
        return getRootDomainUrl(url.toLowerCase()).equals(getRootDomainUrl(url1.toLowerCase()));
    }

    private static String getRootDomainUrl(String url) {
        String[] domainKeys = url.split("/")[2].split("\\.");
        int length = domainKeys.length;
        int dummy = domainKeys[0].equals("www") ? 1 : 0;
        if (length - dummy == 2)
            return domainKeys[length - 2] + "." + domainKeys[length - 1];
        else {
            if (domainKeys[length - 1].length() == 2) {
                return domainKeys[length - 3] + "." + domainKeys[length - 2] + "." + domainKeys[length - 1];
            } else {
                return domainKeys[length - 2] + "." + domainKeys[length - 1];
            }
        }
    }

    public static void tintMenuIcon(Context context, MenuItem item, int color) {
        Drawable drawable = item.getIcon();
        if (drawable != null) {
            // If we don't mutate the drawable, then all drawable's with this id will have a color
            // filter applied to it.
            drawable.mutate();
            drawable.setColorFilter(ContextCompat.getColor(context, color), PorterDuff.Mode.SRC_ATOP);
        }
    }

    public static void bookmarkUrl(Context context, String url) {
        SharedPreferences pref = context.getSharedPreferences("androidhive", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();

        // if url is already bookmarked, unbookmark it
        if (pref.getBoolean(url, false)) {
            editor.putBoolean(url, false);
        } else {
            editor.putBoolean(url, true);
        }

        editor.commit();
    }

    public static boolean isBookmarked(Context context, String url) {
        SharedPreferences pref = context.getSharedPreferences("androidhive", 0);
        return pref.getBoolean(url, false);
    }
	
	private static boolean underHoneyComb() {
		return false;
	}

	private static boolean isLollipop() {
		return Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP || Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP_MR1;
	}

	private static int getDialogTheme() {
		return isLollipop() ? R.style.CustomLollipopDialogStyle : 0;
	}

	@SuppressLint("NewApi")
	static AlertDialog.Builder getDialogBuilder(Context context) {
		if (underHoneyComb()) {
			return new AlertDialog.Builder(context);
		} else {
			return new AlertDialog.Builder(context, getDialogTheme());
		}
	}

	
}
