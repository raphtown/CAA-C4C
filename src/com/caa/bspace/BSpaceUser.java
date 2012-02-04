package com.caa.bspace;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
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

import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.protocol.ClientContext;

public class BSpaceUser {

    public String htmlSource;
    private HttpClient httpClient;
    private CookieStore cookieStore;
    private HttpContext localContext;
    
    private String getCalnetNoOpConversation(){
    	HttpGet calnetLoginGet = new HttpGet("https://auth.berkeley.edu/cas/login?service=https%3A%2F%2Fbspace.berkeley.edu%2Fsakai-login-tool%2Fcontainer&renew=true");
    	try{
	    	HttpResponse response = httpClient.execute(calnetLoginGet, localContext);
	    	BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	        String line = "";
	        StringBuilder sb = new StringBuilder();
	        while ((line = rd.readLine()) != null) {
	            sb.append(line);
	        }
	        htmlSource = sb.toString();
	        
	        int startConversationIndex = htmlSource.indexOf("_cNoOpConversation");
            int endConversationIndex = htmlSource.indexOf("\"", startConversationIndex);
            
            String noOpConversation = htmlSource.substring(startConversationIndex, endConversationIndex);
            Log.d("bspaceuser", noOpConversation);
            return noOpConversation;
    	} catch (Exception e){
    	}
    	return null;
    }
    
    private void calnetLogin(String username, String password){
    	String calnetNoOpConversation = getCalnetNoOpConversation();
    	Log.d("bspaceuser", calnetNoOpConversation);
    	HttpPost calnetLoginPost = new HttpPost("https://auth.berkeley.edu/cas/login?service=https%3A%2F%2Fbspace.berkeley.edu%2Fsakai-login-tool%2Fcontainer&renew=true");
    	
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
            Log.d("bspaceuser", htmlSource);
        } catch (Exception e) {
          //  Log.e("bspaceuser", "exception", e);
        }
    }
    
    private String getMainPage(){
    	HttpGet mainPageGet = new HttpGet("https://bspace.berkeley.edu/portal/");
    	
    	try{
    		 HttpResponse response = httpClient.execute(mainPageGet, localContext);
    		 BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
             String line = "";
             while ((line = rd.readLine()) != null) {
            	 Log.d("bspaceuser", line);
             }
    	} catch (Exception e){
    		Log.e("bspaceuser", "exception", e);
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
