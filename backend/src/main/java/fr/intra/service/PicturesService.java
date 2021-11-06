package fr.intra.service;

import fr.intra.repository.PicturesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PicturesService {
    private final PicturesRepository picturesRepository;
    private final AuthService authService;

    @Autowired
    public PicturesService(PicturesRepository picturesRepository, AuthService authService){
        this.picturesRepository = picturesRepository;
        this.authService = authService;
    }

    public List<String> findALLPicturesByUserId(long id){
        return picturesRepository.findAllPicturesByUserId(id);
    }
}
