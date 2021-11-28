package fr.intra.service;

import fr.intra.entity.ChatRoomStatus;
import fr.intra.repository.LikesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikesService {
    private final LikesRepository likesRepository;

    @Autowired
    public LikesService(LikesRepository likesRepository){
        this.likesRepository = likesRepository;
    }

    public ChatRoomStatus insertLike(long likerId, long likedId){
        return likesRepository.insertLike(likerId, likedId);
    }

    public List<Long> findUserLikesIds(long id){
        return likesRepository.findUserLikesIds(id);
    }

    public boolean checkMatch(long id, long likedID) {
        return likesRepository.checkMatch(id, likedID);
    }
}
