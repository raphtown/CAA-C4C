package com.caa.bspace;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;

public class BSpaceUser {
	
	public BSpaceUser(String username, String password){
		HttpClient httpClient = new DefaultHttpClient();
		
		List<NameValuePair> loginNameValuePairs = new ArrayList<NameValuePair>(3);
		loginNameValuePairs.add(new BasicNameValuePair("eid", username));
		loginNameValuePairs.add(new BasicNameValuePair("pw", password));
		loginNameValuePairs.add(new BasicNameValuePair("submit", "login"));
		
		HttpPost loginPost = new HttpPost("https://bspace.berkeley.edu/portal/xlogin");
		
		try{
			loginPost.setEntity(new UrlEncodedFormEntity(loginNameValuePairs));
				
			HttpResponse response = httpClient.execute(loginPost);
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));
			String line = "";
			while ((line = rd.readLine()) != null) {
				Log.d("bspaceuser", line);
			}
		} catch (Exception e){
			Log.e("bspaceuser", "exception", e);

			return;
		}
	}
}
