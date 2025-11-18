package com.juniorsfredo.xtreme_management_api.infrastructure.external.mailjet;

import com.juniorsfredo.xtreme_management_api.domain.ports.EmailService;
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
            MailjetClient client;
            MailjetRequest request;
            MailjetResponse response;
            client = new MailjetClient(mailjetPublicKey, mailjetPrivateKey);
            request = new MailjetRequest(Emailv31.resource)
                    .property(Emailv31.MESSAGES, new JSONArray()
                            .put(new JSONObject()
                                    .put(Emailv31.Message.FROM, new JSONObject()
                                            .put("Email", senderEmail)
                                            .put("Name", "Mailjet Pilot"))
                                    .put(Emailv31.Message.TO, new JSONArray()
                                            .put(new JSONObject()
                                                    .put("Email", to)
                                                    .put("Name", "passenger 1")))
                                    .put(Emailv31.Message.SUBJECT, "Your email flight plan!")
                                    .put(Emailv31.Message.TEXTPART, "Dear passenger 1, welcome to Mailjet! May the delivery force be with you!")
                                    .put(Emailv31.Message.HTMLPART, "<h3>Dear passenger 1, welcome to <a href=\"https://www.mailjet.com/\">Mailjet</a>!</h3><br />May the delivery force be with you!")));
            response = client.post(request);
            System.out.println(response.getStatus());
            System.out.println(response.getData());

        } catch (MailjetException e) {
            throw new ExternalServiceException("Error occurred while sending email.");
        }
    }
}
