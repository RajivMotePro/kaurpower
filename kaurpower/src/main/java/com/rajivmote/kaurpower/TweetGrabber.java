package com.rajivmote.kaurpower;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

public class TweetGrabber {
	private static final Logger LOG = Logger.getLogger(TweetGrabber.class.getName());
	static final String TRIGGER = "kaurpower";
	private TwitterTemplate twitterTemplate;
	
	protected boolean isTriggeredTweet(Tweet tweet) {
		return tweet.getInReplyToStatusId() != null && tweet.getText().toLowerCase().contains(TRIGGER);
	}
	
	public List<Tweet> pollForTweets(Map<Tweet, Tweet> targetsByMention) {
		List<Tweet> mentions = new ArrayList<Tweet>();
		List<Tweet> replies = twitterTemplate.timelineOperations().getMentions();
		LOG.info(String.format("Retrieved %d Twitter mentions", replies.size()));
		for (Tweet tweet : replies) {
			if (isTriggeredTweet(tweet)) {
				mentions.add(tweet);
				targetsByMention.put(
						tweet, 
						twitterTemplate.timelineOperations().getStatus(tweet.getInReplyToStatusId()));
			}
		}
		return mentions;
	}

	public TwitterTemplate getTwitterTemplate() {
		return twitterTemplate;
	}

	public void setTwitterTemplate(TwitterTemplate twitterTemplate) {
		this.twitterTemplate = twitterTemplate;
	}
}
