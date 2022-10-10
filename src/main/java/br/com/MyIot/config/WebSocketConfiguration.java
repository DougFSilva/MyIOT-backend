package br.com.MyIot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * A classe <b>WebSocketConfiguration</b> implementa a interface <b>WebSocketMessageBrokerConfigurer</b> é responsável pela
 * configuração da conexão websocket
 * @author Douglas Ferreira da Silva
 * @since Out 2022
 * @version 1.0
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {
	
	@Value("${websocket.allowedOrigin}")
	private String webSocketOrigin;

	/**
	 * Método implementado da interface <b>WebSocketMessageBrokerConfigurer</b> que registra o endpoint da conexão websocket
	 * e seta a origem permitida
	 */
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/myiot-websocket").setAllowedOrigins(webSocketOrigin).withSockJS();
	}

	/**
	 * Método implementado da interface <b>WebSocketMessageBrokerConfigurer</b> que configura o Broker
	 */
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.setApplicationDestinationPrefixes("/app").enableSimpleBroker("/queue");
	}

	/**
	 * Método implementado da interface <b>WebSocketMessageBrokerConfigurer</b> que personaliza o canal de conexão,
	 * buscando o usuário autenticado e setando o mesmo no canal, permitindo uma conexão privada de websocket
	 */
	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		 registration.interceptors(new ChannelInterceptor() {
	            @Override
	            public Message<?> preSend(Message<?> message, MessageChannel channel) {
	                StompHeaderAccessor accessor =
	                        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
	                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
	                    Authentication user = SecurityContextHolder.getContext().getAuthentication() ; 
	                    accessor.setUser(user);
	                }
	                return message;
	            }
	        });
	}
	
	
}
