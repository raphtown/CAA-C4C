package com.caa.bspace;

public class BSpaceResourceFiles implements BSpaceResource {
	private String BSPACE_DAV_URL = "dav";
	private BSpaceUser user;
	
	public BSpaceResourceFiles(BSpaceUser user) {
		this.user = user;
		
		String nextClass = this.user.classes.getFirst();
		String[] components = { BSpaceConstants.BSPACE_URL, this.BSPACE_DAV_URL, nextClass };
		System.out.println(BSpaceResourceFiles.implode("/", components));
	}
	
	private static String implode(String sep, String[] components) {
		return BSpaceResourceFiles.implode(sep, components, true);
	}
	
	private static String implode(String sep, String[] components, Boolean trailingSlash) {
		StringBuilder sb = new StringBuilder();
		for (String s : components) {
			sb.append(s);
			sb.append(sep);
		}
		String out = sb.toString();
		out = out.substring(0,  out.length() - (trailingSlash ? 0 : 1));
		return out;
	}
}

