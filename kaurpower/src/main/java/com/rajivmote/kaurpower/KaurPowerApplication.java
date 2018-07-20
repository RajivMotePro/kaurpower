package com.rajivmote.kaurpower;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.social.twitter.api.Tweet;

public class KaurPowerApplication {
	private static final Logger LOG = Logger.getLogger(KaurPowerApplication.class.getName());
	private static TweetGrabber tweetGrabber;
	private static PoemWriter poemWriter = new PoemWriter();
	private static ImageCreator imageCreator = new ImageCreator();
	
	public static void main(String[] args) {
		// 1. Get @ replies for "kaurpower"
		Map<Tweet, Tweet> targetsByMention = new HashMap<Tweet, Tweet>();
		List<Tweet> tweets = tweetGrabber.pollForTweets(targetsByMention, 0);
		LOG.info(String.format("Retrieved %d triggered Tweets", tweets.size()));
		
		// 2. Create poem per Tweet
		for (Tweet tweet : tweets) {
			String[] poemLines = poemWriter.writePoem(tweet.getText());
			if (poemLines != null && poemLines.length > 0) {
				
				// 4. Select a random image template
				// TODO
				
				// 4. Generate image per Tweet
				InputStream rawImageStream = null;
				// TODO
				// TODO BufferedImage image = imageCreator.createImage(rawImageStream, poemLines);
				
				// 5. Post image as a reply Tweet
				// TODO TweetData replyData = generateReplyData(tweet, poemLines);
			}
		}		
	}
	
}
