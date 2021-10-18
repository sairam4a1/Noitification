package com.notification.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;

public class SmsRequest {

    @NotBlank(message = "phoneNumber hould not empty")
    private final String phoneNumber; // destination

    @NotBlank(message = "Message should not empty")
    private final String message;

    public SmsRequest(@JsonProperty("phoneNumber") String phoneNumber,
                      @JsonProperty("message") String message) {
        this.phoneNumber = phoneNumber;
        this.message = message;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "SmsRequest{" +
                "phoneNumber= ..." + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

}
