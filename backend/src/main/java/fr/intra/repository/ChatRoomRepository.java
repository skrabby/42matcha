package fr.intra.repository;

import fr.intra.entity.ChatRoomStatus;
import fr.intra.properties.PgProperties;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ChatRoomRepository {

    private final PgProperties pgProperties;
    private static final String CHAT_ROOMS  = "CHAT_ROOMS";
    private final LikesRepository likesRepository;

    @Autowired
    public ChatRoomRepository(PgProperties pgProperties,
                              LikesRepository likesRepository) {
        this.pgProperties = pgProperties;
        this.likesRepository = likesRepository;
    }

    public List<String> getAllChats(long id) {
        List<String> result = new ArrayList<>();
        String SQL = String.format("SELECT id FROM %s WHERE user_id1=? OR user_id2=?", CHAT_ROOMS);

        try (Connection pgPool = DriverManager.getConnection(pgProperties.getAuthorizedUrl())) {
            PreparedStatement statement = pgPool.prepareStatement(SQL);
            statement.setLong(1, id);
            statement.setLong(2, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                result.add(rs.getString(1));
            }
            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return result;
        }
    }
}
