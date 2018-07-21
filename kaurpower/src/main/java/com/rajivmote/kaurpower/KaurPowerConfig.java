package com.rajivmote.kaurpower;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

@Configuration
public class KaurPowerConfig {
	
	@Value("${store.tweet.last.filename}")
	private String lastTweetStoreFilename;
	
	@Value("${twitter.consumerKey}")
	private String consumerKey;
	
	@Value("${twitter.consumerSecret}")
	private String consumerSecret;
	
	@Value("${twitter.accessToken}")
	private String accessToken;
	
	@Value("${twitter.accessTokenSecret}")
	private String accessTokenSecret;
	
	@Bean
	Resource tweetStoreResource() {
		return new ClassPathResource(lastTweetStoreFilename);
	}
	
	@Bean
	public LastProcessedTweetStore tweetStore() {
		try {
			return new LastProcessedTweetFileSystemStore(tweetStoreResource().getFile());
		} catch (IOException e) {
			return null;
		}
	}
	
	@Bean
	TwitterTemplate twitter() {
		return new TwitterTemplate(consumerKey, consumerSecret, accessToken, accessTokenSecret);
	}
	
	@Bean
	public TweetGrabber tweetGrabber() {
		TweetGrabber tweetGrabber = new TweetGrabber();
		tweetGrabber.setTwitterTemplate(twitter());
		return tweetGrabber;
	}
	
	@Bean
	public PoemWriter poemWriter() {
		return new PoemWriter();
	}
	
	@Bean
	public ImageCreator imageCreator() {
		return new ImageCreator(1);
	}
	
	@Bean
	TweetPoster tweetPoster() {
		return new TweetPoster(twitter());
	}	

}
