package fr.intra.service;

import com.sun.javafx.scene.control.behavior.SliderBehavior;
import fr.intra.entity.User;
import fr.intra.repository.LikesRepository;
import fr.intra.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final LikesRepository likesRepository;
    private final AuthService authService;

    @Autowired
    public UserService(UserRepository userRepository, AuthService authService, LikesRepository likesRepository) {
        this.userRepository = userRepository;
        this.likesRepository = likesRepository;
        this.authService = authService;
    }

    public User findById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public boolean updateUserProfile(User user) {
        return userRepository.updateUserProfile(user);
    }

    public List<User> findPartners(long id) {
        User user = findById(id);
        List<User> partners;
        if (user.getOrientation().equals("BISEXUAL"))
            partners = userRepository.findPartners(user.getEmail());
        else
            partners = userRepository.findPartners(user.getOrientation(), user.getGender(), user.getEmail());
        for (int i = 0; i < partners.size(); i++) {
            if(!(likesRepository.checkMatch(id, partners.get(i).getId())))
                partners.remove(i);
        }
        return partners;
    }
}
