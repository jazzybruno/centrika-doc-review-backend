package rw.ac.rca.centrika.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.rca.centrika.dtos.requests.CreateGroupMessageDto;
import rw.ac.rca.centrika.dtos.requests.CreateMessageDto;
import rw.ac.rca.centrika.models.Message;
import rw.ac.rca.centrika.services.serviceImpl.MessageServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/messages/")
@Slf4j
@RequiredArgsConstructor
public class MessageController {
    private final String ENTITY = "Message";
    private MessageServiceImpl messageService;

    @PostMapping(value = "/create/dm")
    public ResponseEntity<Message> savePersonal(@RequestBody CreateMessageDto dto) {
        log.info("The Entity {} of a direct message was saved" , ENTITY);
         return ResponseEntity.status(HttpStatus.CREATED).body(this.messageService.savePersonal(dto));
    }

    @PostMapping(value = "/create/group")
    public ResponseEntity<Message> saveGroup(@RequestBody CreateGroupMessageDto dto) {
        log.info("The Entity {} of a group message was saved" , ENTITY);
        return ResponseEntity.status(HttpStatus.CREATED).body(this.messageService.saveGroup(dto));
    }

    @GetMapping(value = "/id/{id}")
    public ResponseEntity<Message> getById(@PathVariable(value = "id") UUID id) {
        log.info("Fetched Entity of type {} with id {}" , ENTITY , id);
        return ResponseEntity.status(HttpStatus.OK).body(this.messageService.getById(id));
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable(value = "id") UUID id) {
        log.info("Deleted Entity of type {} with id {}" , ENTITY , id);
        return ResponseEntity.status(HttpStatus.OK).body(this.messageService.delete(id));
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<Message> update(@PathVariable(value = "id") UUID id, @RequestParam(value = "content") String content) {
        log.info("Updated Entity of type {} with id {}" , ENTITY , id);
        return ResponseEntity.status(HttpStatus.OK).body(this.messageService.update(id , content));
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<Message>> getAll() {
        log.info("Fetched All Entities of type {}" , ENTITY );
        return ResponseEntity.status(HttpStatus.OK).body(this.messageService.getAll());
    }

    @GetMapping("/sender/{senderId}/receiver/{receiverId}")
    public ResponseEntity<List<Message>> getAllByReceiverAndSender(@PathVariable(value = "senderId") UUID senderId, @PathVariable(value = "receiverId" ) UUID receiverId) {
        log.info("Fetched Entity of type {} with sender id {} and receiver id {} " , ENTITY , senderId , receiverId);
        return ResponseEntity.status(HttpStatus.OK).body(this.messageService.getAllByReceiverAndSender(senderId  , receiverId));
    }

    @GetMapping(value = "/group/{id}")
    public ResponseEntity<List<Message>> getAllByGroup(@PathVariable(value = "id") UUID group) {
        log.info("Fetched Entity of type {} with group id {} " , ENTITY , group);
        return ResponseEntity.status(HttpStatus.OK).body(this.messageService.getAllByGroup(group));
    }
}
