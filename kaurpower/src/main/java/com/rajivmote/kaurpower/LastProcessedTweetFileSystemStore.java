package com.rajivmote.kaurpower;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.WritableResource;

public class LastProcessedTweetFileSystemStore implements LastProcessedTweetStore {
	private static Logger LOG = Logger.getLogger(LastProcessedTweetFileSystemStore.class.getName());
	private WritableResource tweetStoreResource;
	
	public LastProcessedTweetFileSystemStore(File tweetStoreFile) {
		this.tweetStoreResource = new FileSystemResource(tweetStoreFile);
	}

	public long loadLastProcessedTweet() {
		if (tweetStoreResource.exists()) {
			DataInputStream in = null;
			try {
				in = new DataInputStream(tweetStoreResource.getInputStream());
				Long lastTweetId = in.readLong();
				if (lastTweetId != null) {
					LOG.info(String.format("Retrieved last Tweet ID = %d", lastTweetId));
					return lastTweetId.longValue();
				}
			} catch (IOException e) {
				LOG.warning(e.toString());
			} catch (NumberFormatException e) {
				LOG.warning(e.toString());
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						LOG.warning(e.toString());
					}
				}
			}
		}
		return 0;
	}

	public void saveLastProcessedTweet(long lastTweetId) {
		DataOutputStream out = null;
		try {
			out = new DataOutputStream(tweetStoreResource.getOutputStream());
			out.writeLong(lastTweetId);
			LOG.info(String.format("Wrote last Tweet ID = %d", lastTweetId));
		} catch (IOException e) {
			LOG.warning(e.toString());
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				LOG.warning(e.toString());
			}
		}

	}

}
