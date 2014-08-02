package com.example.android.photobyintent;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class WebViewActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.web_view);
		WebView webView = (WebView) findViewById(R.id.webView);
		String url = getIntent().getExtras().getString("url");
		webView.loadUrl(url);
	}
	
}
