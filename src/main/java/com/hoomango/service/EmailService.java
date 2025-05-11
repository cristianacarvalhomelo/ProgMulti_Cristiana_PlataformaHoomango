package com.hoomango.service;

import jakarta.ejb.Stateless;
import jakarta.enterprise.context.SessionScoped;
import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.io.Serializable;
import java.util.Properties;

@Stateless
public class EmailService implements Serializable {

    public void enviarEmail(String destinatario, String assunto, String mensagem) {

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("cristianacarvalhomelo@gmail.com", "eezr mlvk ozpl rnoc");
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("seuemail@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject(assunto);
            message.setText(mensagem);
            System.out.println("TESTE CRIS "+message);
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
