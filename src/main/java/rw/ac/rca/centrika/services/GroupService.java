package rw.ac.rca.centrika.services;

import rw.ac.rca.centrika.dtos.requests.CreateGroupDto;
import rw.ac.rca.centrika.models.Group;
import java.util.List;
import java.util.UUID;

public interface GroupService {
    Group save(CreateGroupDto dto);
    Group update(UUID id , CreateGroupDto dto);
    boolean delete(UUID id);
    Group addUser(UUID groupId , UUID userId);
    Group removeUser(UUID groupId , UUID userId);
    Group getById(UUID id);
    List<Group> getAll();
}