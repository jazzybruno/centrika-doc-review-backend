package rw.ac.rca.centrika.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.rca.centrika.dtos.requests.CreateGroupDto;
import rw.ac.rca.centrika.models.Group;
import rw.ac.rca.centrika.services.serviceImpl.GroupServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController()
@RequestMapping(value = "/api/groups")
@Slf4j
@RequiredArgsConstructor
public class GroupController{
    private GroupServiceImpl groupService;
    private final String  ENTITY = "Group";

    @PostMapping(value = "/create")
    public ResponseEntity<Group> save(@RequestBody CreateGroupDto dto) {
        log.info("Created an entity of type {} " , ENTITY);
        return ResponseEntity.status(HttpStatus.CREATED).body(this.groupService.save(dto));
    }

   @PutMapping("/update/{id}")
    public ResponseEntity<Group> update(@PathVariable(value = "id") UUID id, @RequestBody CreateGroupDto dto) {
       log.info("Updated an entity of type {} with id : {}" , ENTITY , id);
       return ResponseEntity.status(HttpStatus.OK).body(this.groupService.update(id , dto));
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable(value = "id") UUID id) {
        log.info("Deleted an entity of type {} with id : {}" , ENTITY , id);
        return ResponseEntity.status(HttpStatus.OK).body(this.groupService.delete(id));
    }

    @PatchMapping("/add/user/{userId}/group/{groupId}")
    public ResponseEntity<Group> addUser(@PathVariable(value = "groupId") UUID groupId, @PathVariable(value = "userId") UUID userId) {
        log.info("Updated an entity of type {} with id : {}" , ENTITY , groupId);
        return ResponseEntity.status(HttpStatus.OK).body(this.groupService.addUser(groupId , userId));
    }

    @PatchMapping("/remove/user/{userId}/group/{groupId}")
    public ResponseEntity<Group> removeUser(@PathVariable(value = "groupId") UUID groupId, @PathVariable(value = "userId") UUID userId) {
        log.info("Updated an entity of type {} with id : {}" , ENTITY , groupId);
        return ResponseEntity.status(HttpStatus.OK).body(this.groupService.removeUser(groupId , userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Group> getById(@PathVariable(value = "id") UUID id) {
        log.info("Fetched an entity of type {} with id : {}" , ENTITY , id);
        return ResponseEntity.status(HttpStatus.OK).body(this.groupService.getById(id));
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<Group>> getAll() {
        log.info("Fetched entities of type {}" , ENTITY);
        return ResponseEntity.status(HttpStatus.OK).body(this.groupService.getAll());
    }

    @GetMapping(value = "/all/user/{id}")
    public ResponseEntity<List<Group>> getAllByUser(@PathVariable(value = "id") UUID id) {
        log.info("Fetched entities of type {} with user with id : {}" , ENTITY , id);
        return ResponseEntity.status(HttpStatus.OK).body(this.groupService.getAllByUser(id));
    }
}
