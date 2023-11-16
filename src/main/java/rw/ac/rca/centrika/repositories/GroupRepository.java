package rw.ac.rca.centrika.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.rca.centrika.models.Group;
import rw.ac.rca.centrika.models.User;

import java.util.List;
import java.util.UUID;

public interface GroupRepository extends JpaRepository<Group, UUID> {
    List<Group> findAllByMembersContains(User user);
}