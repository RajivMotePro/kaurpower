package com.rajivmote.kaurpower;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TweetData;

public class KaurPowerApp {
	private static final Logger LOG = Logger.getLogger(KaurPowerApp.class.getName());
	private TweetGrabber tweetGrabber;
	private PoemWriter poemWriter = new PoemWriter();
	
	public void runCycle() {
		// 1. Get @ replies for "kaurpower"
		List<Tweet> tweets = tweetGrabber.pollForTweets();
		LOG.info(String.format("Retrieved %d triggered Tweets", tweets.size()));
		
		// 2. Create poem per Tweet
		for (Tweet tweet : tweets) {
			String[] poemLines = poemWriter.writePoem(tweet.getText());
			if (poemLines != null && poemLines.length > 0) {
				TweetData replyData = generateReplyData(tweet, poemLines);
				
				// 4. Post image per Tweet
			}
		}		
	}
	
	public TweetData generateReplyData(Tweet tweet, String[] poemLines) {
		return null;
	}
	
}
