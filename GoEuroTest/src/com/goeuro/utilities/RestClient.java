package com.goeuro.utilities;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class RestClient {

	/**
	 * HostnameVerifier Always overrides the authentication scheme
	 * and always returns true
	 * @return
	 */
	private HostnameVerifier getHostnameVerifier() {
		return new HostnameVerifier() {
			@Override
			public boolean verify(String hostname,
					javax.net.ssl.SSLSession sslSession) {
				return true;
			}
		};
	}

	/**
	 * retrieveResource establishes SSL connection  to retrieve the 
	 * JSON objects from the RESTful Service
	 * 
	 * @param url
	 * @return
	 */
	public synchronized String retrieveResource(String url) {

		String jsonOutput = null;
		Client client = null;
		SSLContext sslContext = null;
		WebResource webResource = null;

		//Establishing SSL connection
		ClientTrustManager clientTrustManager = new ClientTrustManager();
		try {
			sslContext = SSLContext.getInstance("SSL");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		try {
			sslContext
					.init(null,
							new javax.net.ssl.TrustManager[] { clientTrustManager },
							null);
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}

		//Configuring client API to retrieve information from the server
		DefaultClientConfig defaultClientConfig = new DefaultClientConfig();
		defaultClientConfig
				.getProperties()
				.put(com.sun.jersey.client.urlconnection.HTTPSProperties.PROPERTY_HTTPS_PROPERTIES,
						new com.sun.jersey.client.urlconnection.HTTPSProperties(
								getHostnameVerifier(), sslContext));

		client = Client.create(defaultClientConfig);
		webResource = client.resource(url);
		//Retrieving the JSON objects
		jsonOutput = webResource.get(String.class);

		//Clearing all the API objects
		webResource = null;
		client.destroy();
		client = null;

		return jsonOutput;
	}
}
