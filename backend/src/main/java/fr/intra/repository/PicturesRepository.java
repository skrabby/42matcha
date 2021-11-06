package fr.intra.repository;

import fr.intra.properties.PgProperties;
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
}
