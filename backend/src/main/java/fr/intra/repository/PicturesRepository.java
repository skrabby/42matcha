package fr.intra.repository;

import fr.intra.properties.PgProperties;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PicturesRepository {
    private final PgProperties pgProperties;
    private static final String PICTURES  = "PICTURES";

    @Autowired
    public PicturesRepository(PgProperties pgProperties){
        this.pgProperties = pgProperties;
    }

    public List<String> findAllPicturesByUserId(long id){
        ArrayList<String> result = new ArrayList<>();
        String SQL = String.format("SELECT url FROM %s WHERE user_id=?",  PICTURES);

        try(Connection pgPool = DriverManager.getConnection(pgProperties.getAuthorizedUrl())) {
            PreparedStatement statement = pgPool.prepareStatement(SQL);
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                result.add(rs.getString("url"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }


    public boolean save(String id, String path, String pictureNum) {
        String SQL = String.format("INSERT INTO %s (user_id, url, picture_num) VALUES (?, ?, ?)",
                PICTURES);
        try (Connection pgPool = DriverManager.getConnection(pgProperties.getAuthorizedUrl())) {
            PreparedStatement statement = pgPool.prepareStatement(SQL);
            statement.setLong(1, Long.valueOf(id));
            statement.setString(2, path);
            statement.setInt(3,Integer.valueOf(pictureNum));
            statement.executeUpdate();
        } catch (SQLException ex) {
            if(ex.toString().contains
                    ("ERROR: duplicate key value violates unique constraint \"pictures_url_key\""))
                return true;
            return false;
        }
        return true;
    }

    public String findPicture(long userID, int pictureNum) {
        String SQL = String.format("SELECT url FROM %s WHERE user_id=? AND picture_num=?",  PICTURES);

        try(Connection pgPool = DriverManager.getConnection(pgProperties.getAuthorizedUrl())) {
            PreparedStatement statement = pgPool.prepareStatement(SQL);
            statement.setLong(1, userID);
            statement.setInt(2,  pictureNum);
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
