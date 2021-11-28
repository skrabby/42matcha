package fr.intra.service;

import fr.intra.entity.ChatInfo;
import fr.intra.repository.ChatRoomRepository;
import fr.intra.repository.LikesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    @Autowired
    public ChatRoomService(ChatRoomRepository chatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;
    }

    public List<ChatInfo> getAllChats(long id) {
        return chatRoomRepository.getAllChats(id);
    }
}
