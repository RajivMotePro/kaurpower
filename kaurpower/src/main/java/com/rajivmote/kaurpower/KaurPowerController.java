package com.rajivmote.kaurpower;

import org.springframework.social.twitter.api.Tweet;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KaurPowerController {
	
	@RequestMapping("/")
	public String ping() {
		return "Pong from KaurPowerController";
	}
	
	@RequestMapping("/nextTweets")
	public Tweet[] getNextTweets(long sinceId) {
		return null;
	}
	
	@RequestMapping("/getPoemLines")
	public String[] getPoemLines(Tweet tweet) {
		return null;
	}

}
