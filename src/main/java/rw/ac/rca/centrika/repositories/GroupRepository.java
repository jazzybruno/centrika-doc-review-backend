package rw.ac.rca.centrika.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.rca.centrika.models.Group;

import java.util.UUID;

public interface GroupRepository extends JpaRepository<Group, UUID> {
}