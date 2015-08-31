package com.battaman.commuter;

public interface CommuteTimeRetriever {
	public int retrieveCommuteTime(String fromAddress, String toAddress);
}
