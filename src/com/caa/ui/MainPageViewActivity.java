package com.caa.ui;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.caa.bspace.*;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class MainPageViewActivity extends Activity {
	
	WebView mWebView;
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.syllabus);
	    
	    BSpaceMainPageResource rc = new BSpaceMainPageResource(BSpaceMobileActivity.currentClass);
	    String html = rc.getHtml();
	    
	    System.out.println(html);

	    mWebView = (WebView) findViewById(R.id.webview);
	    mWebView.getSettings().setJavaScriptEnabled(true);
	    
	    try {
			html = URLEncoder.encode(html, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    html = html.replace('+', ' ');
	    mWebView.loadData(html, "text/html", "utf-8");
	}
}
