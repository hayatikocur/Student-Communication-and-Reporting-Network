package com.example;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.io.InputStream;
import java.util.Properties;

public class SendGmail {

    private static final String PROPERTIES_FILE = "mail.properties";

    public static void sendPassword(String recipient, String newPassword) {
        String subject = "SCRN Password Reset";
        String message = "Your new temporary password is: " + newPassword + "\n\nPlease log in and change it immediately.";

        sendEmail(recipient, subject, message);
    }

    public static void sendEmail(String recipient) {
        String subject = "SCRN Email Verification";
        String message = "Your email has been successfully registered. Welcome to the platform!";

        sendEmail(recipient, subject, message);
    }

    public static void sendEmail(String recipient, String subject, String messageText) {
        try {
            Properties mailProps = new Properties();
            try (InputStream input = SendGmail.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
                if (input == null) {
                    throw new IllegalStateException("mail.properties not found");
                }
                mailProps.load(input);
            }

            String senderEmail = mailProps.getProperty("mail.username");
            String senderPassword = mailProps.getProperty("mail.password");

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(senderEmail, senderPassword);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(subject);
            message.setText(messageText);

            Transport.send(message);
            System.out.println("Email sent to " + recipient);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to send email to " + recipient);
        }
    }
}