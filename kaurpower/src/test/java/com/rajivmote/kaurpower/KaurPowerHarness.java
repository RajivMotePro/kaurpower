package com.rajivmote.kaurpower;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

public class KaurPowerHarness {

	public static void main(String[] args) {
		if (args != null && args.length == 4) {
			try {
				TweetGrabber runner = new TweetGrabber();
				runner.setTwitterTemplate(new TwitterTemplate(args[0], args[1], args[2], args[3]));
				ImageCreator imageCreator = new ImageCreator();
				PoemWriter poemWriter = new PoemWriter();
				Map<Tweet, Tweet> targetsByMention = new HashMap<Tweet, Tweet>();
				List<Tweet> mentions = runner.pollForTweets(targetsByMention);
				for (Tweet mention : mentions) {
					Tweet target = targetsByMention.get(mention);
					System.out.println(String.format("@%s says: %s\n\tin reply to @%s: %s\n", 
							mention.getFromUser(), mention.getUnmodifiedText(), 
							target.getFromUser(), target.getUnmodifiedText()));
					String[] poemLines = poemWriter.writePoem(target.getUnmodifiedText());
					for (String line : poemLines) {
						System.out.println(line);
					}
					System.out.println();
				}
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
		} else {
			System.err.println("Usage: consumerKey consumerSecret accessKey accessKeySecret");
		}
	}

}
