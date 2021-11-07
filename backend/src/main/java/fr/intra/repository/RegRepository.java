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
    private static final String USERS = "USERS";

    @Autowired
    public RegRepository(PgProperties pgProperties) {
        this.pgProperties = pgProperties;
    }


    public ResponseEntity<?> confirmEmail(String secretKey, String email) {
        String SQL = String.format("SELECT * FROM %s WHERE secret_key = ? AND email = ?", REG_EMAILS);
        User user = new User();

        //Check Emails in registration table
        try (Connection regPool = DriverManager.getConnection(pgProperties.getAuthorizedUrl())) {
            PreparedStatement statement = regPool.prepareStatement(SQL);
            statement.setString(1, secretKey);
            statement.setString(2, email);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setName(rs.getString("name"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        if (user.getEmail() != null) {
            //If email find then insert new client in USERS
            SQL = String.format("INSERT INTO %s (name, email, password) VALUES (?, ?, ?)",
                    USERS);

            try (Connection regPool = DriverManager.getConnection(pgProperties.getAuthorizedUrl())) {
                PreparedStatement statement = regPool.prepareStatement(SQL);
                statement.setString(1, user.getName());
                statement.setString(2, user.getEmail());
                statement.setString(3, user.getPassword());
                if (statement.executeUpdate() > 0)
                    return new ResponseEntity<>(HttpStatus.ACCEPTED);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            } catch (SQLException ex) {
                ex.printStackTrace();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
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
