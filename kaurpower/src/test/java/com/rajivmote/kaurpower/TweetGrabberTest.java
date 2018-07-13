package com.rajivmote.kaurpower;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.social.twitter.api.Tweet;

public class TweetGrabberTest {
	private TweetGrabber tweetGrabber;

	@Before
	public void setUp() throws Exception {
		tweetGrabber = new TweetGrabber();
	}

	@After
	public void tearDown() throws Exception {
		tweetGrabber = null;
	}

	@Test
	public void testIsTriggeredTweet() {
		@SuppressWarnings("deprecation")
		Tweet triggeredTweet = new Tweet(0, "@RajivMote " + TweetGrabber.TRIGGER + " to ya.", null, null, null, null, 0, null, null);
		assertFalse(tweetGrabber.isTriggeredTweet(triggeredTweet));
		triggeredTweet.setInReplyToStatusId(500L);
		assertTrue(tweetGrabber.isTriggeredTweet(triggeredTweet));
		@SuppressWarnings("deprecation")
		Tweet untriggeredTweet = new Tweet(0, "@RajivMote Hello sailor!", null, null, null, null, 0, null, null);
		assertFalse(tweetGrabber.isTriggeredTweet(untriggeredTweet));
	}

}
