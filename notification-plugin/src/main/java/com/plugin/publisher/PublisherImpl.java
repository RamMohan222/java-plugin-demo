package com.plugin.publisher;

public class PublisherImpl implements IPublisher {

	public boolean send(String message) {
		System.out.println("Notification sent with " + message);
		return false;
	}

}
