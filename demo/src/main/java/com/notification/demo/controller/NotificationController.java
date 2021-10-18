package com.notification.demo.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.notification.demo.model.NotificationRequest;
import com.notification.demo.queue.NotificationPublisher;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class NotificationController {

	private final static Logger logger = LoggerFactory.getLogger(NotificationController.class);

	@Autowired
	private NotificationPublisher notificationPublisher;



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
