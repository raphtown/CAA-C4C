package com.caa.bspace;

public class BSpaceMainPageResource implements BSpaceResource {
    
    private static final String BSPACE_CLASS_MAIN_BASE_URL = "https://bspace.berkeley.edu/access/site/";
    
    private BSpaceClass ownerClass;
    private String url;
    
    public BSpaceMainPageResource(BSpaceClass ownerClass) {
        this.ownerClass = ownerClass;
        this.url = BSPACE_CLASS_MAIN_BASE_URL + ownerClass.uuid;
    }
    
    public String getHtml() {
        return ownerClass.user.openPage(url);
    }

}
