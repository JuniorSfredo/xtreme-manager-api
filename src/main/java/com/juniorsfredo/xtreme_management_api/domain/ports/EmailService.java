package com.juniorsfredo.xtreme_management_api.domain.ports;

public interface EmailService {

    void sendEmail(String to, String subject, String body);
}
