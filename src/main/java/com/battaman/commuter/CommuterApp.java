package com.battaman.commuter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;

import com.battaman.commuter.http.HttpRequester;
import com.battaman.commuter.listener.NotificationListener;
import com.battaman.commuter.listener.SysOutNotificationListener;

public class CommuterApp 
{	
	private static final int WAIT_TIME = 5000;
	private static final int LEAD_TIME_IN_MINUTES = 5;
	
    public static void main( String[] args ) throws IOException, InterruptedException {

    	if(args.length != 3) {
    		throw new IllegalArgumentException("Exactly three arguments are required");
    	}
    	
    	// TODO: Lots of assumptions here.  Add checks to confirm valid inputs.
    	String inputTime = args[0];
    	String fromAddress = args[1];
    	String toAddress = args[2];
    	
    	CommuteTimeRetriever commuteTimeRetriever = new MapQuestCommuteTimeRetriever(new HttpRequester());

    	final Calendar desiredArrivalTime = getArrivalTimeInMs(inputTime);
    	System.out.println("Desired Arrival:" + desiredArrivalTime.getTime().toString());
    	
    	List<NotificationListener> listeners = new ArrayList<NotificationListener>();
    	listeners.add(new SysOutNotificationListener());
    	
    	// TODO: Lead time in minutes is currently assumed to be 5 minutes.  This should come from an input param.
    	CommuteTimeWatcher ctw = new CommuteTimeWatcher(commuteTimeRetriever, desiredArrivalTime, LEAD_TIME_IN_MINUTES, listeners, fromAddress, toAddress);
    	
    	Timer timer = new Timer();
    	timer.scheduleAtFixedRate(ctw, 0, WAIT_TIME);
    }
    
    private static Calendar getArrivalTimeInMs(String desiredArrivalTime) {
    	// TODO: Major assumption about the format and contents of the requested input time.  Add more checks.
    	String[] timeComponents = desiredArrivalTime.split(":");
    	
    	// Assuming arrival time is for today's date
    	Calendar cal = Calendar.getInstance();
    	cal.setTimeInMillis(System.currentTimeMillis());
    	cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeComponents[0]));
    	cal.set(Calendar.MINUTE, Integer.parseInt(timeComponents[1]));
    	cal.set(Calendar.SECOND, 0);
    	cal.set(Calendar.MILLISECOND, 0);

    	return cal;
    }
}
