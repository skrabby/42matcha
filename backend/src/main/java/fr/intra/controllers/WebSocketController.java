package fr.intra.controllers;

import fr.intra.messages.ChatNotification;
import fr.intra.messages.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    @MessageMapping("/chat")
    public void sendMessage(@Payload Message chatMessage) {

        messagingTemplate.convertAndSendToUser(
                chatMessage.getSender(),"/queue/messages",
                new ChatNotification(
                        "1", "3",
                        "Igor2"));
    }
}

