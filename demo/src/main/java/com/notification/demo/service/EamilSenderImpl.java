package com.notification.demo.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.notification.demo.model.EmailRequest;
import com.notification.demo.queue.NotificationPublisher;

@Component
public class EamilSenderImpl implements EmailSender {
	private final static Logger logger = LoggerFactory.getLogger(EamilSenderImpl.class);

	@Autowired
	private JavaMailSender sender;

	@Override
	public void sendEmail(EmailRequest emailRequest) {
		logger.info("Enter into sendEmail()" + emailRequest);
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		try {
			helper.setTo(emailRequest.getToAddress());
			helper.setSubject(emailRequest.getSubject());
			helper.setText(emailRequest.getBody());
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		sender.send(message);
		logger.info("email send to " + emailRequest.getToAddress());

	}
}
