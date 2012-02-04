package com.caa.ui;

import com.caa.bspace.*;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class SyllabusViewActivity extends Activity {
	
	WebView mWebView;
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.syllabus);
	    
//	    BSpaceSyllabusResource rc = BSpaceMobileActivity.currentClass;

	    mWebView = (WebView) findViewById(R.id.webview);
	    mWebView.getSettings().setJavaScriptEnabled(true);
	    mWebView.loadUrl("http://www.google.com");
	}
}
