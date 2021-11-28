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

//    @GetMapping("id{id}")
//    @ResponseBody
//    public User getUserById(@PathVariable Long id) {
//        User user = userService.findById(id);
//
//        return user;
//    }

    @GetMapping("profile")
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

    @PostMapping("setAvatar")
    public ResponseEntity<?> setAvatar(@RequestBody String pictureNum,
                                       @RequestHeader String token){
        long id;
        try {
            id = authService.getUserId(token);
        } catch (JWTException ex){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if(userService.setAvatar(pictureNum, id))
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("update")
    public ResponseEntity<?> updateUser(@RequestBody User user,
                                        @RequestHeader String token){
        long id;
        try {
            id = authService.getUserId(token);
        } catch (JWTException ex){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return userService.updateUserProfile(user) ?
                new ResponseEntity<>(HttpStatus.OK):
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("findPartner")
    @ResponseBody
    public List<User> getPartners(@RequestHeader String token){
        long id;
        try {
            id = authService.getUserId(token);
        } catch (JWTException ex){
            return null;
        }
        List<User> users = userService.findPartners(id);
        return users;
    }
}
