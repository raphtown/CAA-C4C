package com.caa.ui;

import com.caa.bspace.R;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class SyllabusViewActivity extends Activity {
	
	WebView mWebView;
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.syllabus);

	    mWebView = (WebView) findViewById(R.id.webview);
	    mWebView.getSettings().setJavaScriptEnabled(true);
	    mWebView.loadUrl("http://www.google.com");
	}
}
