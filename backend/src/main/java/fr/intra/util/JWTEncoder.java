package fr.intra.util;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class JWTEncoder {
    private static RSAPrivateKey privateKey;
    private static RSAPublicKey publicKey;

    static {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            String privateKeyContent = new String(
                    Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("keys/private_key_pkcs8.pem").toURI())));
            privateKeyContent = privateKeyContent
                    .replaceAll("\\n", "")
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "");


            PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyContent));
            privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpecPKCS8);

            String publicKeyContent = new String(
                    Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("keys/public_key.pem").toURI())));
            publicKeyContent = publicKeyContent
                    .replaceAll("\\n", "")
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "");

            X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyContent));
            publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpecX509);

        } catch (NoSuchAlgorithmException | URISyntaxException | IOException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }

    public static String generateToken(Long userId) {
        String token = null;
        Algorithm algorithm = Algorithm.RSA256(null, privateKey);
        token = JWT.create()
                .withIssuer("42matcha")
                .withClaim("userId", userId)
                .sign(algorithm);
        return token;
    }

    public static DecodedJWT verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.RSA256(publicKey, null);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("42matcha")
                    .build();
            return verifier.verify(token);
        } catch (JWTVerificationException x) {
            throw x;
        }
    }
}
