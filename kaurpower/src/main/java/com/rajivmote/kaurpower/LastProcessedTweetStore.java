package com.rajivmote.kaurpower;

public interface LastProcessedTweetStore {
	
	public long loadLastProcessedTweet();
	
	public void saveLastProcessedTweet(long lastTweetId);

}
