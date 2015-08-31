package com.battaman.commuter;

import java.io.IOException;
import java.net.URLEncoder;

import com.battaman.commuter.http.HttpRequester;
import com.battaman.commuter.parser.MapQuestJsonParser;

public class MapQuestCommuteTimeRetriever implements CommuteTimeRetriever {

	private static final String MQ_REQUEST_URL = "http://www.mapquestapi.com/directions/v2/route?key=y8AF3zlYF43HC7HgcPWGlU5aMud9MhEF&from=FROM_ADDRESS&to=TO_ADDRESS";
	private HttpRequester httpRequester;
	
	public MapQuestCommuteTimeRetriever(HttpRequester httpRequester)
	{
		this.httpRequester = httpRequester;
	}
	
	public int retrieveCommuteTime(String fromAddress, String toAddress)
	{
		String jsonResponse;
		try {
			fromAddress = URLEncoder.encode(fromAddress, "UTF-8");
			toAddress = URLEncoder.encode(toAddress, "UTF-8");
			
			String requestUrl = MQ_REQUEST_URL.replace("FROM_ADDRESS", fromAddress);
			requestUrl = requestUrl.replace("TO_ADDRESS", toAddress);
			jsonResponse = httpRequester.sendHttpGet(requestUrl);
		}
		catch(IOException ioException) {
			return -1;
		}
		
		return new MapQuestJsonParser(jsonResponse).parseRealTimeInSeconds();
	}
}


