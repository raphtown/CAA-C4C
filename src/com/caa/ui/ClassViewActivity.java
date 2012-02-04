package com.caa.ui;

import java.util.ArrayList;

import com.caa.bspace.R;
import com.caa.bspace.R.layout;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



public class ClassViewActivity extends ListActivity {
	

	ArrayList<String> listItems=new ArrayList<String>();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  listItems.add("African American Studies 61A");
	  listItems.add("Jay Kong");
	  listItems.add("Captain Planet");
	  setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, listItems));

	  ListView lv = getListView();
	  lv.setTextFilterEnabled(true);

	  lv.setOnItemClickListener(new OnItemClickListener() {
	    public void onItemClick(AdapterView<?> parent, View view,
	        int position, long id) {
	      // When clicked, show a toast with the TextView text
	    	Intent myIntent = new Intent(ClassViewActivity.this, LinksViewActivity.class);
			 ClassViewActivity.this.startActivity(myIntent);
	    }
	  });
	}
}
