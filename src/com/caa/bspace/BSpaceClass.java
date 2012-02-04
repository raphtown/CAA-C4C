package com.caa.bspace;

import java.util.*;
import org.htmlcleaner.*;

import android.text.Html;

public class BSpaceClass {
    
    private static final String BSPACE_CLASS_BASE_URL = "https://bspace.berkeley.edu/portal/site/";
    private static final String XPATH_SIDEBAR = "//div[@id='toolMenu']//a";
    
    private static final String[] supportedResources = { "Syllabus", "Resources", "Gradebook" };
    
    BSpaceUser user;
    public String uuid, name;
    public HashMap<String, String> uuidMap;
    
    public BSpaceClass(BSpaceUser user, String uuid, String name) {
        this.user = user;
        this.uuid = uuid;
        this.name = Html.fromHtml(name).toString();
    }
    
    public void parseSidebar() {
        String url = BSPACE_CLASS_BASE_URL + uuid;
        String html = user.openPage(url);
        
        HtmlCleaner cleaner = new HtmlCleaner();
        TagNode root = cleaner.clean(html);
        
        try {
            Object[] sidebarEls = root.evaluateXPath(XPATH_SIDEBAR);
            HashSet<String> supported = new HashSet<String>(Arrays.asList(supportedResources));
            HashSet<String> actual = new HashSet<String>();
            uuidMap = new HashMap<String, String>();
            
            for (Object obj : sidebarEls) {
                TagNode el = (TagNode) obj;
                String[] components = el.getAttributeByName("href").split("/");
                try {
                    UUID uuidTest = UUID.fromString(components[components.length - 1]);
                    String name = ((TagNode) el.evaluateXPath("span")[0]).getText().toString();
                    String uuid = components[components.length - 1];
                    actual.add(name);
                    uuidMap.put(name, uuid);
                } catch (Exception e) {
                    // not a valid UUID; do nothing
                }
            }
//            supported.retainAll(actual); // Intersect support and actual resource list; result stored in supported
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public String toString() {
    	return name;
    }

}
