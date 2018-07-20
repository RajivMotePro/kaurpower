package com.rajivmote.kaurpower;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class LastProcessedTweetFileSystemStoreTest {
	private LastProcessedTweetFileSystemStore store;

	@Before
	public void setUp() throws Exception {
		Resource resource = new ClassPathResource("TestLastTweetStore.txt");
		store = new LastProcessedTweetFileSystemStore(resource.getFile());
	}

	@After
	public void tearDown() throws Exception {
		store = null;
	}

	@Test
	public void testSaveLoadLastProcessedTweet() {
		long lastProcessedId = 555L;
		store.saveLastProcessedTweet(lastProcessedId);
		assertEquals(lastProcessedId, store.loadLastProcessedTweet());
		lastProcessedId = 777L;
		store.saveLastProcessedTweet(lastProcessedId);
		assertEquals(lastProcessedId, store.loadLastProcessedTweet());
	}

}
