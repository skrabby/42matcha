package fr.intra.controller;

import fr.intra.entity.ChatRoomStatus;
import fr.intra.exception.JWTException;
import fr.intra.service.AuthService;
import fr.intra.service.LikesService;
import fr.intra.service.TagsService;
import fr.intra.service.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@Controller
@RequestMapping("/")
@Transactional
public class LikeController {
    private final LikesService likesService;
    private final AuthService authService;

    @Autowired
    public LikeController(LikesService likesService,
                          AuthService authService) {
        this.likesService = likesService;
        this.authService = authService;
    }

    @PostMapping("setLike/{likedID}")
    @ResponseBody
    public ChatRoomStatus setLike(@RequestHeader String token,
                                  @PathVariable long likedID) {
        long id;
        try {
            id = authService.getUserId(token);
        } catch (JWTException ex) {
            return ChatRoomStatus.NOT_AUTHORIZED;
        }
        return likesService.insertLike(id, likedID);
    }

    @GetMapping("isMatch/{likedID}")
    @ResponseBody
    public boolean isMatch(@RequestHeader String token,
                           @PathVariable long likedID) {
        long id;
        try {
            id = authService.getUserId(token);
        } catch (JWTException ex) {
            return false;
        }
        return likesService.checkMatch(id, likedID);
    }
}