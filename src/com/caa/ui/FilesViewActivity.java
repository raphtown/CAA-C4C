package com.caa.ui;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.caa.bspace.BSpaceFilesResource;
import com.caa.bspace.BSpaceFilesResource.*;
import com.caa.bspace.R;

public class FilesViewActivity extends ListActivity
{
	ArrayAdapter<BSpaceFilesItem> adapter;
	ArrayList<BSpaceFilesItem> listItems=new ArrayList<BSpaceFilesItem>();
	
	public void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);
		  
		  BSpaceFilesResource filesResource = new BSpaceFilesResource(BSpaceMobileActivity.currentUser, BSpaceMobileActivity.currentClass);
		  
		  String requestedDirectoryUrl = getIntent().getStringExtra("requestedDirectory");
		  Log.d("filesview", "requested directory was " + requestedDirectoryUrl);
		  
		  BSpaceDirectory requestedDirectory = BSpaceFilesResource.directoryMap.get(requestedDirectoryUrl);
		  if(requestedDirectory == null){
			  requestedDirectory = filesResource.rootDirectory;
		  } else {
			  requestedDirectory.getItems();
		  }
		  
		  for(BSpaceFilesItem subdirectory : requestedDirectory.subdirectories){
			  listItems.add(subdirectory);
		  }
		  for(BSpaceFilesItem file: requestedDirectory.files){
			  listItems.add(file);
		  }
		  
		  
		  
		  if (listItems.size() == 0){
			  Toast.makeText(FilesViewActivity.this.getApplicationContext(), "No files found for this class.", Toast.LENGTH_LONG).show();
		  } else {
			  adapter= new ArrayAdapter<BSpaceFilesItem>(this, R.layout.list_item, listItems);
			  setListAdapter(adapter);

			  ListView lv = getListView();
			  lv.setTextFilterEnabled(true);
			  
			  lv.setOnItemClickListener(new OnItemClickListener() {
				    public void onItemClick(AdapterView<?> parent, View view,
				        int position, long id) {
				    	BSpaceFilesItem selectedItem = listItems.get(position);
				    	if (selectedItem instanceof BSpaceDirectory){
				    		BSpaceDirectory selectedDirectory = (BSpaceDirectory) selectedItem;
				    		Log.d("filesview", "Pretend I'm doing something with dir " + selectedDirectory);
				    		FilesViewActivity.this.startActivity(new Intent(FilesViewActivity.this, FilesViewActivity.class).putExtra("requestedDirectory", selectedDirectory.url));
				    	} else {
				    		Log.d("filesview", "What do I do with this file? " + selectedItem);
				    	}
				    	 adapter.notifyDataSetChanged();
				    }
				  });
		  }
	}
}
