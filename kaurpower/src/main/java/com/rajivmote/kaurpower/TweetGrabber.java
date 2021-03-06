package com.rajivmote.kaurpower;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

public class TweetGrabber {
	private static final Logger LOG = Logger.getLogger(TweetGrabber.class.getName());
	private static final int TWEET_PAGE_SIZE = 200;
	static final String TRIGGER = "kaurpower";
	private TwitterTemplate twitterTemplate;
	
	protected boolean isTriggeredTweet(Tweet tweet) {
		LOG.fine(String.format("Testing tweet %d: %s", tweet.getId(), tweet.getUnmodifiedText()));
		return tweet.getInReplyToStatusId() != null 
				&& tweet.getUnmodifiedText().toLowerCase().contains(TRIGGER);
	}
	
	public List<Tweet> pollForTweets(Map<Tweet, Tweet> targetsByMention, long sinceId) {
		List<Tweet> mentions = new ArrayList<Tweet>();
		List<Tweet> replies = null;
		if (sinceId > 0) {
			replies = twitterTemplate.timelineOperations().getMentions(TWEET_PAGE_SIZE, sinceId, Long.MAX_VALUE - 1);
		} else {
			replies = twitterTemplate.timelineOperations().getMentions(TWEET_PAGE_SIZE);
		}
		LOG.fine(String.format("Retrieved %d Twitter mentions", replies.size()));
		for (Tweet reply : replies) {
			if (isTriggeredTweet(reply)) {
				mentions.add(reply);
				targetsByMention.put(
						reply, 
						twitterTemplate.timelineOperations().getStatus(reply.getInReplyToStatusId()));
			}
		}
		LOG.info(String.format("Found %d triggering Tweets", mentions.size()));
		return mentions;
	}

	public TwitterTemplate getTwitterTemplate() {
		return twitterTemplate;
	}

	public void setTwitterTemplate(TwitterTemplate twitterTemplate) {
		this.twitterTemplate = twitterTemplate;
	}
}
