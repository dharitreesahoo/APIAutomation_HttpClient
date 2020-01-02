package com.qa.tests;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.util.TestUtil;

import junit.framework.Assert;

public class GetAPITest extends TestBase {

	TestBase testBase;
	String serviceURL;
	String apiURL;
	String url;
	RestClient restClient;
	CloseableHttpResponse closeableHttpResponse;

	@BeforeMethod
	public void setup() throws ClientProtocolException, IOException {
		testBase = new TestBase();
		serviceURL = prop.getProperty("URL");
		apiURL = prop.getProperty("serviceURL");
		url = serviceURL + apiURL;

	}

	@Test
	public void getTest() throws ClientProtocolException, IOException {
		restClient = new RestClient();
		closeableHttpResponse = restClient.get(url);

		// a. get Status code
		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status code is==>  " + statusCode);
		Assert.assertEquals( statusCode,RESPONSE_STATUS_200);

		// b.Json String
		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
		System.out.println(responseString);
		JSONObject responseJSON = new JSONObject(responseString);
		String perPageVal = TestUtil.getValueByJPath(responseJSON, "/per_page");
		System.out.println(perPageVal);
		Assert.assertEquals(perPageVal, 6);
		
		//total attribute
		String totalVal = TestUtil.getValueByJPath(responseJSON, "/total");
		System.out.println(totalVal);
		Assert.assertEquals(totalVal, 12);
		
		//JSON Array
		TestUtil.getValueByJPath(responseJSON, "/data[0]/last_name");
		TestUtil.getValueByJPath(responseJSON, "/data[0]/id");
		TestUtil.getValueByJPath(responseJSON, "/data[0]/avatar");
		TestUtil.getValueByJPath(responseJSON, "/data[0]/firstname");
		
		// c.get all headers
		Header[] headers = closeableHttpResponse.getAllHeaders();
		HashMap<String, String> allheaders = new HashMap<String, String>();
		for (Header header : headers) {
			allheaders.put(header.getName(), header.getValue());
		}
		System.out.println("Header Array ===" + allheaders);
		

	}
}
