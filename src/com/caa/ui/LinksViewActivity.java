package com.caa.ui;

import java.util.ArrayList;

import com.caa.bspace.R;
import com.caa.bspace.R.layout;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class LinksViewActivity extends ListActivity
{
	ArrayAdapter<String> adapter;
	ArrayList<String> listItems=new ArrayList<String>();
	
	public void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);

		  listItems.add("LOL");
		  adapter= new ArrayAdapter<String>(this, R.layout.list_item, listItems);
		  setListAdapter(adapter);

		  ListView lv = getListView();
		  lv.setTextFilterEnabled(true);
		  
		  lv.setOnItemClickListener(new OnItemClickListener() {
			    public void onItemClick(AdapterView<?> parent, View view,
			        int position, long id) {
			    	 listItems.add("Jay");
			    	 adapter.notifyDataSetChanged();
			    }
			  });
	}
}
