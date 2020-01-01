package com.qa.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.*;



public class RestClient {
	// 1. GET Method without Headers:
	public CloseableHttpResponse get(String url) throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(url); // http get request
		CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpget);
		//a. get Status code
		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status code is==>  " + statusCode);
		
		//b.Json String
		String responseString  =EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
		
		JSONObject jsonObject = new JSONObject(responseString);
		//c.get all headers
		Header[] headers = closeableHttpResponse.getAllHeaders();
		HashMap<String,String> allheaders = new HashMap<String,String>();
		for (Header header:headers ) {
			allheaders.put(header.getName(), header.getValue());
		}
		System.out.println("Header Array ==="+allheaders);
		return closeableHttpResponse;
	}
	public static void main(String[] args) throws ClientProtocolException, IOException {
		RestClient obj = new RestClient();
		obj.get("https://reqres.in//api/users");
	}
	
		
		
		
}
