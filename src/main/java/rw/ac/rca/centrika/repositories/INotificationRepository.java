package rw.ac.rca.centrika.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rw.ac.rca.centrika.configs.OpenApiConfigs;
import rw.ac.rca.centrika.models.Notification;
import rw.ac.rca.centrika.models.User;

import java.util.List;
import java.util.UUID;

public interface INotificationRepository extends JpaRepository<Notification, UUID> {
    List<Notification> findAllBySender(User user);
    List<Notification> findAllByReceiver(User user);

    List<Notification> findAllBySenderAndRead(User user , boolean isRead);
    List<Notification> findAllByReceiverAndRead(User user , boolean isRead);


}