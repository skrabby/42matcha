package fr.intra.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
@Getter
public class PgProperties {
    private final String url;
    private final String username;
    private final String password;
    private final String authorizedUrl;

    @Autowired
    public PgProperties(@Value("${spring.datasource.url}") @NotNull String url,
                        @Value("${spring.datasource.username}") @NotNull String username,
                        @Value("${spring.datasource.password}") @NotNull String password) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.authorizedUrl = String.format("%s?user=%s&password=%s",
                url, username, password);
    }
}
