package fr.intra.controller;

import fr.intra.entity.User;
import fr.intra.exception.JWTException;
import fr.intra.service.AuthService;
import fr.intra.service.TagsService;
import fr.intra.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/")
@Transactional
public class UserController {
    private final UserService userService;
    private final AuthService authService;

    @Autowired
    public UserController(UserService userService, AuthService authService, TagsService tagsService){
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("profile")
    @ResponseBody
    public User getUserFromToken(@RequestHeader String token) {
        User user;
        long id;
        try {
            id = authService.getUserId(token);
        } catch (JWTException ex){
            return null;
        }
        user = userService.findById(id);
        return user;
    }

    @PostMapping("update")
    public ResponseEntity<?> updateUser(@RequestBody User user){
        try {
            long id = authService.getUserId(user.getToken());
            if (id != user.getId())
                return new  ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (JWTException ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return userService.updateUserProfile(user) ?
                new ResponseEntity<>(HttpStatus.OK):
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("findPartner")
    @ResponseBody
    public List<User> getPartners(@RequestHeader String token){
        long id;
        try {
            id = authService.getUserId(token);
        } catch (JWTException ex){
            return null;
        }
        return userService.findPartners(id);
    }
}
