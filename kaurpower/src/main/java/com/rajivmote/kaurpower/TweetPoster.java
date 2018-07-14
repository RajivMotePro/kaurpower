package com.rajivmote.kaurpower;

import org.springframework.core.io.Resource;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TweetData;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

public class TweetPoster {
	
	private TwitterTemplate twitter;
	
	public TweetPoster(TwitterTemplate twitter) {
		this.twitter = twitter;
	}
	
	public Tweet postImageReply(long replyToId, String message, Resource imageResource) {
		TweetData tweetData = new TweetData(message).withMedia(imageResource);
		tweetData = tweetData.inReplyToStatus(replyToId);
		return twitter.timelineOperations().updateStatus(tweetData);
	}

}
