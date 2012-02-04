package com.caa.bspace;

public class BSpaceSyllabusResource implements BSpaceResource {
    
    private static final String BSPACE_SYLLABUS_BASE_URL = "https://bspace.berkeley.edu/portal/site/";
    
    private BSpaceClass ownerClass;
    private String uuid;
    private String url;
    
    public BSpaceSyllabusResource(BSpaceClass ownerClass, String uuid) {
        this.ownerClass = ownerClass;
        this.uuid = uuid;
        this.url = BSPACE_SYLLABUS_BASE_URL + ownerClass.uuid + "/page/" + uuid;
    }
    
    public void getHtml() {
        System.out.println(ownerClass.user.openPage(url));
    }

}
