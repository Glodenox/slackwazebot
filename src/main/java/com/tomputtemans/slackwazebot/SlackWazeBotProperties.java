package com.tomputtemans.slackwazebot;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Properties wrapper with some helpful constants
 * 
 * @throws IOException
 *             if the properties file could not be found or read
 */
public class SlackWazeBotProperties {
	public static final String SLACK_TOKEN = "slack-token";
	public static final String SLACK_CHANNEL = "slack-channel";

	private final Properties properties = new Properties();


	public SlackWazeBotProperties() throws IOException {
		InputStream inStream = new FileInputStream("./WazeBot.properties");
		properties.load(inStream);
		inStream.close();
	}

	/**
	 * Retrieve a certain value from the properties file
	 * 
	 * @param key
	 *            The key of the value to retrieve
	 * @return The value configured for the given key
	 */
	public String getProperty(String key) {
		return properties.getProperty(key);
	}
}
