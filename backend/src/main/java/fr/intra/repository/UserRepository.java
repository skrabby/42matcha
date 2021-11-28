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
    private final TagsRepository tagsRepository;
    private final PicturesRepository picturesRepository;

    @Autowired
    public UserRepository(PgProperties pgProperties,
                          TagsRepository tagsRepository,
                          PicturesRepository picturesRepository) {
        this.pgProperties = pgProperties;
        this.tagsRepository = tagsRepository;
        this.picturesRepository = picturesRepository;
    }

    public User findById(Long id) {
        User user = null;
        String SQL = String.format("SELECT * FROM %s WHERE id=?", USERS);

        try (Connection pgPool = DriverManager.getConnection(pgProperties.getAuthorizedUrl())) {
            PreparedStatement statement = pgPool.prepareStatement(SQL);
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                user = buildUserFromRs(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        if (user != null) {
            user.setTags(tagsRepository.findAllTagsById(id));
            user.setPictures(picturesRepository.getPicturesById(id));
        }
        return user;
    }

    public User findByEmail(String email) {
        User user = null;
        String SQL = String.format("SELECT * FROM %s WHERE email=?", USERS);

        try (Connection pgPool = DriverManager.getConnection(pgProperties.getAuthorizedUrl())) {
            PreparedStatement statement = pgPool.prepareStatement(SQL);
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                user = buildUserFromRs(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        if (user != null) {
            user.setTags(tagsRepository.findAllTagsById(user.getId()));
        }
        return user;
    }

    public List<User> findAll() {
        List<User> userList = new ArrayList<>();
        String SQL = String.format("SELECT * FROM %s", USERS);

        try (Connection pgPool = DriverManager.getConnection(pgProperties.getAuthorizedUrl())) {
            PreparedStatement statement = pgPool.prepareStatement(SQL);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                User user = buildUserFromRs(rs);
                user.setTags(tagsRepository.findAllTagsById(user.getId()));
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
                .birthday(rs.getString("birthday"))
                .popularity(rs.getInt("popularity"))
                .latitude(rs.getString("latitude"))
                .longitude(rs.getString("longitude"))
                .avatarUrl(rs.getString("avatar_url"))
                .build();
    }

    public boolean updateUserProfile(User user) {
        String SQL = String.format("UPDATE %s " +
                "SET name = ?,gender = ?,orientation = ?,description = ?" +
                "WHERE id=?", USERS);
        try (Connection pgPool = DriverManager.getConnection(pgProperties.getAuthorizedUrl())) {
            PreparedStatement statement = pgPool.prepareStatement(SQL);
            statement.setString(1, user.getName());
            statement.setString(2, user.getGender());
            statement.setString(3, user.getOrientation());
            statement.setString(4, user.getDescription());
            statement.setLong(5, user.getId());
            if (statement.executeUpdate() > 0)
                return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return false;
    }

    public List<User> findPartners(String email) {
        List<User> partners = new ArrayList<>();
        String SQL = String.format("SELECT * FROM %s WHERE email != ? AND orientation = ?", USERS);

        try (Connection pgPool = DriverManager.getConnection(pgProperties.getAuthorizedUrl())) {
            PreparedStatement statement = pgPool.prepareStatement(SQL);
            statement.setString(1, email);
            statement.setString(2, "BISEXUAL");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                User user = buildUserFromRs(rs);
                user.setTags(tagsRepository.findAllTagsById(user.getId()));
                partners.add(user);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return partners;
    }

    public List<User> findPartners(String orientation, String gender, String email) {
        List<User> partners = new ArrayList<>();
        String SQL = String.format("SELECT * FROM %s WHERE email != ? AND orientation = ? AND gender = ?", USERS);

        try (Connection pgPool = DriverManager.getConnection(pgProperties.getAuthorizedUrl())) {
            PreparedStatement statement = pgPool.prepareStatement(SQL);
            statement.setString(1, email);
            statement.setString(2, orientation);
            if (orientation.equals("HETERO"))
                statement.setString(3, gender.equals("MALE") ? "FEMALE" : "MALE");
            else
                statement.setString(3, gender.equals("MALE") ? "MALE" : "FEMALE");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                User user = buildUserFromRs(rs);
                user.setTags(tagsRepository.findAllTagsById(user.getId()));
                partners.add(user);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return partners;
    }

    public boolean setAvatar(long id, String url) {
        String SQL = String.format("UPDATE %s " +
                "SET avatar_url = ?" +
                "WHERE id=?", USERS);
        try (Connection pgPool = DriverManager.getConnection(pgProperties.getAuthorizedUrl())) {
            PreparedStatement statement = pgPool.prepareStatement(SQL);
            statement.setString(1, url);
            statement.setLong(2, id);
            if (statement.executeUpdate() > 0)
                return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return false;
    }
}
