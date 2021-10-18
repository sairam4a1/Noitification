package com.notification.demo.controller;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.notification.demo.model.EmailRequest;
import com.notification.demo.model.NotificationRequest;
import com.notification.demo.model.SmsRequest;
import com.notification.demo.queue.NotificationPublisher;
import com.notification.demo.service.NotificationService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class NotificationController {

	private final static Logger logger = LoggerFactory.getLogger(NotificationController.class);
	@Autowired
	private NotificationService notificationService;

	@Autowired
	private NotificationPublisher notificationPublisher;

	@Autowired
	private JavaMailSender sender;

	@PostMapping("/sms")
	public String sendSms(@Valid @RequestBody SmsRequest smsRequest) {
		notificationService.sendSms(smsRequest);
		return "Your SMS successfully sent ";
	}

	@PostMapping("/email")
	public String sendEmail(@Valid @RequestBody EmailRequest emailRequest) {
		notificationService.sendEmail(emailRequest);
		return "your Email successfully sent";
	}

	@PostMapping("/emailSample")
	public String sendEmailSample() throws MessagingException {
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true,"UTF-8") {
			@Override
			public void addInline(String contentId, Resource resource) throws MessagingException {
				Assert.notNull(resource, "Resource must not be null");
				String contentType = this.getFileTypeMap().getContentType(resource.getFilename());
				contentType = contentType.replace("x-png", "png");
				this.addInline(contentId, resource, contentType);

			}

		};
		try {
			helper.setTo("lakshmivagicharla123@gmail.com");
			helper.setSubject("Testing email inline message");
			helper.setText("<html><body><img src='cid:decorative-krishna-flute-wall-hanger-500x500.jpg'></body></html>",
					true);
			File file = new File("c:/Users/sairam/Pictures/decorative-krishna-flute-wall-hanger-500x500.jpg");
			helper.addInline("cid:decorative-krishna-flute-wall-hanger-500x500.jpg", file);
			helper.addAttachment("decorative-krishna-flute-wall-hanger-500x500.jpg", file);
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		sender.send(message);
		return "your Email successfully sent";
	}

	@PostMapping("/emailPublisher")
	public EmailRequest emailPublisher(@Valid @RequestBody EmailRequest emailRequest) {
		EmailRequest publishEvent = notificationPublisher.publishEvent(emailRequest);
		return publishEvent;
	}

	@PostMapping("/notificationPublisher")
	public ResponseEntity<String> notificationPublisher(@Valid @RequestBody NotificationRequest notificationRequest) {
		if (notificationRequest.isSubscribeNotification()) {
			notificationPublisher.publishNotification(notificationRequest);
			logger.info("Notification Successfully Published");
			return new ResponseEntity<String>("Notification submitted Successfully", HttpStatus.OK);
		}
		logger.error("Please Subscribe for Notification");
		return new ResponseEntity<String>("Please Subscribe for Notification ", HttpStatus.BAD_REQUEST);
	}
}
