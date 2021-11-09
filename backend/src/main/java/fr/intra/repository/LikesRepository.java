package fr.intra.repository;

import fr.intra.properties.PgProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class LikesRepository {
    private final PgProperties pgProperties;
    private static final String LIKES = "LIKES";

    @Autowired
    public LikesRepository(PgProperties pgProperties) {
        this.pgProperties = pgProperties;
    }

    public boolean insertLike(long likerId, long likedId){
        String SQL = String.format("INSERT INTO %s (liker_id, liked_id) VALUES (?, ?)",
                LIKES);

        try(Connection pgPool = DriverManager.getConnection(pgProperties.getAuthorizedUrl())) {
            PreparedStatement statement = pgPool.prepareStatement(SQL);
            statement.setLong(1, likerId);
            statement.setLong(2, likedId);
            if (statement.executeUpdate() > 0)
                return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return false;
    }

    //Check matches
    public boolean checkMatch(long id1, long id2){
        String SQL = String.format("SELECT id FROM %s WHERE liker_id = ? AND liked_id = ?", LIKES);
        ArrayList<Long> result = new ArrayList<>();

        try (Connection pgPool = DriverManager.getConnection(pgProperties.getAuthorizedUrl())) {
            PreparedStatement statement = pgPool.prepareStatement(SQL);
            statement.setLong(1, id1);
            statement.setLong(2,  id2);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                result.add(Long.parseLong(rs.getString("id")));
            }
            statement.setLong(1, id2);
            statement.setLong(2,  id1);
            rs = statement.executeQuery();
            while (rs.next()){
                result.add(Long.parseLong(rs.getString("id")));
            }
            if (result.size() == 2)
                return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return true;
        }
        return false;
    }

    public List<Long> findUserLikesIds(long id) {
        String SQL = String.format("SELECT id FROM %s WHERE liker_id=?", LIKES);
        ArrayList<Long> result = new ArrayList<>();

        try (Connection pgPool = DriverManager.getConnection(pgProperties.getAuthorizedUrl())) {
            PreparedStatement statement = pgPool.prepareStatement(SQL);
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                result.add(Long.parseLong(rs.getString("id")));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }
}