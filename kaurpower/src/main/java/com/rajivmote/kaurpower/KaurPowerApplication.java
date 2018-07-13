package com.rajivmote.kaurpower;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TweetData;

@SpringBootApplication
public class KaurPowerApplication {
	private static final Logger LOG = Logger.getLogger(KaurPowerApplication.class.getName());
	private TweetGrabber tweetGrabber;
	private PoemWriter poemWriter = new PoemWriter();
	private ImageCreator imageCreator = new ImageCreator();
	
	public static void main(String[] args) {
		SpringApplication.run(KaurPowerApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner commandLineRunner(final ApplicationContext ctx) {
		return new CommandLineRunner() {
			
			public void run(String... args) throws Exception {
				System.out.println("Let's inspect the beans provided by Spring Boot:");
				
				String[] beanNames = ctx.getBeanDefinitionNames();
				Arrays.sort(beanNames);
				for (String beanName : beanNames) {
					System.out.println(beanName);
				}
			}
		};
	}
	
	public void runCycle() {
		// 1. Get @ replies for "kaurpower"
		List<Tweet> tweets = tweetGrabber.pollForTweets();
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
				TweetData replyData = generateReplyData(tweet, poemLines);
			}
		}		
	}
	
	protected TweetData generateReplyData(Tweet tweet, String[] poemLines) {
		return null;
	}
	
	protected Resource selectRandomImage() {
		return null;
	}
	
}
