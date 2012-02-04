package com.caa.bspace;

public class BSpaceClass {
    
    private String uuid, name;
    
    public BSpaceClass (String uuid, String name) {
        this.uuid = uuid;
        this.name = name;
        System.out.println("Initialization " + uuid + " " + name);
    }

}
