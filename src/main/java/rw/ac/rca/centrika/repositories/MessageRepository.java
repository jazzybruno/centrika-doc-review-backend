package rw.ac.rca.centrika.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.rca.centrika.models.Message;

import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {
}