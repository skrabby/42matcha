package fr.intra.service;

import fr.intra.repository.LikesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikesService {
    private final LikesRepository likesRepository;
    private final AuthService authService;

    @Autowired
    public LikesService(LikesRepository likesRepository, AuthService authService){
        this.likesRepository = likesRepository;
        this.authService = authService;
    }

    public boolean insertLike(long likerId, long likedId){
        return likesRepository.insertLike(likerId, likedId);
    }

    public List<Long> findUserLikesIds(long id){
        return likesRepository.findUserLikesIds(id);
    }
}
