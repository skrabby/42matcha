package fr.intra.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.validation.constraints.NotNull;
import java.util.Properties;

@Component
@Getter
public class SMTPProperties {
    private final Properties properties;

    @Autowired
    public SMTPProperties(
            @Value("${mail.smtp.host}") @NotNull String host,
            @Value("${mail.smtp.port}") @NotNull String smtpPort,
            @Value("${mail.smtp.auth}") @NotNull String smtpAuth,
            @Value("${mail.smtp.socketFactory.class}") @NotNull String socketFactory,
            @Value("${mail.smtp.socketFactory.port}") @NotNull String socketPort) {
        this.properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.socketFactory.port", socketPort);
        properties.put("mail.smtp.socketFactory.class", socketFactory);
        properties.put("mail.smtp.auth", smtpAuth);
        properties.put("mail.smtp.port", smtpPort);
    }
}
