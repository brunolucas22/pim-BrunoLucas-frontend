package br.com.pesquisa_plus.pesquisa_plus.core.mail.service;

// Imports
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import br.com.pesquisa_plus.pesquisa_plus.apps.user.models.UserModel;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String username;
    
    @Value("${spring.mail.password}")
    private String password;
    
    public String sendEmailCreatedUser( String message, UserModel user ) {
    	Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(username));
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmailUser()));
            mimeMessage.setSubject("Bem Vindo ao Pesquisa Plus!");

            String template = loadTemplate();
            template = template.replace("{{message}}", message).replace("{{user}}", user.getNameUser());

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setText(template, true);

            // Adicionando a imagem inline corretamente
            ClassPathResource imageResource = new ClassPathResource("assets/logo_black.png");
            helper.addInline("logoImage", imageResource, "image/png");

            Transport.send(mimeMessage);
            System.out.println("E-mail enviado com sucesso!");

        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String loadTemplate() throws IOException {
        ClassPathResource resource = new ClassPathResource("created-user.html");
        return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    }
}
