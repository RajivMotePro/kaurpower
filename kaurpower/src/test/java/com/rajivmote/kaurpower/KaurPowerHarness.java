package com.rajivmote.kaurpower;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

public class KaurPowerHarness {

	public static void main(String[] args) {
		if (args != null && args.length == 4) {
			try {
				// TODO Configure by Spring
				TwitterTemplate twitter = new TwitterTemplate(args[0], args[1], args[2], args[3]);
				TweetGrabber runner = new TweetGrabber();
				runner.setTwitterTemplate(twitter);
				ImageCreator imageCreator = new ImageCreator(1);
				TweetPoster tweetPoster = new TweetPoster(twitter);
				PoemWriter poemWriter = new PoemWriter();
				// 0. Retrieve "last Tweet responded to" from store
				// TODO
				// 1. Get app-triggering Tweets and their referents
				Map<Tweet, Tweet> targetsByMention = new HashMap<Tweet, Tweet>();
				List<Tweet> mentions = runner.pollForTweets(targetsByMention);
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
					ByteArrayResource imageResource = new ByteArrayResource(imageStream.toByteArray(), imageFile.getName());
					tweetPoster.postImageReply(mention.getId(), "Here's your #kaurpower meme.", imageResource);
					System.out.println(String.format("Posted response to %s's Tweet, ID = %d", 
							mention.getFromUser(), mention.getId()));
					// 5. Update "last Tweet responded to" store
				}
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
		} else {
			System.err.println("Usage: consumerKey consumerSecret accessKey accessKeySecret");
		}
	}

}
