package com.rajivmote.kaurpower;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.social.twitter.api.Tweet;

@SpringBootApplication
public class KaurPowerApplication implements CommandLineRunner {
	
	@Autowired
	private LastProcessedTweetStore tweetStore;
	@Autowired
	private TweetGrabber tweetGrabber;
	@Autowired
	private PoemWriter poemWriter;
	@Autowired
	private ImageCreator imageCreator;
	@Autowired
	private TweetPoster tweetPoster;
	
	public static void main(String[] args) {
		SpringApplication.run(KaurPowerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (args.length > 0 && "clearlasttweeted".equalsIgnoreCase(args[0])) {
			clearLastTweetedId();
			return;
		}
		boolean suppressTweets = Boolean.parseBoolean(System.getProperty("suppressTweets", "false"));
		// 0. Retrieve "last Tweet responded to" from store
		long lastProcessedTweetId = 0L;
		lastProcessedTweetId = tweetStore.loadLastProcessedTweet();
		System.out.println(String.format("Loaded last processed Tweet ID = %d", lastProcessedTweetId));
		// 1. Get app-triggering Tweets and their referents
		Map<Tweet, Tweet> targetsByMention = new HashMap<Tweet, Tweet>();
		List<Tweet> mentions = tweetGrabber.pollForTweets(targetsByMention, lastProcessedTweetId);
		int imageIndex = 1;
		for (Tweet mention : mentions) {
			Tweet target = targetsByMention.get(mention);
			System.out.println(String.format("@%s says: %s\n\tin reply to @%s: %s\n", 
					mention.getFromUser(), mention.getUnmodifiedText(), 
					target.getFromUser(), target.getUnmodifiedText()));
			System.out.println("Extra data:");
			Map<String, Object> extraData = target.getEntities().getExtraData();
			for (String key : extraData.keySet()) {
				Object value = extraData.get(key);
				System.out.println(String.format("%s = %s", key, value));
			}
			// 2. Generate a "poem" from the Tweet text
			String[] poemLines = poemWriter.writePoem(target.getUnmodifiedText());
			for (String line : poemLines) {
				System.out.println(line);
			}
			System.out.println();
			// 3. Create image with poem overlay
			BufferedImage image = imageCreator.createImage(poemLines);
			File imageFile = 
					new File(String.format("C:/Users/Rajiv/Pictures/kaurpower_image_%d.jpg", imageIndex++));
			ImageIO.write(image, "jpg", imageFile);
			System.out.println("Wrote: " + imageFile.getAbsolutePath());
			ByteArrayOutputStream imageStream = new ByteArrayOutputStream();
			ImageIO.write(image,  "jpg", imageStream);
			// 4. Post response Tweet
			if (suppressTweets) {
				System.out.println(String.format("SUPPRESSED response to %s's Tweet, ID = %d", 
						mention.getFromUser(), mention.getId()));
			} else {
				ByteArrayResource imageResource = new ByteArrayResource(imageStream.toByteArray(), imageFile.getName());
				tweetPoster.postImageReply(mention.getId(), "Here's your #kaurpower meme.", imageResource);
				System.out.println(String.format("Posted response to %s's Tweet, ID = %d", 
						mention.getFromUser(), mention.getId()));
			}
			// 5. Update "last Tweet responded to" store
			if (lastProcessedTweetId < mention.getId()) {
				lastProcessedTweetId = mention.getId();
				System.out.println(String.format("Saving last processed Tweet ID = %d", lastProcessedTweetId));
				tweetStore.saveLastProcessedTweet(lastProcessedTweetId);
			}
		}
	}
	
	private void clearLastTweetedId() {
		System.out.println("Clearing last processed Tweet ID");
		tweetStore.saveLastProcessedTweet(0L);
	}
	
}
