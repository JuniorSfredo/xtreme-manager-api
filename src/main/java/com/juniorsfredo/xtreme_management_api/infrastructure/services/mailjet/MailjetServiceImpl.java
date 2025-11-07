package com.juniorsfredo.xtreme_management_api.infrastructure.services.mailjet;

import com.juniorsfredo.xtreme_management_api.domain.services.EmailService;
import com.juniorsfredo.xtreme_management_api.infrastructure.exceptions.ExternalServiceException;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.resource.Emailv31;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

public class MailjetServiceImpl implements EmailService {

    @Value("${MAILJET_PUB_KEY}")
    private String mailjetPublicKey;

    @Value("${MAILJET_PV_KEY}")
    private String mailjetPrivateKey;

    @Value("${MAILJET_SENDER}")
    private String senderEmail;

    @Override
    public void sendEmail(String to, String subject, String body) {
        try {
            MailjetClient client = new MailjetClient(mailjetPublicKey, mailjetPrivateKey);

            MailjetRequest request = new MailjetRequest(Emailv31.resource)
                    .property(Emailv31.MESSAGES, new JSONArray()
                            .put(new JSONObject()
                                    .put(Emailv31.Message.FROM, new JSONObject()
                                            .put("Email", senderEmail)
                                            .put("Name", "Me"))
                                    .put(Emailv31.Message.TO, new JSONArray()
                                            .put(new JSONObject()
                                                    .put("Email", to)
                                                    .put("Name", "Recipient")))
                                    .put(Emailv31.Message.SUBJECT, subject)
                                    .put(Emailv31.Message.TEXTPART, body)
                                    .put(Emailv31.Message.HTMLPART, body)
                            ));

            MailjetResponse response = client.post(request);
            System.out.println(response.getStatus());
            System.out.println(response.getData());

        } catch (MailjetException e) {
            throw new ExternalServiceException("Error occurred while sending email.");
        }
    }
}
