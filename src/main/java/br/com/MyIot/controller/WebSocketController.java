package br.com.MyIot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import br.com.MyIot.model.user.User;
import br.com.MyIot.service.UserService;

@Controller
public class WebSocketController {

	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	
	@Autowired
	private UserService userService;

	@MessageMapping("/user-id={userId}")
	public void sendMessage(String message, @DestinationVariable("userId") String userId) {
		User user = userService.findById(message);
		messagingTemplate.convertAndSend("/topic/hi/user0@gmail.com", user);
		System.out.println(user);
	}

}
