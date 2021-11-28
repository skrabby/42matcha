package fr.intra.repository;

import fr.intra.entity.ChatInfo;
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
    private static final String USERS  = "USERS";
    private final LikesRepository likesRepository;
    private final UserRepository userRepository;

    @Autowired
    public ChatRoomRepository(PgProperties pgProperties,
                              LikesRepository likesRepository,
                              UserRepository userRepository) {
        this.pgProperties = pgProperties;
        this.likesRepository = likesRepository;
        this.userRepository = userRepository;
    }

    public List<ChatInfo> getAllChats(long id) {
        List<ChatInfo> result = new ArrayList<>();
        String SQL = String.format("SELECT * FROM %s WHERE user_id1=? OR user_id2=?", CHAT_ROOMS);

        try (Connection pgPool = DriverManager.getConnection(pgProperties.getAuthorizedUrl())) {
            PreparedStatement statement = pgPool.prepareStatement(SQL);
            statement.setLong(1, id);
            statement.setLong(2, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Long chatRoomId  = rs.getLong(1);
                Long userId1  = rs.getLong(2);
                Long userId2  = rs.getLong(3);
                String userName = userId1.equals(id)?
                        userRepository.findById(userId2).getName() :
                        userRepository.findById(userId1).getName();
                result.add(ChatInfo.builder().id(chatRoomId).UserName(userName).build());
            }
            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return result;
        }
    }
}
