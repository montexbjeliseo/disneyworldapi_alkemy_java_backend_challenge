package com.mtx.disneyworld.service;

import com.sendgrid.*;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendGridEmailService implements EmailService {
    
    @Autowired
    private SendGrid sendGridClient;

    @Override
    public void sendText(String from, String to, String subject, String body) {
        //Response response = 
        sendEmail(from, to, subject, new Content("text/plain", body));
        //System.out.println("Status Code: " + response.getStatusCode() + ", Body: " + response.getBody() + ", Headers: " + response.getHeaders());
    }

    @Override
    public void sendHTML(String from, String to, String subject, String body) {
        //Response response = 
        sendEmail(from, to, subject, new Content("text/html", body));
        //System.out.println("Status Code: " + response.getStatusCode() + ", Body: " + response.getBody() + ", Headers: " + response.getHeaders());
    }

    private Response sendEmail(String from, String to, String subject, Content content) {
        Mail mail = new Mail(new Email(from), subject, new Email(to), content);
        mail.setReplyTo(new Email(from));
        Request request = new Request();
        Response response = null;
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            this.sendGridClient.api(request);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return response;
    }
}