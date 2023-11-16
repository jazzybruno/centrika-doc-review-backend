package rw.ac.rca.centrika.services.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rw.ac.rca.centrika.dtos.requests.CreateGroupMessageDto;
import rw.ac.rca.centrika.dtos.requests.CreateMessageDto;
import rw.ac.rca.centrika.exceptions.InternalServerErrorException;
import rw.ac.rca.centrika.exceptions.NotFoundException;
import rw.ac.rca.centrika.models.Group;
import rw.ac.rca.centrika.models.Message;
import rw.ac.rca.centrika.models.User;
import rw.ac.rca.centrika.repositories.GroupRepository;
import rw.ac.rca.centrika.repositories.IUserRepository;
import rw.ac.rca.centrika.repositories.MessageRepository;
import rw.ac.rca.centrika.services.MessageService;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private IUserRepository userRepository;
    private MessageRepository messageRepository;
    private GroupRepository groupRepository;
    public final String internalServerErrorMessage = "Failed try again!!";

    @Override
    public Message savePersonal(CreateMessageDto dto) {
        User sender = userRepository.findById(dto.getSenderId()).orElseThrow(()-> {throw new NotFoundException("The sender was not found");});
        User receiver = userRepository.findById(dto.getReceiverId()).orElseThrow(()-> {throw new NotFoundException("The receiver was not found");});
        Message message = new Message();
        message.setContent(dto.getContent());
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setCreatedAt(new Date());
        try {
            messageRepository.save(message);
            return message;
        }catch (Exception e){
            throw new InternalServerErrorException(internalServerErrorMessage);
        }
    }

    @Override
    public Message saveGroup(CreateGroupMessageDto dto) {
        Group group = groupRepository.findById(dto.getGroupId()).orElseThrow(()-> {throw new NotFoundException("The Group was not found");});
        User sender = userRepository.findById(dto.getSenderId()).orElseThrow(()-> {throw new NotFoundException("The sender was not found");});
        Message message = new Message();
        message.setContent(dto.getContent());
        message.setSender(sender);
        message.setGroup(group);
        message.setCreatedAt(new Date());
        try {
            messageRepository.save(message);
            return message;
        }catch (Exception e){
            throw new InternalServerErrorException(internalServerErrorMessage);
        }
    }

    @Override
    public Message getById(UUID id) {
        try {
            return messageRepository.findById(id).orElseThrow(()-> {throw new NotFoundException("The message was not found");
            });
        }catch (Exception e){
            throw new InternalServerErrorException(internalServerErrorMessage);
        }
    }

    @Override
    public boolean delete(UUID id) {
        Message message = this.getById(id);
        try {
            messageRepository.deleteById(id);
            return true;
        }catch (Exception e){
            throw new InternalServerErrorException(internalServerErrorMessage);
        }
    }

    @Override
    @Transactional
    public Message update(UUID id, String content) {
        Message message = this.getById(id);
        try {
            message.setContent(content);
            message.setUpdatedAt(new Date());
            return message;
        }catch (Exception e){
            throw new InternalServerErrorException(internalServerErrorMessage);
        }
    }

    @Override
    public List<Message> getAll() {
        try {
            return messageRepository.findAll();
        }catch (Exception e){
            throw new InternalServerErrorException(internalServerErrorMessage);
        }
    }

    @Override
    public List<Message> getAllByReceiverAndSender(UUID senderId ,  UUID receiverId) {
        User sender = userRepository.findById(senderId).orElseThrow(()-> {throw new NotFoundException("The sender was not found");});
        User receiver = userRepository.findById(receiverId).orElseThrow(()-> {throw new NotFoundException("The receiver was not found");});
        try {
            return messageRepository.findAllBySenderAndReceiver(sender , receiver);
        }catch (Exception e){
            throw new InternalServerErrorException(internalServerErrorMessage);
        }
    }

    @Override
    public List<Message> getAllByGroup(UUID groupId) {
        Group group = groupRepository.findById(groupId).orElseThrow(()-> {throw new NotFoundException("The Group was not found");});
        try {
            return messageRepository.findAllByGroup(group);
        }catch (Exception e){
            throw new InternalServerErrorException(internalServerErrorMessage);
        }
    }
}
