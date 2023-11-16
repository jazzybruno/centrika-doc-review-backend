package rw.ac.rca.centrika.services;

import rw.ac.rca.centrika.dtos.requests.CreateGroupMessageDto;
import rw.ac.rca.centrika.dtos.requests.CreateMessageDto;
import rw.ac.rca.centrika.models.Message;

import java.util.List;
import java.util.UUID;

public interface MessageService {
    Message savePersonal(CreateMessageDto dto);
    Message saveGroup(CreateGroupMessageDto dto);
    Message getById(UUID id);
    boolean delete(UUID id);
    Message update(UUID id , String content);
    List<Message> getAll();
    List<Message> getAllByReceiverAndSender(UUID receiverId);
    List<Message> getAllByGroup(UUID group);
}
