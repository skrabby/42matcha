package fr.intra.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import fr.intra.entity.User;
import fr.intra.exception.JWTException;
import fr.intra.exception.LoginException;
import fr.intra.repository.UserRepository;
import fr.intra.util.JWTEncoder;
import fr.intra.util.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;

    @Autowired
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User authorize(String username, String password) {
        if (username == null || password == null) {
            throw new LoginException(HttpStatus.BAD_REQUEST, "Invalid body, missing username or password");
        }

        User user = userRepository.findByEmail(username);
        if (user == null || !PasswordEncoder.isMatched(password, user.getPassword())) {
            throw new LoginException(HttpStatus.UNAUTHORIZED, "User not found. Wrong username or password");
        }

        user.setToken(JWTEncoder.generateToken(user.getId()));
        user.setPassword("");
        return user;
    }

    public Long getUserId(String token) {
        DecodedJWT jwt = JWTEncoder.verifyToken(token);
        if (jwt == null) {
            throw new JWTException(HttpStatus.UNAUTHORIZED, "Invalid token received");
        }

        return jwt.getClaim("userId").asLong();
    }
}
