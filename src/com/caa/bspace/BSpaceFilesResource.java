package com.caa.bspace;

import java.util.ArrayList;

import org.apache.http.HttpConnection;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.net.Uri;
import android.util.Log;

public class BSpaceFilesResource {
	
	private String classUid;
	private BSpaceUser user;
	
	private BSpaceDirectory rootDirectory;
	
	private class BSpaceFile{
		private BSpaceDirectory parentDirectory;
		private String name;
		
		public BSpaceFile(BSpaceDirectory parentDirectory, String name){
			this.parentDirectory = parentDirectory;
			this.name = name;
		}
	}
	
	private class BSpaceDirectory{
		public ArrayList<BSpaceDirectory> subdirectories;
		public ArrayList<BSpaceFile> files;
		
		private BSpaceDirectory parentDirectory;
		private String name;
		private String url;
		
		public BSpaceDirectory(){
			url = "https://bspace.berkeley.edu/dav/" + classUid;
		}
		
		public BSpaceDirectory(BSpaceDirectory parentDirectory, String name){
			this.parentDirectory = parentDirectory;
			this.name = name;
			url = parentDirectory.url + "/" + Uri.encode(this.name);
			
			
		}
		
		public void getItems(){
			String html = user.openPageWithAuth(url);
			
			int tableStartIndex = html.indexOf("<table>") + "<table>".length();
			int tableEndIndex = html.indexOf("</table>", tableStartIndex) - "</table>".length();
			
			String tableHtml = html.substring(tableStartIndex, tableEndIndex);
			
			subdirectories = new ArrayList<BSpaceDirectory>();
			files = new ArrayList<BSpaceFile>();
			
			for(String tableRow : tableHtml.split("</tr>")){
				String nameSegment = tableRow.split("</a>")[0];
				String name = nameSegment.substring(nameSegment.lastIndexOf("\">") + 2);
				
				if (name == "Up one level"){
					continue;
				}
				if(subdirectories == null){
					Log.e("panda", "SUBDIRECTORIES IS NULL");
				}
				if(files == null){
					Log.e("panda", "FILES IS NULL");
				}
				if(tableRow.contains("<b>Folder</b>")){
					Log.d("bspacefile", "Adding directory " + name);
					subdirectories.add(new BSpaceDirectory(this, name));
				} else {
					Log.d("bspacefile", "Adding file " + name);
					files.add(new BSpaceFile(this, name));
				}
			}
		}
	}
	
	public BSpaceFilesResource(BSpaceUser user, String classUid){
		this.classUid = classUid;
		this.user = user;
		
		rootDirectory = new BSpaceDirectory();
		rootDirectory.getItems();
	}
}
