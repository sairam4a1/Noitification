package com.notification.demo.model;

import javax.validation.constraints.NotNull;

public class NotificationRequest {
	@NotNull(message = "subscribeNotification should not be empty")
	private boolean subscribeNotification;
	@NotNull(message = "notificationType should not be empty")
	private String notificationType;
	
	private EmailRequest emailRequest;
	
	private SmsRequest smsRequest;

	public NotificationRequest() {
	}

	public NotificationRequest(boolean subscribeNotification, String notificationType, EmailRequest emailRequest,
			SmsRequest smsRequest) {
		this.subscribeNotification = subscribeNotification;
		this.notificationType = notificationType;
		this.emailRequest = emailRequest;
		this.smsRequest = smsRequest;
	}

	public EmailRequest getEmailRequest() {
		return emailRequest;
	}

	public void setEmailRequest(EmailRequest emailRequest) {
		this.emailRequest = emailRequest;
	}

	public SmsRequest getSmsRequest() {
		return smsRequest;
	}

	public void setSmsRequest(SmsRequest smsRequest) {
		this.smsRequest = smsRequest;
	}

	public boolean isSubscribeNotification() {
		return subscribeNotification;
	}

	public void setSubscribeNotification(boolean subscribeNotification) {
		this.subscribeNotification = subscribeNotification;
	}

	public String getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}

	@Override
	public String toString() {
		return "NotificationRequest [subscribeNotification=" + subscribeNotification + ", notificationType="
				+ notificationType + ", emailRequest=" + emailRequest + ", smsRequest=" + smsRequest + "]";
	}
}
