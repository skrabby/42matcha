package fr.intra.service;

import fr.intra.repository.PicturesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PicturesService {
    private final PicturesRepository picturesRepository;
    private final AuthService authService;
    private final String destination = "uploads/";

    @Autowired
    public PicturesService(PicturesRepository picturesRepository, AuthService authService){
        this.picturesRepository = picturesRepository;
        this.authService = authService;
    }

    public List<String> findALLPicturesByUserId(long id){
        return picturesRepository.findAllPicturesByUserId(id);
    }


    public boolean savePictureToDB(String id, String pictureNum, String exp) {
        String path = destination + id +"/" + id + "_" + pictureNum + "."  + exp;
        return picturesRepository.save(id, path, pictureNum);
    }

    public String getDestination(){
        return destination;
    }
}
