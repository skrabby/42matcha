package fr.intra.repository;

import fr.intra.properties.PgProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TagsRepository {
    private final PgProperties pgProperties;
    private static final String USER_TAGS = "USER_TAGS";
    private static final String TAGS = "TAGS";

    @Autowired
    public TagsRepository(PgProperties pgProperties){
        this.pgProperties = pgProperties;
    }

    public List<String> findAllTagsById(long id) {
        ArrayList<String> result = new ArrayList<>();
        String SQL = String.format("SELECT tag FROM %s WHERE id in " +
                "(SELECT tag_id FROM %s WHERE user_id=?)", TAGS, USER_TAGS);
        try (Connection pgPool = DriverManager.getConnection(pgProperties.getAuthorizedUrl())) {
            PreparedStatement statement = pgPool.prepareStatement(SQL);
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                result.add(rs.getString("tag"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }
}
