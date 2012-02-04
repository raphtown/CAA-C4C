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
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.TextView;

public class BSpaceMobileActivity extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
	static BSpaceUser user;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ProgressBar mProgress = (ProgressBar) findViewById(R.id.loginprogress);
		mProgress.setVisibility(ProgressBar.GONE);
        
        Button button = (Button)findViewById(R.id.loginsubmit);
        button.setOnClickListener(this);
        BSpaceUser user = new BSpaceUser("chinesedewey", "password OCF 1");
       BSpaceFilesResource a = new BSpaceFilesResource(user, "30530304-7b27-4b33-9572-592fade4c2e8");
    }

	@Override
	public void onClick(View arg0) {
	//	
		ProgressBar mProgress = (ProgressBar) findViewById(R.id.loginprogress);
		
		
		// DERP this doesn't work
		mProgress.setVisibility(ProgressBar.VISIBLE);
		
    	String username = ((TextView)findViewById(R.id.loginentry)).getText().toString();
		String userpassword = ((TextView)findViewById(R.id.passwordentry)).getText().toString();
		
    	BSpaceMobileActivity.user = new BSpaceUser(username,
				userpassword);
		
		mProgress.setVisibility(ProgressBar.GONE);
		

		if(user.classes.size() == 0)
		{
			Toast.makeText(this, "Invalid Password", Toast.LENGTH_SHORT).show();
			return;
		}
		
		

		 Intent myIntent = new Intent(BSpaceMobileActivity.this, ClassViewActivity.class);
	     BSpaceMobileActivity.this.startActivity(myIntent);
		
	}
}
