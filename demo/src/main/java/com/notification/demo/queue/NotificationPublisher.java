package com.notification.demo.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.notification.demo.model.EmailRequest;
import com.notification.demo.model.NotificationRequest;

@Component
@SuppressWarnings("deprecation")
@EnableBinding(Source.class)
public class NotificationPublisher {
	private final static Logger logger = LoggerFactory.getLogger(NotificationPublisher.class);

	@Autowired
	private MessageChannel output;

	public EmailRequest publishEvent(EmailRequest emailRequest) {
		output.send(MessageBuilder.withPayload(emailRequest).build());
		return emailRequest;
	}

	public NotificationRequest publishNotification(NotificationRequest notificationRequest) {
		logger.info("Enter into the publishNotification()" + notificationRequest);
		output.send(MessageBuilder.withPayload(notificationRequest).build());
		logger.info("Publish payload : " + notificationRequest);
		return notificationRequest;
	}
}
