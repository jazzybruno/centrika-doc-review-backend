package rw.ac.rca.centrika.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rw.ac.rca.centrika.configs.OpenApiConfigs;
import rw.ac.rca.centrika.models.Notification;
import rw.ac.rca.centrika.models.User;

import java.util.List;
import java.util.UUID;

public interface INotificationRepository extends JpaRepository<Notification, UUID> {
    List<Notification> findAllByUser(User user);
    @Query(value = "SELECT * FROM notification WHERE user_id = ?1 AND is_read = ?2", nativeQuery = true)
    List<Notification> getAllByUserAndRead(UUID user , boolean read);
}