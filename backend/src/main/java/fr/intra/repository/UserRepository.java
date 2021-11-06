package fr.intra.repository;

import fr.intra.entity.User;
import fr.intra.properties.PgProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {
    private final PgProperties pgProperties;
    private static final String USERS = "USERS";

    @Autowired
    public UserRepository(PgProperties pgProperties) {
        this.pgProperties = pgProperties;
    }

    public User findById(Long id) {
        User user = null;
        String SQL = String.format("SELECT * FROM %s WHERE id=?", USERS);

        try(Connection pgPool = DriverManager.getConnection(pgProperties.getAuthorizedUrl())) {
            PreparedStatement statement = pgPool.prepareStatement(SQL);
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                user = buildUserFromRs(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return user;
    }

    public User findByEmail(String email) {
        User user = null;
        String SQL = String.format("SELECT * FROM %s WHERE email=?", USERS);

        try(Connection pgPool = DriverManager.getConnection(pgProperties.getAuthorizedUrl())) {
            PreparedStatement statement = pgPool.prepareStatement(SQL);
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                user = buildUserFromRs(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return user;
    }

    public List<User> findAll() {
        List<User> userList = new ArrayList<>();
        String SQL = String.format("SELECT * FROM %s", USERS);

        try(Connection pgPool = DriverManager.getConnection(pgProperties.getAuthorizedUrl())) {
            PreparedStatement statement = pgPool.prepareStatement(SQL);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                User user = buildUserFromRs(rs);
                userList.add(user);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return userList;
    }

    private User buildUserFromRs(ResultSet rs) throws SQLException {
        return User.builder()
                .id(Long.parseLong(rs.getString("id")))
                .password(rs.getString("password"))
                .email(rs.getString("email"))
                .name(rs.getString("name"))
                .gender(rs.getString("gender"))
                .orientation(rs.getString("orientation"))
                .description(rs.getString("description"))
                .build();
    }
}
