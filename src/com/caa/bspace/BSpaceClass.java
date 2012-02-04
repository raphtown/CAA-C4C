package com.caa.bspace;

import java.lang.reflect.Constructor;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.htmlcleaner.*;

import android.text.Html;

public class BSpaceClass {
    
    private static final String BSPACE_CLASS_BASE_URL = "https://bspace.berkeley.edu/portal/site/";
    private static final String XPATH_SIDEBAR = "//div[@id='toolMenu']//a";
    
    private static final String[] supportedResources = { "Syllabus", "Resources", "Gradebook" };
    
    private BSpaceUser user;
    public String uuid, name;
    private LinkedList<BSpaceResource> resources;
    
    public BSpaceClass(BSpaceUser user, String uuid, String name) {
        this.user = user;
        this.uuid = uuid;
        this.name = Html.fromHtml(name).toString();
        this.resources = new LinkedList<BSpaceResource>();
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
            HashMap<String, String> uuidMap = new HashMap<String, String>();
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
            supported.retainAll(actual); // Intersect support and actual resource list; result stored in supported
            for (String resourceName : supported) {
                // Sigh...
                BSpaceResource rsrc;
                String uuid = uuidMap.get(resourceName);
                if (resourceName.equals("Syllabus")) {
                    rsrc = new BSpaceSyllabusResource(this, uuid);
                } else if (resourceName.equals("Resources")) {
                    rsrc = new BSpaceFilesResource(this, uuid);
                    ((BSpaceFilesResource) rsrc).initUser(user);
                } else if (resourceName.equals("Gradebook")) {
                    rsrc = new BSpaceGradebookResource(this, uuid);
                } else {
                    rsrc = null; // -__-
                }
//                String className = "BSpace" + Pattern.compile("[^\\w]").matcher(resourceName).replaceAll("") + "Resource";
//                BSpaceResource rsrc = new BSpaceSyllabusResource(this, uuidMap.get(resourceName));
//                BSpaceResource rsrc = (BSpaceResource) Class.forName(className).getConstructor(BSpaceClass.class, String.class).newInstance(this, uuidMap.get(resourceName));
                resources.add(rsrc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
