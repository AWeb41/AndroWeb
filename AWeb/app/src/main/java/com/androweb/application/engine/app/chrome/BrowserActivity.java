package com.androweb.application.engine.app.chrome;

import com.androweb.application.R;
import com.androweb.application.engine.widget.AdvancedWebView;

import android.annotation.SuppressLint;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;
import android.webkit.JavascriptInterface;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

public class BrowserActivity extends AppCompatActivity implements AdvancedWebView.Listener 
{
    // private String TAG = BrowserActivity.class.getSimpleName();
    private String url;
    private AdvancedWebView webView;
    private ProgressBar progressBar;
    private float m_downX;
    LinearLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_browser);
		
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        url = getIntent().getStringExtra("url");

        // if no url is passed, close the activity
        if (TextUtils.isEmpty(url)) {
            finish();
        }

        webView = (AdvancedWebView) findViewById(R.id.webView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        coordinatorLayout = (LinearLayout) findViewById(R.id.main_content);

        initWebView();

        webView.loadUrl(url);
    }

    private void initWebView() {
		webView.setListener(this, this);
		webView.setGeolocationEnabled(false);
		webView.setMixedContentAllowed(true);
		webView.setCookiesEnabled(true);
		webView.setThirdPartyCookiesEnabled(true);
        webView.setWebChromeClient(new MyWebChromeClient(this));
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
                invalidateOptionsMenu();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
                invalidateOptionsMenu();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                progressBar.setVisibility(View.GONE);
                invalidateOptionsMenu();
            }
        });
		
        webView.addHttpHeader("X-Requested-With", "");
		webView.addJavascriptInterface(new JavaScriptAction(), "JsAction");
		webView.setHorizontalScrollBarEnabled(false);
        webView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getPointerCount() > 1) {
                    //Multi touch detected
                    return true;
                }

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        // save the x
                        m_downX = event.getX();
                    }
                    break;

                    case MotionEvent.ACTION_MOVE:
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP: {
                        // set x so that it doesn't move
                        event.setLocation(m_downX, event.getY());
                    }
                    break;
                }

                return false;
            }
        });
    }
	@Override
	public void onPageStarted(String url, Bitmap favicon)
	{
		// TODO: Implement this method
		webView.setVisibility(View.INVISIBLE);
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
		webView.setVisibility(View.VISIBLE);
	}

	@Override
	public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent)
	{
		// TODO: Implement this method
		Toast.makeText(BrowserActivity.this, "onDownloadRequested(url = "+url+",  suggestedFilename = "+suggestedFilename+",  mimeType = "+mimeType+",  contentLength = "+contentLength+",  contentDisposition = "+contentDisposition+",  userAgent = "+userAgent+")", Toast.LENGTH_LONG).show();

		/*if (AdvancedWebView.handleDownload(this, url, suggestedFilename)) {
		 // download successfully handled
		 }
		 else {
		 // download couldn't be handled because user has disabled download manager app on the device
		 }*/
	}

	@Override
	public void onExternalPageRequest(String url)
	{
		// TODO: Implement this method
		Toast.makeText(this, "onExternalPageRequest(url = "+url+")", Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		// TODO: Implement this method
		super.onActivityResult(requestCode, resultCode, data);
		webView.onActivityResult(requestCode, resultCode, data);
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.browser, menu);

        if (Utility.isBookmarked(this, webView.getUrl())) {
            // change icon color
            Utility.tintMenuIcon(getApplicationContext(), menu.getItem(0), R.color.colorAccent);
        } else {
            Utility.tintMenuIcon(getApplicationContext(), menu.getItem(0), android.R.color.white);
        }
        return true;
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        // menu item 0-index is bookmark icon

        // enable - disable the toolbar navigation icons
        if (!webView.canGoBack()) {
            menu.getItem(1).setEnabled(false);
            menu.getItem(1).getIcon().setAlpha(130);
        } else {
            menu.getItem(1).setEnabled(true);
            menu.getItem(1).getIcon().setAlpha(255);
        }

        if (!webView.canGoForward()) {
            menu.getItem(2).setEnabled(false);
            menu.getItem(2).getIcon().setAlpha(130);
        } else {
            menu.getItem(2).setEnabled(true);
            menu.getItem(2).getIcon().setAlpha(255);
        }

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        if (item.getItemId() == R.id.action_bookmark) {
            // bookmark / unbookmark the url
            Utility.bookmarkUrl(this, webView.getUrl());

            String msg = Utility.isBookmarked(this, webView.getUrl()) ?
                    webView.getTitle() + "is Bookmarked!" :
                    webView.getTitle() + " removed!";
            Snackbar snackbar = Snackbar.make(coordinatorLayout, msg, Snackbar.LENGTH_LONG);
            snackbar.show();

            // refresh the toolbar icons, so that bookmark icon color changes
            // depending on bookmark status
            invalidateOptionsMenu();
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
    private void back() {
        if (webView.canGoBack()) {
            webView.goBack();
        }
    }

    // forward the browser navigation
    private void forward() {
        if (webView.canGoForward()) {
            webView.goForward();
        }
    }

	@SuppressLint("NewApi")
	@Override
	protected void onResume()
	{
		// TODO: Implement this method
		super.onResume();
		webView.onResume();
	}

	@SuppressLint("NewApi")
	@Override
	protected void onPause()
	{
		// TODO: Implement this method
		super.onPause();
		webView.onPause();
	}

	@SuppressLint("NewApi")
	@Override
	protected void onDestroy()
	{
		// TODO: Implement this method
		super.onDestroy();
		webView.onDestroy();
	}
	
	@Override
	public void onBackPressed()
	{
		// TODO: Implement this method
		if (webView.canGoBack()) {
            webView.goBack();
        }else{
		    super.onBackPressed();
		}
	}

	private boolean ASWP_PBAR = true;
    private class MyWebChromeClient extends WebChromeClient {
        Context context;

        public MyWebChromeClient(Context context) {
            super();
            this.context = context;
        }
		
		//Getting webview rendering progress
		@Override
		public void onProgressChanged(WebView view, int p) {
			if (ASWP_PBAR) {
				progressBar.setProgress(p);
				if (p == 100) {
					progressBar.setProgress(0);
				}
			}
		}

		@Override
		public void onReceivedTitle(WebView view, String title)
		{
			// TODO: Implement this method
			super.onReceivedTitle(view, title);
			Snackbar snackbar = Snackbar.make(coordinatorLayout, title, Snackbar.LENGTH_LONG);
            snackbar.show();
		}
    }
	
	public class JavaScriptAction {
		
        @JavascriptInterface
        public void action() {
            //Toast.makeText(BrowserActivity.this, "Called from JavaScript", Toast.LENGTH_LONG).show();
			Snackbar snackbar = Snackbar.make(coordinatorLayout, "AndroWeb", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
		
		@JavascriptInterface
        public void xterminal() {
			int SPLASH_TIME_OUT = 2000;
			new android.os.Handler().postDelayed(new Runnable() {

					/*
					 * Showing splash screen with a timer. This will be useful when you
					 * want to show case your app logo / company
					 */

					@Override
					public void run() {
						// This method will be executed once the timer is over
						// Start your app main activity
						Intent i = new Intent(BrowserActivity.this, AWebActivity.class);
						startActivity(i);

						// close this activity
						finish();
					}
				}, SPLASH_TIME_OUT);
        }
    }
}
