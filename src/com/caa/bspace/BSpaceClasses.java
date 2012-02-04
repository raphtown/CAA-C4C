package com.caa.bspace;

import java.util.LinkedList;
import org.htmlcleaner.*;

public class BSpaceClasses {
    
    public BSpaceUser user; 
    private LinkedList<String> classes;
    private static final String XPATH_CLASSES = "//ul[@id='siteLinkList']//a";

    public BSpaceClasses(BSpaceUser user) {
        this.user = user;
        this.classes = new LinkedList<String>();
        System.out.println("Hi mom!");
        this.parse();
    }
    
    private void parse() {
        HtmlCleaner cleaner = new HtmlCleaner();
        TagNode root = cleaner.clean(this.user.htmlSource);
        
        try {
            Object[] classEls = root.evaluateXPath(XPATH_CLASSES);
            for (Object obj : classEls) {
                TagNode el = (TagNode) obj;
                String[] components = el.getAttributeByName("href").split("/");
                if (components.length > 1)
                    this.classes.add(components[components.length - 1]); 
            }
            System.out.println("Class links: "+ this.classes.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
}
