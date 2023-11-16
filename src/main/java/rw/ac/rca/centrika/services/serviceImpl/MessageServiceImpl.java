package rw.ac.rca.centrika.services.serviceImpl;

import rw.ac.rca.centrika.dtos.requests.CreateGroupMessageDto;
import rw.ac.rca.centrika.dtos.requests.CreateMessageDto;
import rw.ac.rca.centrika.models.Message;
import rw.ac.rca.centrika.services.MessageService;

import java.util.List;
import java.util.UUID;

public class MessageServiceImpl implements MessageService {

    @Override
    public Message savePersonal(CreateMessageDto dto) {
        return null;
    }

    @Override
    public Message saveGroup(CreateGroupMessageDto dto) {
        return null;
    }

    @Override
    public Message getById(UUID id) {
        return null;
    }

    @Override
    public boolean delete(UUID id) {
        return false;
    }

    @Override
    public Message update(UUID id, String content) {
        return null;
    }

    @Override
    public List<Message> getAll() {
        return null;
    }

    @Override
    public List<Message> getAllByReceiverAndSender(UUID receiverId) {
        return null;
    }

    @Override
    public List<Message> getAllByGroup(UUID group) {
        return null;
    }
}
