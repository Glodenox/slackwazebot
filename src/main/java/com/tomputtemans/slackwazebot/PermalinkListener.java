package com.tomputtemans.slackwazebot;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;
import com.ullink.slack.simpleslackapi.listeners.SlackMessagePostedListener;

public class PermalinkListener implements SlackMessagePostedListener {
	private static final Pattern LAYERS_PATTERN = Pattern.compile("layers=\\d+(&amp;)?");
	private static final Logger LOG = LoggerFactory.getLogger(PermalinkListener.class);
	
	private final String channel;
	
	public PermalinkListener(String channel) {
		this.channel = channel;
	}
	
	@Override
	public void onEvent(SlackMessagePosted event, SlackSession session) {
		LOG.debug("Message received: " + event.getMessageContent());
		
		if (channel.equals(event.getChannel().getName())) {
			Matcher matcher = LAYERS_PATTERN.matcher(event.getMessageContent());
			if (matcher.find()) {
				LOG.info("Sending notification message with replaced permalinks");
				// Respond with the cleaned-up message in blockquotes
				session.sendMessage(event.getChannel(), "Er werd een layers-parameter ontdekt in het vorige bericht. "
						+ "Als je WME Toolbox gebruikt kan je de SHIFT-toets gebruiken alvorens te kopiëren om enkel "
						+ "de essentie in de permalink te zetten.\nHieronder het 'opgeschoond' bericht:\n&gt;"
						+ matcher.replaceAll("").replaceAll("\n", "\n&gt;"), null);
			}
		}
	}
}
