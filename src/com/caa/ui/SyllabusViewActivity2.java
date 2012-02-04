package com.caa.ui;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import com.caa.bspace.R;

public class SyllabusViewActivity2 extends Activity {
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.syllabus2);
	    
	    TextView tv = (TextView) findViewById(R.id.syllabustext);
	    tv.setText("blah");
	    
	}

}
