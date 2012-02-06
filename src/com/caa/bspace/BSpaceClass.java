package com.caa.bspace;

import java.util.*;
import org.htmlcleaner.*;

import android.text.Html;

public class BSpaceClass {
    
    private static final String BSPACE_CLASS_BASE_URL = "https://bspace.berkeley.edu/portal/site/";
    private static final String XPATH_SIDEBAR = "//div[@id='toolMenu']//a";
    
    BSpaceUser user;
    public String uuid, name;
    public HashMap<String, String> uuidMap;
    
    public BSpaceClass(BSpaceUser user, String uuid, String name) {
        this.user = user;
        this.uuid = uuid;
        this.uuidMap = new HashMap<String, String>();
        this.name = Html.fromHtml(name).toString();
    }
    
    public void parseSidebar() {
        String url = BSPACE_CLASS_BASE_URL + uuid;
        String html = user.openPage(url);
        
        HtmlCleaner cleaner = new HtmlCleaner();
        TagNode root = cleaner.clean(html);
        
        try {
            Object[] sidebarEls = root.evaluateXPath(XPATH_SIDEBAR);
            
            for (Object obj : sidebarEls) {
                TagNode el = (TagNode) obj;
                String[] components = el.getAttributeByName("href").split("/");
                try {
                	String potentialUuid = components[components.length - 1];
                    UUID uuidTest = UUID.fromString(potentialUuid);
                    String name = ((TagNode) el.evaluateXPath("span")[0]).getText().toString();
                    uuidMap.put(name, potentialUuid);
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
        }
    }
    
    public String toString() {
    	return name;
    }
    
    public String getResourceUuid(String resourceName) {
    	return uuidMap.get(resourceName);
    }
}