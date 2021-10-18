package com.notification.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.notification.demo.configuration.TwilioConfiguration;
import com.notification.demo.model.SmsRequest;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;

@Service("twilio")
public class TwilioSmsSenderImpl implements SmsSender {

	private static final Logger LOGGER = LoggerFactory.getLogger(TwilioSmsSenderImpl.class);

	@Autowired
	private TwilioConfiguration twilioConfiguration;

	@Override
	public void sendSms(SmsRequest smsRequest) {
		PhoneNumber to = new PhoneNumber(smsRequest.getPhoneNumber());
		PhoneNumber from = new PhoneNumber(twilioConfiguration.getTrialNumber());
		String message = smsRequest.getMessage();
		MessageCreator creator = Message.creator(to, from, message);
		creator.create();
		LOGGER.info("Send sms " + smsRequest);

	}

}