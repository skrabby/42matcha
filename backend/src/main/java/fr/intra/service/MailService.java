package fr.intra.service;

import fr.intra.properties.SMTPProperties;
import fr.intra.util.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    private final SMTPProperties smtpProperties;

    @Autowired
    public MailService(SMTPProperties smtpProperties){
        this.smtpProperties = smtpProperties;
    }

    public boolean sendConfirmEmail(String secretKey, String email){
        try {
            new MailSender("regmatcha42@mail.ru", "xjVu2yZuyuSDhgpNxrTd", smtpProperties.getProperties())
                    .send("Registration in Matcha",
                            String.format("Welcome to Matcha 42! \n Confirm you email: http://localhost:8080/%s/%s", secretKey, email),
                            "regmatcha42@mail.ru",
                            email);
        } catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
        return true;
    }
}
