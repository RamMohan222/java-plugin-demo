package com.plugin.publisher;

public class PublisherImpl implements IPublisher {

	@Override
	public boolean send(String message) {
		System.out.println("Email has sent with " + message);
		return true;
	}

}
