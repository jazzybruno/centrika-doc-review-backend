package rw.ac.rca.centrika.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IHistoryRepository extends JpaRepository<History, UUID> {
}