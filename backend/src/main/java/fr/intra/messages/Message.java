package fr.intra.messages;

import lombok.Data;

@Data
public class Message {

	private MessageType type;
	private String content;
	private String sender;
	private String senderId;

	public enum MessageType {
		CHAT, JOIN, LEAVE
	}
}
