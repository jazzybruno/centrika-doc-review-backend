package rw.ac.rca.centrika.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import rw.ac.rca.centrika.services.serviceImpl.MessageServiceImpl;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private final MessageServiceImpl messageService;

    @Autowired
    public WebSocketConfig(MessageServiceImpl messageService) {
        this.messageService = messageService;
    }


    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
       config.enableSimpleBroker("/topic");
       config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").withSockJS();
    }
}
