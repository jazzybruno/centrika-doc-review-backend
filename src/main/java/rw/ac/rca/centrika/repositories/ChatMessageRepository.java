package rw.ac.rca.centrika.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.rca.centrika.models.ChatMessage;

import java.util.UUID;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, UUID> {
}