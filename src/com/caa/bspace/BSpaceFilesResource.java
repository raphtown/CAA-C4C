package com.caa.bspace;

import java.util.ArrayList;

import android.net.Uri;
import android.util.Log;

public class BSpaceFilesResource implements BSpaceResource {
	
	private BSpaceClass bspaceClass;
	private BSpaceUser user;
	
	public BSpaceDirectory rootDirectory;
	
	public interface BSpaceFilesItem{
	}
	
	public class BSpaceFile implements BSpaceFilesItem{
		private BSpaceDirectory parentDirectory;
		private String name;
		
		public BSpaceFile(BSpaceDirectory parentDirectory, String name){
			this.parentDirectory = parentDirectory;
			this.name = name;
		}
		
		public String toString(){
			return name;
		}
	}
	
	public class BSpaceDirectory implements BSpaceFilesItem{
		public ArrayList<BSpaceDirectory> subdirectories;
		public ArrayList<BSpaceFile> files;
		
		private BSpaceDirectory parentDirectory;
		private String name;
		private String url;
		
		public BSpaceDirectory(){
			url = "https://bspace.berkeley.edu/dav/" + bspaceClass.uuid;
		}
		
		public String toString(){
			return name;
		}
		
		public BSpaceDirectory(BSpaceDirectory parentDirectory, String name){
			this.parentDirectory = parentDirectory;
			this.name = name;
			url = parentDirectory.url + "/" + Uri.encode(this.name);
			
			
		}
		
		public void getItems(){
			String html = user.openPageWithAuth(url);
			
			subdirectories = new ArrayList<BSpaceDirectory>();
			files = new ArrayList<BSpaceFile>();
			
			int tableStartIndex = html.indexOf("<table>") + "<table>".length();
			int tableEndIndex = html.indexOf("</table>", tableStartIndex) - "</table>".length();
			if(tableStartIndex == -1 || tableEndIndex == -1){
				Log.d("bspaceFiles", html);
				Log.w("bspacefiles", "unable to parse table");
				return;
			}
			String tableHtml = html.substring(tableStartIndex, tableEndIndex);
			
			
			
			for(String tableRow : tableHtml.split("</tr>")){
				String nameSegment = tableRow.split("</a>")[0];
				String name = nameSegment.substring(nameSegment.lastIndexOf("\">") + 2);
				
				if (name == "Up one level"){
					continue;
				}
				if(subdirectories == null){
					Log.e("bspacefiles", "SUBDIRECTORIES IS NULL");
				}
				if(files == null){
					Log.e("bspacefiles", "FILES IS NULL");
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
	
	public BSpaceFilesResource(BSpaceUser user, BSpaceClass bspaceClass){
		this.bspaceClass = bspaceClass;
		this.user = user;
		
		rootDirectory = new BSpaceDirectory();
		rootDirectory.getItems();
	}
}