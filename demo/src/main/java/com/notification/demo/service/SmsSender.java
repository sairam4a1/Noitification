package com.notification.demo.service;

import com.notification.demo.model.SmsRequest;

public interface SmsSender {

	void sendSms(SmsRequest smsRequest);

}