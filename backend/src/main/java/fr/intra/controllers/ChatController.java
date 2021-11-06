package fr.intra.controllers;

import fr.intra.messages.Message;
import fr.intra.util.Utils;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;

@Controller
public class ChatController {

	@RequestMapping("/usersChat/sender{sender_id}/recip{recip_id}")
	public String index(HttpServletRequest request, Model model,
						@PathVariable("sender_id") String senderID,
						@PathVariable("recip_id") String recipientID) {
		try {
			ResultSet resultSet = Utils.selectEvent("select name from users where id = " + senderID);

			if (resultSet == null){
				return "error";
			}
			resultSet.next();
			String username = resultSet.getString(1);
			resultSet = Utils.selectEvent("select name from users where id = " + recipientID);
			resultSet.next();
			String recipname = resultSet.getString(1);

			model.addAttribute("username", username);
			model.addAttribute("recipname", recipname);
			model.addAttribute("senderID", senderID);
			return "chat";
		} catch (Exception ex){
			ex.printStackTrace();
			return "error";
		}
	}
}
