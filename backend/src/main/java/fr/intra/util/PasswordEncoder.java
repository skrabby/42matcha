package fr.intra.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private PasswordEncoder() {}

    public static String encode(String password) {
        return passwordEncoder.encode(password);
    }

    public static boolean isMatched(String actual, String encoded) {
        return passwordEncoder.matches(actual, encoded);
    }
}
