package com.androweb.application.engine.app.chrome;

import com.androweb.application.R;
import com.androweb.application.engine.widget.AdvancedWebView;

import android.annotation.SuppressLint;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;
import android.webkit.JavascriptInterface;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import android.widget.Toast;

public class ChromeActivity extends AppCompatActivity implements AdvancedWebView.Listener 
{

    //private String postUrl = "http://api.androidhive.info/webview/index.html";
    private ProgressBar progressBar;
    private float m_downX;
    private ImageView imgHeader;
	private AdvancedWebView webView;
	private String postUrl = "https://aweb41.github.io/AsepMo/";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_engine);
		
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        webView = (AdvancedWebView) findViewById(R.id.webView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        imgHeader = (ImageView) findViewById(R.id.backdrop);
		imgHeader.setImageResource(R.drawable.image_1);
        if (!TextUtils.isEmpty(getIntent().getStringExtra("postUrl"))) {
            postUrl = getIntent().getStringExtra("postUrl");
        }

        initWebView();
        initCollapsingToolbar();
        renderPost();


        // enable / disable javascript
        // webView.getSettings().setJavaScriptEnabled(true);

        // loading url into web view
        // webView.loadUrl("http://www.google.com");

        /**
         * loading custom html into webivew
         * */
        /*
        String customHtml = "<html><body><h1>Hello, WebView</h1> <h1>Heading 1</h1><h2>Heading 2</h2><h3>Heading 3</h3>" +
                "<p>This is a sample paragraph.</p></body></html>";
        webView.loadData(customHtml, "text/html", "UTF-8");
        */

        /**
         * Enabling zoom-in controls
         * */
        /*
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(true);
        */

        // Loading local html file into web view
        // webView.loadUrl("file:///android_asset/sample.html");

        /**
         * Loading custom fonts and css
         * */
        /*
        String style = "<style type='text/css'>@font-face { font-family: 'roboto'; src: url('Roboto-Light.ttf');}@font-face { font-family: 'roboto-medium'; src: url('Roboto-Medium.ttf'); }" +
                "body{color:#666;font-family: 'roboto';padding: 0.3em;}";
        style += "a{color:" + String.format("#%06X", (0xFFFFFF & ContextCompat.getColor(this, R.color.colorPrimaryDark))) + "}</style>";
        String customHtml = "<h1>Hello, WebView</h1> <h1>Heading 1</h1><h2>Heading 2</h2><h3>Heading 3</h3>" +
                "<p>This is a sample paragraph.</p>";
        String content = "<html>" + style + "<body'>" + customHtml + "</body></Html>";
        webView.loadDataWithBaseURL("file:///android_asset/", content, "text/html", "utf-8", null);
        */
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
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
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

    private void renderPost() {
        webView.loadUrl(postUrl);
		//webView.loadUrl("file:///android_asset/sample.html");
    }

    private void openInAppBrowser(String url) {
        Intent intent = new Intent(ChromeActivity.this, BrowserActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }

    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar txtPostTitle on scroll
     */
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the txtPostTitle when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle("AWeb Browser");
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });

        // loading toolbar header image
        Glide.with(getApplicationContext())
		.load("http://api.androidhive.info/webview/nougat.jpg")
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgHeader);
    }

    private class MyWebChromeClient extends WebChromeClient {
        Context context;

        public MyWebChromeClient(Context context) {
            super();
            this.context = context;
        }

		@Override
		public void onReceivedTitle(WebView view, String title)
		{
			// TODO: Implement this method
			super.onReceivedTitle(view, title);
			Toast.makeText(ChromeActivity.this, title, Toast.LENGTH_SHORT).show();
			
		}
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
		Toast.makeText(ChromeActivity.this, "onDownloadRequested(url = "+url+",  suggestedFilename = "+suggestedFilename+",  mimeType = "+mimeType+",  contentLength = "+contentLength+",  contentDisposition = "+contentDisposition+",  userAgent = "+userAgent+")", Toast.LENGTH_LONG).show();

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
		if (Utils.isSameDomain(postUrl, url)) {
			Intent intent = new Intent(ChromeActivity.this, ChromeActivity.class);
			intent.putExtra("postUrl", url);
			startActivity(intent);
		} else {
			// launch in-app browser i.e BrowserActivity
			openInAppBrowser(url);
		}
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
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// TODO: Implement this method
		switch(item.getItemId()){
			case android.R.id.home:
				finish();
				return true;
		}
		return super.onOptionsItemSelected(item);
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
	
	public class JavaScriptAction {
        @JavascriptInterface
        public void action() {
            Toast.makeText(ChromeActivity.this, "Called from JavaScript", Toast.LENGTH_LONG).show();
        }
		
		// forward the browser navigation
	   @JavascriptInterface
       public void forward() {
			if (webView.canGoForward()) {
				webView.goForward();
			}
		}
		
		// backward the browser navigation
		@JavascriptInterface
		public void back() {
			if (webView.canGoBack()) {
				webView.goBack();
			}
		}
    }
}
