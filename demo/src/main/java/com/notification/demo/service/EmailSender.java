package com.notification.demo.service;

import com.notification.demo.model.EmailRequest;

public interface EmailSender {
	public void sendEmail(EmailRequest emailRequest);
}
