package rw.ac.rca.centrika.services.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.ac.rca.centrika.dtos.requests.CreateNotificationDTO;
import rw.ac.rca.centrika.dtos.requests.UpdateNotificationDTO;
import rw.ac.rca.centrika.exceptions.InternalServerErrorException;
import rw.ac.rca.centrika.exceptions.NotFoundException;
import rw.ac.rca.centrika.models.Notification;
import rw.ac.rca.centrika.models.User;
import rw.ac.rca.centrika.repositories.INotificationRepository;
import rw.ac.rca.centrika.services.NotificationService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final INotificationRepository notificationRepository;
    private final UserServiceImpl userService;
    @Override
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    @Override
    public Notification getNotificationById(UUID notId) {
        return notificationRepository.findById(notId).orElseThrow(()-> {throw new NotFoundException("The notification was not found");
        });
    }

    @Override
    public List<Notification> getAllNotificationsByUser(UUID userId) {
        User user = userService.getUserById(userId);
        try {
            return notificationRepository.findAllByUser(user);
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public Notification createdNotification(CreateNotificationDTO createNotificationDTO) {
        User user = userService.getUserById(createNotificationDTO.getUser());
        boolean isRead = false;
        try {
             Notification notification = new Notification(
                     user,
                     createNotificationDTO.getMessage(),
                     isRead
             );
             notificationRepository.save(notification);
             return notification;
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Notification updateNotification(UUID notId, UpdateNotificationDTO updateNotificationDTO){
        Notification notification = this.getNotificationById(notId);
        try {
            notification.setMessage(updateNotificationDTO.message);
            return notification;
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public Notification deleteNotification(UUID notId) {
        Notification notification = this.getNotificationById(notId);
        try {
            notificationRepository.deleteById(notId);
            return notification;
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public Notification markAsRead(UUID notId) {
        Notification notification = this.getNotificationById(notId);
        try {
            notification.setRead(true);
            return notification;
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }
}
