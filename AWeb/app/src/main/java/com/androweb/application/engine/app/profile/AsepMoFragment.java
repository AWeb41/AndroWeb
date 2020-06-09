package com.androweb.application.engine.app.profile;

import android.support.v4.app.Fragment;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.Toast;
import android.webkit.WebView;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;

import com.androweb.application.R;
import com.androweb.application.engine.widget.AdvancedWebView;

public class AsepMoFragment extends Fragment implements AdvancedWebView.Listener 
{

	private static final String TEST_PAGE_URL = "https://aweb41.github.io/AWeb/";
	private AdvancedWebView mWebView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// TODO: Implement this method
		return inflater.inflate(R.layout.fragment_profile_asepmo, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onViewCreated(view, savedInstanceState);
		mWebView = (AdvancedWebView) view.findViewById(R.id.webview);
		mWebView.setListener(getActivity(), this);
		mWebView.setGeolocationEnabled(false);
		mWebView.setMixedContentAllowed(true);
		mWebView.setCookiesEnabled(true);
		mWebView.setThirdPartyCookiesEnabled(true);
		mWebView.setWebViewClient(new WebViewClient() {

				@Override
				public void onPageFinished(WebView view, String url) {
					Toast.makeText(getActivity(), "Finished loading", Toast.LENGTH_SHORT).show();
				}

			});
		mWebView.setWebChromeClient(new WebChromeClient() {

				@Override
				public void onReceivedTitle(WebView view, String title) {
					super.onReceivedTitle(view, title);
					Toast.makeText(getActivity(), title, Toast.LENGTH_SHORT).show();
				}

			});
		mWebView.addHttpHeader("X-Requested-With", "");
		mWebView.loadUrl(TEST_PAGE_URL);
	}

	public void onBack()
	{
		if (!mWebView.onBackPressed()) { return; }
		// ...
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
