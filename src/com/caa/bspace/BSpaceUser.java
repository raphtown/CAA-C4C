package com.caa.bspace;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;

import android.util.Log;

import org.apache.http.client.protocol.ClientContext;

public class BSpaceUser {
    
    public static final String TAG = "BSpaceUser";
    
    private static final String BSPACE_LOGIN_URL = "https://auth.berkeley.edu/cas/login?service=https%3A%2F%2Fbspace.berkeley.edu%2Fsakai-login-tool%2Fcontainer&renew=true";
    private static final String BSPACE_PORTAL_URL = "https://bspace.berkeley.edu/portal/";

    public String htmlSource;
    private HttpClient httpClient;
    private CookieStore cookieStore;
    private HttpContext localContext;
    
    private String getCalnetNoOpConversation(){
    	HttpGet calnetLoginGet = new HttpGet(BSPACE_LOGIN_URL);
    	try{
	    	HttpResponse response = httpClient.execute(calnetLoginGet, localContext);
	    	BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	    	String src = "";
	        String line = "";
	        StringBuilder sb = new StringBuilder();
	        while ((line = rd.readLine()) != null) {
	            sb.append(line);
	        }
	        src = sb.toString();
	        
	        int startConversationIndex = src.indexOf("_cNoOpConversation");
            int endConversationIndex = src.indexOf("\"", startConversationIndex);
            
            String noOpConversation = src.substring(startConversationIndex, endConversationIndex);
            Log.d(TAG, noOpConversation);
            return noOpConversation;
    	} catch (Exception e) {
    	}
    	return null;
    }
    
    private void calnetLogin (String username, String password) {
    	String calnetNoOpConversation = getCalnetNoOpConversation();
    	Log.d(TAG, calnetNoOpConversation);
    	HttpPost calnetLoginPost = new HttpPost(BSPACE_LOGIN_URL);
    	
    	List<NameValuePair> loginNameValuePairs = new ArrayList<NameValuePair>(3);
        loginNameValuePairs.add(new BasicNameValuePair("username", username));
        loginNameValuePairs.add(new BasicNameValuePair("password", password));
        loginNameValuePairs.add(new BasicNameValuePair("lt", calnetNoOpConversation));
        loginNameValuePairs.add(new BasicNameValuePair("_eventId", "submit"));
        
        try {
        	calnetLoginPost.setEntity(new UrlEncodedFormEntity(loginNameValuePairs));

            HttpResponse response = httpClient.execute(calnetLoginPost, localContext);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line = "";
            StringBuilder sb = new StringBuilder();
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            htmlSource = sb.toString();
            Log.d(TAG, htmlSource);
        } catch (Exception e) {
        }
    }
    
    private String getMainPage() {
    	HttpGet mainPageGet = new HttpGet(BSPACE_PORTAL_URL);
    	
    	try{
    		 HttpResponse response = httpClient.execute(mainPageGet, localContext);
    		 BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
             String line = "";
             StringBuilder sb = new StringBuilder();
             while ((line = rd.readLine()) != null) {
                 sb.append(line);
             }
             htmlSource = sb.toString();
             Log.d(TAG, htmlSource);
    	} catch (Exception e){
    	}
    	return "no";
    }
    
    public BSpaceUser(String username, String password) {
    	httpClient = new DefaultHttpClient();
        cookieStore = new BasicCookieStore();
        //httpClient.getParams().setParameter(ClientPNames.MAX_REDIRECTS, 3); 
        localContext = new BasicHttpContext();
        localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

        calnetLogin(username, password);
        getMainPage();
    }
}
