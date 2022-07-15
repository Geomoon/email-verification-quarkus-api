package com.luna.restapi.mail;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class MailService {

    private Mailer mailer;

    @Inject
    public MailService(Mailer mailer) {
        this.mailer = mailer;
    }

    public void sendEmail(String to, String subject, String body) {
        mailer.send(
                Mail.withText(to, subject, body)
        );
    }

}
