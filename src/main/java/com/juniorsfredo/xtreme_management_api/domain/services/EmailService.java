package com.juniorsfredo.xtreme_management_api.domain.services;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {

    void sendEmail(String to, String subject, String body);
}
