package com.caa.ui;

import com.caa.bspace.BSpaceFilesResource;
import com.caa.bspace.BSpaceUser;
import com.caa.bspace.R;
import com.caa.bspace.R.id;
import com.caa.bspace.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class BSpaceMobileActivity extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button button = (Button)findViewById(R.id.loginsubmit);
        button.setOnClickListener(this);
        BSpaceUser user = new BSpaceUser("chinesedewey", "password OCF 1");
       BSpaceFilesResource a = new BSpaceFilesResource(user, "30530304-7b27-4b33-9572-592fade4c2e8");
    }

	@Override
	public void onClick(View arg0) {
	//	Toast.makeText(this, "Beep Bop", Toast.LENGTH_SHORT).show();
		 Intent myIntent = new Intent(BSpaceMobileActivity.this, ClassViewActivity.class);
	     BSpaceMobileActivity.this.startActivity(myIntent);
		
	}
}