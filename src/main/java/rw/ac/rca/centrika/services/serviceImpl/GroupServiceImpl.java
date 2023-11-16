package rw.ac.rca.centrika.services.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rw.ac.rca.centrika.dtos.requests.CreateGroupDto;
import rw.ac.rca.centrika.exceptions.InternalServerErrorException;
import rw.ac.rca.centrika.exceptions.NotFoundException;
import rw.ac.rca.centrika.models.Group;
import rw.ac.rca.centrika.models.User;
import rw.ac.rca.centrika.repositories.GroupRepository;
import rw.ac.rca.centrika.repositories.IUserRepository;
import rw.ac.rca.centrika.services.GroupService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private IUserRepository userRepository;
    private GroupRepository groupRepository;
    private final String internalServerError = "Failed Try Again!!";
    @Override
    public Group save(CreateGroupDto dto) {
        Group group = new Group();
        group.setName(dto.getName());
        group.setDescription(dto.getDescription());
        group.setMembers(new HashSet<User>());
        try {
            groupRepository.save(group);
            return group;
        }catch (Exception e){
            throw new InternalServerErrorException(internalServerError);
        }
    }

    @Override
    public Group update(UUID id, CreateGroupDto dto) {
        Group group = this.getById(id);
        try {
            group.setName(dto.getName());
            group.setDescription(dto.getDescription());
            return group;
        }catch (Exception e){
            throw new InternalServerErrorException(internalServerError);
        }
    }

    @Override
    public boolean delete(UUID id) {
        Group group = this.getById(id);
        try {
           groupRepository.deleteById(id);
           return true;
        }catch (Exception e){
            throw new InternalServerErrorException(internalServerError);
        }
    }

    @Override
    @Transactional
    public Group addUser(UUID groupId, UUID userId) {
        Group group = this.getById(groupId);
        User user = userRepository.findById(userId).orElseThrow(()->{throw new NotFoundException("The user was not found");});
        try {
            Set<User> users = group.getMembers();
            users.add(user);
            group.setMembers(users);
            return group;
        }catch (Exception e){
            throw new InternalServerErrorException(internalServerError);
        }
    }

    @Override
    @Transactional
    public Group removeUser(UUID groupId, UUID userId) {
        Group group = this.getById(groupId);
        User user = userRepository.findById(userId).orElseThrow(()->{throw new NotFoundException("The user was not found");});
        try {
            Set<User> users = group.getMembers();
            users.remove(user);
            group.setMembers(users);
            return group;
        }catch (Exception e){
            throw new InternalServerErrorException(internalServerError);
        }
    }

    @Override
    public Group getById(UUID id) {
        try {
           return groupRepository.findById(id).orElseThrow(()->{throw new NotFoundException("The group was not found");
           });
        }catch (Exception e){
            throw new InternalServerErrorException(internalServerError);
        }
    }

    @Override
    public List<Group> getAll() {
        try {
            return groupRepository.findAll();
        }catch (Exception e){
            throw new InternalServerErrorException(internalServerError);
        }
    }

    @Override
    public List<Group> getAllByUser(UUID id) {
        User user = userRepository.findById(id).orElseThrow(()->{throw new NotFoundException("The user was not found");});
        try {
          return groupRepository.findAllByMembersContains(user);
        }catch (Exception e){
            throw new InternalServerErrorException(internalServerError);
        }
    }
}
