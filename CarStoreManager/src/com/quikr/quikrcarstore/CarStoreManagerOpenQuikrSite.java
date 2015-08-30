package com.quikr.quikrcarstore;



import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

public class CarStoreManagerOpenQuikrSite extends Activity 
{

	private WebView mWebview ;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		
		String url = intent.getStringExtra(URL);
		String title = intent.getStringExtra(TITLE);
		if (title != null)
			setTitle(title);
		mWebview  = new WebView(this);

		mWebview.getSettings().setJavaScriptEnabled(true); // enable javascript

		final Activity activity = this;

		mWebview.setWebViewClient(new WebViewClient()
		{
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
			{
				Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
			}
		});

		mWebview.loadUrl(url);
		
		setContentView(mWebview );

	}

	boolean loadingFinished = true;
	boolean redirect = false;
	public static final String TITLE = "title";
	public static final String URL = "url";
}

