package com.battaman.commuter;

import java.io.IOException;

import com.battaman.commuter.http.HttpRequester;

public class MockHttpRequester extends HttpRequester {

	private String requestedUrl;
	
	@Override
	public String sendHttpGet(String url) throws IOException {
		this.requestedUrl = url;
		if(url.contains("IOException"))
			throw new IOException("Mock IOException");
		return "{\"route\":{\"hasTollRoad\":false,\"computedWaypoints\":[],\"fuelUsed\":0.86,\"hasUnpaved\":false,\"hasHighway\":true,\"realTime\":1437}}";
	}

	public String getRequestedUrl() {
		return requestedUrl;
	}
}
