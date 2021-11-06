package fr.intra.service;

import fr.intra.repository.TagsRepository;
import fr.intra.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagsService {
    private final TagsRepository tagsRepository;
    private final AuthService authService;

    @Autowired
    public TagsService(TagsRepository tagsRepository, AuthService authService){
        this.authService = authService;
        this.tagsRepository = tagsRepository;
    }

    public List<String> findAllTagsById(long id){
        return tagsRepository.findAllTagsById(id);
    }
}
