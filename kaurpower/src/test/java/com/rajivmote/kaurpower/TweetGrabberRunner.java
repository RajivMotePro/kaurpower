package com.rajivmote.kaurpower;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

public class TweetGrabberRunner extends TweetGrabber {
	
	public TweetGrabberRunner(String consumerKey, String consumerSecret, String accessToken, String accessTokenSecret) {
		super();
		setTwitterTemplate(new TwitterTemplate(consumerKey, consumerSecret, accessToken, accessTokenSecret));
	}
	
	@Override
	protected boolean isTriggeredTweet(Tweet tweet) {
		return tweet.getInReplyToStatusId() != null;
	}

	public static void main(String[] args) {
		if (args != null && args.length == 4) {
			TweetGrabberRunner runner = new TweetGrabberRunner(args[0], args[1], args[2], args[3]);
			Map<Tweet, Tweet> targetsByMention = new HashMap<Tweet, Tweet>();
			List<Tweet> mentions = runner.pollForTweets(targetsByMention, 0L);
			for (Tweet mention : mentions) {
				Tweet target = targetsByMention.get(mention);
				System.out.println(String.format("@%s says: %s\n\tin reply to @%s: %s\n", 
						mention.getFromUser(), mention.getUnmodifiedText(), 
						target.getFromUser(), target.getUnmodifiedText()));
			}
		} else {
			System.err.println("Usage: consumerKey consumerSecret accessKey accessKeySecret");
		}
	}

}
