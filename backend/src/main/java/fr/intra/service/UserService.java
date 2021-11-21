package fr.intra.service;

import com.sun.javafx.scene.control.behavior.SliderBehavior;
import fr.intra.entity.User;
import fr.intra.repository.LikesRepository;
import fr.intra.repository.TagsRepository;
import fr.intra.repository.UserRepository;
import fr.intra.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final LikesRepository likesRepository;
    private final FilesStorageService filesStorageService;
    private final AuthService authService;

    @Autowired
    public UserService(UserRepository userRepository,
                       AuthService authService,
                       LikesRepository likesRepository,
                       FilesStorageService filesStorageService) {
        this.userRepository = userRepository;
        this.likesRepository = likesRepository;
        this.filesStorageService = filesStorageService;
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
            if(likesRepository.checkMatch(id, partners.get(i).getId()))
                partners.remove(i);
            else if (!(Utils.checkAge(user.getBirthday(), partners.get(i).getBirthday())))
                partners.remove(i);
            else if (!(Utils.checkLocation(user, partners.get(i))))
                partners.remove(i);
        }
        Collections.sort(partners, new Comparator<User>() {

            @Override
            public int compare(User r1, User r2) {
                int result = Utils.checkOverlap(r1, r2, user);
                if(result != 0) {
                    return result;
                }else{
                return Integer.valueOf(r1.getPopularity()).compareTo(Integer.valueOf(r2.getPopularity()));
                }
            }
        });

        return partners;
    }

    public boolean setAvatar(String pictureNum, long id) {
        String url = filesStorageService.getPicture(id, Integer.valueOf(pictureNum));
        return userRepository.setAvatar(id, url);
    }
}
