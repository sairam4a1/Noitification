package com.notification.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;

public class EmailRequest {

	@NotBlank(message = "Email to Address should not empty")
	private String toAddress; // destination
	
	@NotBlank(message = "Email subject should not empty")
	private String subject;

	private String body;

	public EmailRequest(@JsonProperty("toAddress") String phoneNumber, @JsonProperty("subject") String message,
			@JsonProperty("body") String body) {
		this.toAddress = phoneNumber;
		this.subject = message;
		this.body = body;
	}

	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "EmailRequest [toAddress=" + toAddress + ", subject=" + subject + ", body=" + body + "]";
	}
}
