package rw.ac.rca.centrika.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import rw.ac.rca.centrika.dtos.requests.CreateGroupMessageDto;
import rw.ac.rca.centrika.dtos.requests.CreateMessageDto;
import rw.ac.rca.centrika.models.Message;
import rw.ac.rca.centrika.services.MessageService;
import rw.ac.rca.centrika.services.serviceImpl.MessageServiceImpl;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class WebSocketController {

    private final MessageServiceImpl messageService;

    @MessageMapping("/chat/{groupId}")
    @SendTo("topic/group/{groupId}")
    public Message groupChat(@DestinationVariable UUID groupId ,  CreateGroupMessageDto message){
      return messageService.saveGroup(message);
    }

    @MessageMapping("/chat/direct/{userId}")
    @SendTo("/topic/direct/{userId}")
    public Message groupChat(@DestinationVariable UUID userId ,  CreateMessageDto message){
        return messageService.savePersonal(message);
    }
}
