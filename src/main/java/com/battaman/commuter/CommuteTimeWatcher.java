package com.battaman.commuter;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;
import com.battaman.commuter.listener.NotificationListener;

public class CommuteTimeWatcher extends TimerTask {

	private final CommuteTimeRetriever commuteTimeRetriever;
	private final Calendar desiredArrivalTime;
	private final int leadTimeInMinutes;
	private final List<NotificationListener> listeners;
	private final String fromAddress;
	private final String toAddress;
	
	public CommuteTimeWatcher(CommuteTimeRetriever commuteTimeRetriever, 
			Calendar desiredArrivalTime, int leadTimeInMinutes, List<NotificationListener> listeners,
			String fromAddress, String toAddress) {
		this.commuteTimeRetriever = commuteTimeRetriever;
		this.desiredArrivalTime = desiredArrivalTime;
		this.leadTimeInMinutes = leadTimeInMinutes;
		this.listeners = listeners;
		this.fromAddress = fromAddress;
		this.toAddress = toAddress;
	}

	@Override
	public void run() {
		Calendar currentArrivalTime = getCurrentArrivalTime();
		if(currentArrivalTime.compareTo(desiredArrivalTime) < 0) {
			long extraTimeInMillis = desiredArrivalTime.getTimeInMillis() - currentArrivalTime.getTimeInMillis();
			for(NotificationListener listener : listeners) {
				listener.info("Not time to leave yet. " + extraTimeInMillis / 1000 + "seconds to go.\n");
			}
		}
		else {
			for(NotificationListener listener : listeners) {
				listener.alert("Time to leave!!");
			}
			System.exit(0);
		}
	}
	
	private Calendar getCurrentArrivalTime() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
    	int currentCommuteTimeInSeconds = commuteTimeRetriever.retrieveCommuteTime(fromAddress, toAddress);
    	cal.add(Calendar.SECOND, currentCommuteTimeInSeconds);
    	cal.add(Calendar.MINUTE, leadTimeInMinutes);
    	System.out.println("Current arrival time: " + cal.getTime().toString());
    	return cal;
    }
}
