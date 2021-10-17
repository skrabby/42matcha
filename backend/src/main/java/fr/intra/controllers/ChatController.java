package fr.intra.controllers;

import fr.intra.messages.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ChatController {

	@RequestMapping("/usersChat")
	public String index(HttpServletRequest request, Model model) {
		String username = "test";

		model.addAttribute("username", username);

		return "chat";
	}
}
