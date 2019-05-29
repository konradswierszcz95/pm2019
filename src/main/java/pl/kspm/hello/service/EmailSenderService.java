package pl.kspm.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import pl.kspm.hello.interfaces.EmailSenderInterface;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailSenderService implements EmailSenderInterface {
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendEmail(String to, String subject, String content) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setFrom("praca.magisterska.rejestracja@gmail.com");
            mimeMessageHelper.setReplyTo("praca.magisterska.rejestracja@gmail.com");
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(content);
        } catch(MessagingException ex) {
            ex.printStackTrace();
        }

        javaMailSender.send(mimeMessage);
    }
}
