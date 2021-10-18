package com.notification.demo.queue;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;

import com.notification.demo.model.EmailRequest;
import com.notification.demo.model.NotificationRequest;
import com.notification.demo.model.SmsRequest;
import com.notification.demo.service.NotificationService;

@SuppressWarnings("deprecation")
@Component
@EnableBinding(Sink.class)
public class NotificationConsumer {
	private final static Logger logger = LoggerFactory.getLogger(NotificationConsumer.class);

	@Autowired
	private NotificationService notificationService;

	@StreamListener("input")
	public void consumeNotification(@Valid NotificationRequest notificationRequest) {

		if (notificationRequest.getNotificationType().equals("email")) {
			EmailRequest emailRequest = notificationRequest.getEmailRequest();
			notificationService.sendEmail(emailRequest);
			logger.info("Consume payload : " + notificationRequest.getEmailRequest());
		}
		if (notificationRequest.getNotificationType().equals("sms")) {
			SmsRequest smsRequest = notificationRequest.getSmsRequest();
			notificationService.sendSms(smsRequest);
			logger.info("Consume payload : " + notificationRequest.getSmsRequest());
		}
	}
}
