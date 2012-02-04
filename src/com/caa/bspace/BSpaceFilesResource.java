package com.caa.bspace;

import java.util.ArrayList;

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
		private ArrayList<BSpaceDirectory> subdirectories;
		private ArrayList<BSpaceFile> files;
		
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
			String html = "<html><head><link href=\"https://bspace.berkeley.edu/css/default.css\" type=\"text/css\" rel=\"stylesheet\" media=\"screen\" /><STYLE type=\"text/css\"><!-- td {padding-right: .5em} --> </STYLE> </head><body> <div style=\"padding: 16px\"> <h2>Contents of /dav/1c446e0d-afa9-4dd3-aba9-1d82ad8af048/</h2> <table> <tr><td><a href=\"Discussion%20Sections/\">Discussion Sections</a></td><td><b>Folder</b></td><td></td><td></td><td></td></tr> <tr><td><a href=\"Homework%20Solutions/\">Homework Solutions</a></td><td><b>Folder</b></td><td></td><td></td><td></td></tr> <tr><td><a href=\"Homeworks/\">Homeworks</a></td><td><b>Folder</b></td><td></td><td></td><td></td></tr>  <tr><td><a href=\"Lecture%20Notes/\">Lecture Notes</a></td><td><b>Folder</b></td><td></td><td></td><td></td></tr> </table></div></body></html>";
			HtmlCleaner cleaner = new HtmlCleaner();
			TagNode rootNode = cleaner.clean(html);
			
			try {
	            NodeList[] classEls = (NodeList) rootNode.evaluateXPath("//a");
	            for (Node node : classEls) {
	                String name = node.getChildNodes();
	                Log.d("bspaceuser", name);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
	}
	
	private String getRootDavUrl(){
		return "https://bspace.berkeley.edu/dav/" + classUid;
	}
	
	public BSpaceFilesResource(String classUid, BSpaceUser user){
		this.classUid = classUid;
		this.user = user;
		
		rootDirectory = new BSpaceDirectory();
		rootDirectory.getItems();
	}
}
