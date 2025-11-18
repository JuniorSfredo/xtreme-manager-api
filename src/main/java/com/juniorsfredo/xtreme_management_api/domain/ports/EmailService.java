package com.juniorsfredo.xtreme_management_api.domain.ports;

import org.springframework.stereotype.Service;

public interface EmailService {

    void sendEmail(String to, String subject, String body);
}
