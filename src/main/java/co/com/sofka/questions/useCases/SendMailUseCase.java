package co.com.sofka.questions.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@Validated
public class SendMailUseCase {

    @Autowired
    private JavaMailSender mailSender;
    private final String FROM = "brayan.demo.empresarial@gmail.com";

    public SendMailUseCase() {
    }

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public Mono<String> sendMail(String to, String subject, String body){
        try {

            MimeMessage message = mailSender.createMimeMessage();

            message.setSubject(subject);
            MimeMessageHelper helper;
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(FROM);
            helper.setTo(to);
            helper.setText(body, true);
            mailSender.send(message);
        } catch (MessagingException ex){
            ex.getMessage();
        }

        return Mono.just("¡Send!");
    }

}
