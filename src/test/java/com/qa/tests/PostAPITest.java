package com.qa.tests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.data.Users;
import com.qa.util.TestUtil;

import junit.framework.Assert;

public class PostAPITest extends TestBase {

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
	public void postAPITest() throws JsonGenerationException, JsonMappingException, IOException {
		restClient = new RestClient();
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("content-type", "aplication/json");

		// jackson API://Create user class in Java and insert the data
		ObjectMapper mapper = new ObjectMapper();
		Users users = new Users("morpheus", "leader");

		// Covert POJO to json file:
		mapper.writeValue(new File(System.getProperty("user.dir") + "/src/main/java/com/qa/data/users.json"), users);

		// Convert JSON object to String
		String userJSONString = mapper.writeValueAsString(users);
		System.out.println(userJSONString);

		// java object to json in String:
		String usersJsonString = mapper.writeValueAsString(users);
		System.out.println(usersJsonString);

		closeableHttpResponse = restClient.post(url, usersJsonString, headerMap); // call

		// 1. status code:
		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		Assert.assertEquals(statusCode, testBase.RESPONSE_STATUS_201);

	}

}
