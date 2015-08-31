package com.battaman.commuter.parser;

import org.json.JSONObject;

public class MapQuestJsonParser {

	private JSONObject json;
	
	public MapQuestJsonParser(String json) {
		this.json = new JSONObject(json);
	}
	
	public int parseRealTimeInSeconds()
	{
		return json.getJSONObject("route").getInt("realTime");
	}
}
