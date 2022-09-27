package br.com.MyIot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.MyIot.model.user.User;

@Service
public class WebSocketService {

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	public void sendMessage(String message) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		messagingTemplate.convertAndSendToUser(user.getUsername(), "/queue/message", message);
	}
}
