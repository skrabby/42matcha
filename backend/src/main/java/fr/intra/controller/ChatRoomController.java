package fr.intra.controller;

import fr.intra.exception.JWTException;
import fr.intra.service.AuthService;
import fr.intra.service.ChatRoomService;
import fr.intra.service.LikesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.transaction.Transactional;
import java.util.List;

@Controller
@Transactional
public class ChatRoomController {
    private final AuthService authService;
    private final ChatRoomService chatRoomService;

    @Autowired
    public ChatRoomController(AuthService authService,
                              ChatRoomService chatRoomService) {
        this.authService = authService;
        this.chatRoomService = chatRoomService;
    }

    @GetMapping("findAllChats")
    @ResponseBody
    public List<String> findAllChats(@RequestHeader String token){
        long id;
        try {
            id = authService.getUserId(token);
        } catch (JWTException ex){
            return null;
        }
        return chatRoomService.getAllChats(id);
    }

}
