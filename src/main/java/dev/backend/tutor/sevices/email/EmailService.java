package dev.backend.tutor.sevices.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.jsoup.Jsoup;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class EmailService{

    private static final String EMAIL_CONFIRMATION_PATH = "src/main/resources/static/gmailConfirmation.html";
    private static final String CORPORATE_EMAIL = "tutorai@gmail.com";

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmailVerificationMessage(String email)  {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, "utf-8");
            String content = getEmailHtml();
            helper.setFrom(CORPORATE_EMAIL);
            helper.setTo(email);
            helper.setText(content, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new IllegalStateException("failed to send email");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private String getEmailHtml() throws IOException {
        return Jsoup.parse(new File(EMAIL_CONFIRMATION_PATH), "UTF-8").outerHtml();

    }
}