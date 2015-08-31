package com.battaman.commuter;

import org.junit.Test;

import junit.framework.Assert;

public class MapQuestCommuteTimeRetrieverTest {

	@Test
	public void testRetrieveCommuteTime() {
		MockHttpRequester mhr = new MockHttpRequester();
		MapQuestCommuteTimeRetriever retriever = new MapQuestCommuteTimeRetriever(mhr);
		int response = retriever.retrieveCommuteTime("125 Main St. Denver, CO", "456 County Rd.");
		Assert.assertEquals(1437, response);
		Assert.assertEquals("http://www.mapquestapi.com/directions/v2/route?key=y8AF3zlYF43HC7HgcPWGlU5aMud9MhEF&from=125+Main+St.+Denver%2C+CO&to=456+County+Rd.", 
				mhr.getRequestedUrl());
	}
	
	@Test
	public void testIOException() {
		MockHttpRequester mhr = new MockHttpRequester();
		MapQuestCommuteTimeRetriever retriever = new MapQuestCommuteTimeRetriever(mhr);
		int response = retriever.retrieveCommuteTime("IOException", "456 County Rd.");
		Assert.assertEquals(-1, response);
	}
}
