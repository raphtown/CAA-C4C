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
=======
public class BSpaceFilesResource implements BSpaceResource {

    private BSpaceClass ownerClass;
    private String classUid;
    private BSpaceUser user;

    private BSpaceDirectory rootDirectory;

    public interface BSpaceFilesItem{
	}
    
    private class BSpaceFile implements BSpaceFilesItem{
        private BSpaceDirectory parentDirectory;
        private String name;

        public BSpaceFile(BSpaceDirectory parentDirectory, String name) {
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

        public String toString(){
			return name;
		}
        
        public BSpaceDirectory() {
            url = "https://bspace.berkeley.edu/dav/" + classUid;
        }

        public BSpaceDirectory(BSpaceDirectory parentDirectory, String name) {
            this.parentDirectory = parentDirectory;
            this.name = name;
            url = parentDirectory.url + "/" + Uri.encode(this.name);

        }

        public void getItems() {
            String html = user.openPageWithAuth(url);

            int tableStartIndex = html.indexOf("<table>") + "<table>".length();
            int tableEndIndex = html.indexOf("</table>", tableStartIndex) - "</table>".length();

            String tableHtml = html.substring(tableStartIndex, tableEndIndex);

            subdirectories = new ArrayList<BSpaceDirectory>();
            files = new ArrayList<BSpaceFile>();

            for (String tableRow : tableHtml.split("</tr>")) {
                String nameSegment = tableRow.split("</a>")[0];
                String name = nameSegment.substring(nameSegment.lastIndexOf("\">") + 2);

                if (name == "Up one level") {
                    continue;
                }
                if (subdirectories == null) {
                    Log.e("panda", "SUBDIRECTORIES IS NULL");
                }
                if (files == null) {
                    Log.e("panda", "FILES IS NULL");
                }
                if (tableRow.contains("<b>Folder</b>")) {
                    Log.d("bspacefile", "Adding directory " + name);
                    subdirectories.add(new BSpaceDirectory(this, name));
                } else {
                    Log.d("bspacefile", "Adding file " + name);
                    files.add(new BSpaceFile(this, name));
                }
            }
        }
    }

    public BSpaceFilesResource(BSpaceClass ownerClass, String classUid) {
        this.ownerClass = ownerClass;
        this.classUid = classUid;

        rootDirectory = new BSpaceDirectory();
        rootDirectory.getItems();
    }

    public void initUser(BSpaceUser user) {
        this.user = user;
    }
}
