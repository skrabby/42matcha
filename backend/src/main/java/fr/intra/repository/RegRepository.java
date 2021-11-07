package fr.intra.repository;


import fr.intra.entity.User;
import fr.intra.properties.PgProperties;
import net.bytebuddy.utility.RandomString;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

@Repository
public class RegRepository {
    private final PgProperties pgProperties;
    private static final String REG_EMAILS = "REG_EMAILS";

    @Autowired
    public RegRepository(PgProperties pgProperties) {
        this.pgProperties = pgProperties;
    }


    public ResponseEntity<?> confirmEmail(String secretKey, String email) {
        String SQL = String.format("SELECT email FROM %s WHERE secret_key = ? AND email = ?", REG_EMAILS);
        ArrayList<Long> result = new ArrayList<>();

        try (Connection pgPool = DriverManager.getConnection(pgProperties.getAuthorizedUrl())) {
            PreparedStatement statement = pgPool.prepareStatement(SQL);
            statement.setString(1, secretKey);
            statement.setString(2, email);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                result.add(Long.parseLong(rs.getString("email")));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        if (result.size() == 1)
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public String insertEmailToReg(User user) {
        String SQL = String.format("INSERT INTO %s (name, email, password, secret_key) VALUES (?, ?, ?, ?)",
                REG_EMAILS);
        try(Connection pgPool = DriverManager.getConnection(pgProperties.getAuthorizedUrl())) {
            String secretKey;
            PreparedStatement statement = pgPool.prepareStatement(SQL);
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, secretKey = RandomStringUtils.random(10, true, true));
            if (statement.executeUpdate() > 0)
                return secretKey;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
        return null;
    }
}
