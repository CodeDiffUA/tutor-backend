package dev.backend.tutor.sevices.email;

import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.repositories.emails.ConfirmationEmailTokenRepository;
import dev.backend.tutor.repositories.passwords.ConfirmationPasswordTokenRepository;
import dev.backend.tutor.repositories.student.StudentRepository;
import dev.backend.tutor.sevices.security.TokenFactory;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class EmailService implements EmailSender {
    @Value("${front-end.base-url}")
    private static String frontendHostUrl;
    private static final String EMAIL_CONFIRMATION_PATH = "src/main/resources/static/gmailConfirmation.html";
    private static final String FORGOT_PASSWORD_PAGE = frontendHostUrl + "/forgot-password?token=";
    private static final String CONFIRM_EMAIL_ENDPOINT_DEV = "http://localhost:8080/api/v1/registration/confirm-login?token=";
    private static final String CONFIRM_EMAIL_ENDPOINT_PROD = "https://tutor-backend-k28m.onrender.com/api/v1/registration/confirm?token=";
    private static final String EMAIL_FORGOT_PASSWORD_PATH = "src/main/resources/static/gmailForgotPassword.html";

    private static final String CORPORATE_EMAIL = "shraierbohdan@gmail.com";

    private final JavaMailSender mailSender;
    private final TokenFactory tokenFactory;
    private final StudentRepository studentRepository;
    private final ConfirmationEmailTokenRepository confirmationEmailTokenRepository;
    private final ConfirmationPasswordTokenRepository confirmationPasswordTokenRepository;

    public EmailService(JavaMailSender mailSender, TokenFactory tokenFactory, StudentRepository studentRepository, ConfirmationEmailTokenRepository confirmationEmailTokenRepository, ConfirmationPasswordTokenRepository confirmationPasswordTokenRepository) {
        this.mailSender = mailSender;
        this.tokenFactory = tokenFactory;
        this.studentRepository = studentRepository;
        this.confirmationEmailTokenRepository = confirmationEmailTokenRepository;
        this.confirmationPasswordTokenRepository = confirmationPasswordTokenRepository;
    }

    public void sendEmailVerificationMessage(String email) throws NotFoundUserException, IOException {
        var student = studentRepository.findStudentsByUsernameOrEmailWithRoles(email)
                .orElseThrow(() -> new NotFoundUserException("cannot find user - " + email));
        var token = tokenFactory.createConfirmationEmailToken(student);
        confirmationEmailTokenRepository.insert(token);
        sendMessage(token.getToken(), email, EMAIL_CONFIRMATION_PATH, CONFIRM_EMAIL_ENDPOINT_DEV);
    }

    public void sendEmailForgotPasswordMessage(String email) throws NotFoundUserException, IOException {
        var student = studentRepository.findStudentsByUsernameOrEmailWithRoles(email)
                .orElseThrow(() -> new NotFoundUserException("cannot find user - " + email));
        var token = tokenFactory.createConfirmationPasswordToken(student);
        confirmationPasswordTokenRepository.insert(token);
        sendMessage(token.getToken(), email, EMAIL_FORGOT_PASSWORD_PATH, FORGOT_PASSWORD_PAGE);
    }

    private void sendMessage(String token, String email, String emailForgotPasswordPath, String forgotPasswordPage) throws IOException {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, "utf-8");
            String content = getEmailHtml(token, emailForgotPasswordPath, forgotPasswordPage);
            helper.setFrom(CORPORATE_EMAIL);
            helper.setTo(email);
            helper.setText(content, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new IllegalStateException("failed to send email");
        }
    }


    private String getEmailHtml(String token, String path, String url) throws IOException {
        var html = Jsoup.parse(new File(path), "UTF-8").outerHtml();
        html = html.replace(
                "null",
                url + token);
        return html;
    }
}