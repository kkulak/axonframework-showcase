package knbit.memberquestions.bc.question.answering.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    private final MailSender mailSender;

    @Autowired
    public MailService(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void send(Email email) {
        final SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email.email());
        mailMessage.setSubject(email.subject());
        mailMessage.setText(email.content());

        mailSender.send(mailMessage);
    }

}
