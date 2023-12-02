package rw.ac.rca.centrika.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.rca.centrika.models.Group;
import rw.ac.rca.centrika.models.Message;
import rw.ac.rca.centrika.models.User;

import java.util.List;
import java.util.UUID;

public interface IMessageRepository extends JpaRepository<Message, UUID> {
    List<Message> findAllBySenderAndReceiver(User sender , User receiver);
    List<Message> findAllByGroup(Group group);
}