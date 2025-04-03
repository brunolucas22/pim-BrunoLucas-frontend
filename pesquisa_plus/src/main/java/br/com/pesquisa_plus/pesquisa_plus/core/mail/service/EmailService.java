package br.com.pesquisa_plus.pesquisa_plus.core.mail.service;

// Imports
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String username;

    public String sendEmailText(String destiny, String subject, String message) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);

            messageHelper.setFrom(username);
            messageHelper.setTo(destiny);
            messageHelper.setSubject(subject);

            String template = loadTemplate();
            messageHelper.setText(template, true);
            javaMailSender.send(mimeMessage);
            return "Email enviado com sucesso!";
        } catch (Exception e) {
            System.out.println(e);
            return "Erro ao enviar email!";
        }
    }

    public String loadTemplate() throws IOException {
        ClassPathResource resource = new ClassPathResource("created-user.html");
        return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    }
}
