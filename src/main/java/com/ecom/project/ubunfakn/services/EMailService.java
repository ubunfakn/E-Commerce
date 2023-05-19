package com.ecom.project.ubunfakn.services;

import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

import org.springframework.stereotype.Service;

@Service
public class EMailService {
    
    public static void sendEmail(String message, String subject, String to, String from) throws Exception
    {
        String host="smtp.googlemail.com";
        Properties properties=System.getProperties();
        properties.put("mail.smtp.host",host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication("ankitnashine12@gmail.com", "olpnndkbwwyqasur");
            }
        });
        session.setDebug(true);
        
        MimeMessage mimeMessage=new MimeMessage(session);
        mimeMessage.setFrom(from);
        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        mimeMessage.setSubject(subject);
        mimeMessage.setText(message);

        Transport.send(mimeMessage);
        System.out.println("Message send successfully");
    }
}
