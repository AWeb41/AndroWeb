package com.androweb.application.engine.app.profile;

import android.support.v4.app.Fragment;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.webkit.WebView;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;
import android.webkit.JavascriptInterface;

import com.androweb.application.R;
import com.androweb.application.engine.widget.AdvancedWebView;
import com.androweb.application.ApplicationActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.androweb.application.engine.app.chrome.Utility;
import android.support.design.widget.Snackbar;

public class AsepMoFragment extends Fragment implements AdvancedWebView.Listener 
{

	//private static final String TEST_PAGE_URL = "https://asepmo.github.io/AsepMo/";
	private String TEST_PAGE_URL = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=PLiKkX4KV1eFLUxsoE7fIDx5RDSC0qOdC4&duration&key=AIzaSyAYgHbHDXV1x-wSdJPqdPiwq-2GgdWEqWk&maxResults=50";
	
	private static boolean ASWP_PBAR = true;
	
	private AdvancedWebView mWebView;
	ProgressBar asw_progress;
	LinearLayout coordinatorLayout;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// TODO: Implement this method
		setHasOptionsMenu(true);
		return inflater.inflate(R.layout.fragment_profile_asepmo, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onViewCreated(view, savedInstanceState);
		if (ASWP_PBAR) {
            asw_progress = view.findViewById(R.id.msw_progress);
        } else {
            view.findViewById(R.id.msw_progress).setVisibility(View.GONE);
        }
		
		coordinatorLayout = (LinearLayout) view.findViewById(R.id.content_asepmo);
		
		mWebView = (AdvancedWebView) view.findViewById(R.id.webview);
		mWebView.setListener(getActivity(), this);
		mWebView.setGeolocationEnabled(false);
		mWebView.setMixedContentAllowed(true);
		mWebView.setCookiesEnabled(true);
		mWebView.setThirdPartyCookiesEnabled(true);
		/*mWebView.setOnKeyListener(new View.OnKeyListener(){
				@Override
				public boolean onKey(View v, int keyCode, KeyEvent event) {
					if (keyCode == KeyEvent.KEYCODE_BACK
						&& event.getAction() == MotionEvent.ACTION_UP
						&& mWebView.canGoBack()) {
						mWebView.goBack();
						return true;
					}
					return false;
				}
		});*/
		mWebView.setWebViewClient(new WebViewClient() {

				@Override
				public void onPageFinished(WebView view, String url) {
					Toast.makeText(getActivity(), "Finished loading", Toast.LENGTH_SHORT).show();
				}

			});
		mWebView.setWebChromeClient(new WebChromeClient() {


				//Getting webview rendering progress
				@Override
				public void onProgressChanged(WebView view, int p) {
					if (ASWP_PBAR) {
						asw_progress.setProgress(p);
						if (p == 100) {
							asw_progress.setProgress(0);
						}
					}
				}
				
				@Override
				public void onReceivedTitle(WebView view, String title) {
					super.onReceivedTitle(view, title);
					Toast.makeText(getActivity(), title, Toast.LENGTH_SHORT).show();
				}

			});
		mWebView.addHttpHeader("X-Requested-With", "");
		mWebView.addJavascriptInterface(new JavaScriptAction(), "JsAction");
		mWebView.loadUrl(TEST_PAGE_URL);
	}
	
	public boolean canGoBack() {
		return mWebView.canGoBack();
	}

	public void goBack() {
		mWebView.goBack();
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		// TODO: Implement this method
		inflater.inflate(R.menu.browser, menu);

        if (Utility.isBookmarked(getActivity(), mWebView.getUrl())) {
            // change icon color
            Utility.tintMenuIcon(getActivity().getApplicationContext(), menu.getItem(0), R.color.colorAccent);
        } else {
            Utility.tintMenuIcon(getActivity().getApplicationContext(), menu.getItem(0), android.R.color.white);
        }
		//super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu)
	{
		// TODO: Implement this method
		if (!mWebView.canGoBack()) {
            menu.getItem(1).setEnabled(false);
            menu.getItem(1).getIcon().setAlpha(130);
        } else {
            menu.getItem(1).setEnabled(true);
            menu.getItem(1).getIcon().setAlpha(255);
        }

        if (!mWebView.canGoForward()) {
            menu.getItem(2).setEnabled(false);
            menu.getItem(2).getIcon().setAlpha(130);
        } else {
            menu.getItem(2).setEnabled(true);
            menu.getItem(2).getIcon().setAlpha(255);
        }
		//super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// TODO: Implement this method
		if (item.getItemId() == R.id.action_bookmark) {
            // bookmark / unbookmark the url
            Utility.bookmarkUrl(getActivity(), mWebView.getUrl());

            String msg = Utility.isBookmarked(getActivity(), mWebView.getUrl()) ?
				mWebView.getTitle() + "is Bookmarked!" :
				mWebView.getTitle() + " removed!";
            Snackbar snackbar = Snackbar
				.make(coordinatorLayout, msg, Snackbar.LENGTH_LONG);
            snackbar.show();

            // refresh the toolbar icons, so that bookmark icon color changes
            // depending on bookmark status
            getActivity().invalidateOptionsMenu();
        }

        if (item.getItemId() == R.id.action_back) {
            back();
        }

        if (item.getItemId() == R.id.action_forward) {
            forward();
        }
		
		return super.onOptionsItemSelected(item);
	}
	
	// backward the browser navigation
    public void back() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        }
    }

    // forward the browser navigation
    public void forward() {
        if (mWebView.canGoForward()) {
            mWebView.goForward();
        }
    }
	
	public class JavaScriptAction {
        @JavascriptInterface
        public void action() {
            Toast.makeText(getActivity(), "Called from JavaScript", Toast.LENGTH_LONG).show();
        }
    }
	
	@SuppressLint("NewApi")
	@Override
	public void onResume()
	{
		// TODO: Implement this method
		super.onResume();
		mWebView.onResume();
	}

	@SuppressLint("NewApi")
	@Override
	public void onPause()
	{
		// TODO: Implement this method
		super.onPause();
		mWebView.onPause();
	}

	@SuppressLint("NewApi")
	@Override
	public void onDestroy()
	{
		// TODO: Implement this method
		super.onDestroy();
		mWebView.onDestroy();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		// TODO: Implement this method
		super.onActivityResult(requestCode, resultCode, data);
		mWebView.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onExternalPageRequest(String url)
	{
		// TODO: Implement this method
		Toast.makeText(getActivity(), "onExternalPageRequest(url = "+url+")", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onPageStarted(String url, Bitmap favicon)
	{
		// TODO: Implement this method
		mWebView.setVisibility(View.INVISIBLE);
	}

	@Override
	public void onPageError(int errorCode, String description, String failingUrl)
	{
		// TODO: Implement this method
	}

	@Override
	public void onPageFinished(String url)
	{
		// TODO: Implement this method
		mWebView.setVisibility(View.VISIBLE);
	}

	@Override
	public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent)
	{
		// TODO: Implement this method
		Toast.makeText(getActivity(), "onDownloadRequested(url = "+url+",  suggestedFilename = "+suggestedFilename+",  mimeType = "+mimeType+",  contentLength = "+contentLength+",  contentDisposition = "+contentDisposition+",  userAgent = "+userAgent+")", Toast.LENGTH_LONG).show();

		/*if (AdvancedWebView.handleDownload(this, url, suggestedFilename)) {
		 // download successfully handled
		 }
		 else {
		 // download couldn't be handled because user has disabled download manager app on the device
		 }*/
	}
	
	
}
