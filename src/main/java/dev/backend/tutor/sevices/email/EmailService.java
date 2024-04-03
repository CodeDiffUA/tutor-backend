package dev.backend.tutor.sevices.email;

import dev.backend.tutor.entities.auth.ConfirmationEmailToken;
import dev.backend.tutor.exceptions.InvalidTokenException;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.repositories.emails.ConfirmationEmailTokenRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.jsoup.Jsoup;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class EmailService implements EmailSender{

    private static final String EMAIL_CONFIRMATION_PATH = "src/main/resources/static/gmailConfirmation.html";
    private static final String CORPORATE_EMAIL = "shraierbohdan@gmail.com";

    private final JavaMailSender mailSender;
    private final ConfirmationEmailTokenRepository confirmationEmailTokenRepository;
    private final StudentRepository studentRepository;

    public EmailService(JavaMailSender mailSender, ConfirmationEmailTokenRepository confirmationEmailTokenRepository, StudentRepository studentRepository) {
        this.mailSender = mailSender;
        this.confirmationEmailTokenRepository = confirmationEmailTokenRepository;
        this.studentRepository = studentRepository;
    }

    public void sendEmailVerificationMessage(String email, String token) throws NotFoundUserException, InvalidTokenException {
        ConfirmationEmailToken confirmationEmailToken = confirmationEmailTokenRepository.findByTokenWithStudent(token)
                .orElseThrow(() -> new InvalidTokenException("no such a token found"));
//        checkIfEmailExistsInDB(email);
        checkIfTokenAndEmailRelated(email, confirmationEmailToken);
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, "utf-8");
            String content = getEmailHtml(token);
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

    private void checkIfTokenAndEmailRelated(String email, ConfirmationEmailToken confirmationEmailToken) throws NotFoundUserException {
        if (!email.equals(confirmationEmailToken.getStudent().getEmail())){
            throw new NotFoundUserException("token and email don't match");
        }
    }

    private void checkIfEmailExistsInDB(String email) throws NotFoundUserException {
        if (studentRepository.existsStudentByEmail(email)){
            throw new NotFoundUserException("no such email in database - " + email);
        }
    }


    private String getEmailHtml(String token) throws IOException {
        var html = Jsoup.parse(new File(EMAIL_CONFIRMATION_PATH), "UTF-8").outerHtml();
        html = html.replace(
                "http://localhost:3000/confirm-page",
                "http://localhost:3000/confirm-page/"+token);
        return html;
    }
}