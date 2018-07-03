package com.rajivmote.kaurpower;

import java.util.List;

import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

public class TweetGrabberRunner extends TweetGrabber {
	
	public TweetGrabberRunner(String consumerKey, String consumerSecret, String accessToken, String accessTokenSecret) {
		super();
		setTwitterTemplate(new TwitterTemplate(consumerKey, consumerSecret, accessToken, accessTokenSecret));
	}
	
	@Override
	protected boolean isTriggeredTweet(Tweet tweet) {
		return true;
	}

	public static void main(String[] args) {
		if (args != null && args.length == 4) {
			TweetGrabberRunner runner = new TweetGrabberRunner(args[0], args[1], args[2], args[3]);
			List<Tweet> tweets = runner.pollForTweets();
			for (Tweet tweet : tweets) {
				System.out.println(tweet.getFromUser());
				System.out.println(tweet.getUnmodifiedText());
				System.out.println();
			}
		} else {
			System.err.println("Usage: consumerKey consumerSecret accessKey accessKeySecret");
		}

	}

}
