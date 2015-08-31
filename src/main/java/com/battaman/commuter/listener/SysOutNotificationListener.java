package com.battaman.commuter.listener;

public class SysOutNotificationListener implements NotificationListener {

	public void alert(String message) {
		System.out.print("NOTIFICATION ALERT: " + message);
	}

	public void info(String message) {
		System.out.print("Notification Info: " + message);
	}
}
