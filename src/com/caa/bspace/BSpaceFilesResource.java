package com.caa.bspace;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Html;
import android.util.Log;

public class BSpaceFilesResource implements BSpaceResource {
    private BSpaceClass ownerClass;
    private BSpaceUser user;

    public BSpaceDirectory rootDirectory;
    
    public static HashMap<String, BSpaceDirectory> directoryMap = new HashMap<String, BSpaceDirectory>();

    public class BSpaceFilesItem{
    	protected BSpaceDirectory parentDirectory;
    	protected String name;
    	public String url;

	}
    
    public class BSpaceFile extends BSpaceFilesItem{
        

        public BSpaceFile(BSpaceDirectory parentDirectory, String name) {
            this.parentDirectory = parentDirectory;
            this.name = Html.fromHtml(name).toString();
            try {
				url = parentDirectory.url + "/" + java.net.URLEncoder.encode(this.name, "utf-8").replace("+", "%20");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
        }
        
        public String toString(){
			return name;
		}
    }

    public class BSpaceDirectory extends BSpaceFilesItem{
        public ArrayList<BSpaceDirectory> subdirectories;
        public ArrayList<BSpaceFile> files;

        public String toString(){
			return name;
		}
        
        public BSpaceDirectory() {
            url = "https://bspace.berkeley.edu/dav/group/" + ownerClass.uuid;
        }

        public BSpaceDirectory(BSpaceDirectory parentDirectory, String name) {
            this.parentDirectory = parentDirectory;
            this.name = Html.fromHtml(name).toString();
            try {
				url = parentDirectory.url + "/" + java.net.URLEncoder.encode(this.name, "utf-8").replace("+", "%20");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
            directoryMap.put(url, this);
        }

        public void getItems() {
            String html = user.openPageWithAuth(url);
            Log.d("bspacedirectory", html);

            subdirectories = new ArrayList<BSpaceDirectory>();
            files = new ArrayList<BSpaceFile>();
            
            int tableStartIndex = html.indexOf("<table>") + "<table>".length();
            int tableEndIndex = html.indexOf("</table>", tableStartIndex);

            Log.d("tableindex", tableStartIndex + " to " + tableEndIndex);
            if(tableStartIndex == -1 || tableEndIndex == -1 || tableStartIndex == tableEndIndex){
            	Log.w("bspacefiles", "No table to parse");
            	return;
            }
            
            String tableHtml = html.substring(tableStartIndex, tableEndIndex);

            for (String tableRow : tableHtml.split("</tr>")) {
                String nameSegment = tableRow.split("</a>")[0];
                String name = nameSegment.substring(nameSegment.lastIndexOf("\">") + 2);

                if (name.equals("Up one level")){
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

    public BSpaceFilesResource(BSpaceUser user, BSpaceClass bspaceClass) {
        this.user = user;
        this.ownerClass = bspaceClass;

        rootDirectory = new BSpaceDirectory();
        rootDirectory.getItems();
    }

    public void initUser(BSpaceUser user) {
        this.user = user;
    }
}
