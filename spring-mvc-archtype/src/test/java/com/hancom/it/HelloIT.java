package com.hancom.it;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class HelloIT{
	
	private static String port, name;
	
	@BeforeAll
	public static void findPort() {
		port = System.getProperty("servlet.port", "8080");
	}

	@Test
	public void hello() throws IOException {
		String testName = "testname";
		HttpURLConnection connection = (HttpURLConnection)new URL("http://localhost:" + port +"/hello?name="+testName).openConnection();
		{
			connection.connect();
			assertEquals(200, connection.getResponseCode());
			
			try (InputStream in = connection.getInputStream()) {
				String output = IOUtils.toString(in, StandardCharsets.UTF_8);
				assertTrue(output.contains(testName), "Sent name not found in page  with source \n" + output);
			}
		}
	}

}
