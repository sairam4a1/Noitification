package com.notification.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.notification.demo.model.EmailRequest;
import com.notification.demo.model.SmsRequest;

@Service
public class NotificationService {

	@Qualifier("twilio")
	@Autowired
	private SmsSender smsSender;

	@Autowired
	private EmailSender emailSender;

	public void sendSms(SmsRequest smsRequest) {
		smsSender.sendSms(smsRequest);
	}

	public void sendEmail(EmailRequest emailRequest) {
		emailSender.sendEmail(emailRequest);
	}
}
