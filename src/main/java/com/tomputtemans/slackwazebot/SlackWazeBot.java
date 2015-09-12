package com.tomputtemans.slackwazebot;

import java.io.Console;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;

/**
 * Bot to automatically detect and propose better Waze editor URLs in Slack if
 * they contain the layers parameter
 * 
 * @author Tom Puttemans
 */
public class SlackWazeBot {

	private static final Logger LOG = LoggerFactory.getLogger(SlackWazeBot.class);

	public static void main(String[] args) {
		SlackSession session = null;

		try {
			SlackWazeBotProperties config = new SlackWazeBotProperties();
			session = SlackSessionFactory.createWebSocketSlackSession(config.getProperty(SlackWazeBotProperties.SLACK_TOKEN));

			// We need to attach our listener before connecting
			session.addMessagePostedListener(new PermalinkListener(config.getProperty(SlackWazeBotProperties.SLACK_CHANNEL)));

			// Open connection with Slack
			session.connect();

			// Keep thread running
			Console cons = System.console();
			while (!"exit".equalsIgnoreCase(cons.readLine())) {
				Thread.sleep(500);
			}
		} catch (IOException | InterruptedException e) {
			LOG.error("Exception occurred while running the bot", e);
		} finally {
			try {
				if (session != null) {
					session.disconnect();
				}
			} catch (IOException e) {
				LOG.error("Exception occurred while trying to disconnect", e);
			}
		}
	}
}
