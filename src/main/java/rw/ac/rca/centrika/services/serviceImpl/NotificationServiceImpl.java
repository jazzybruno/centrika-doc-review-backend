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

import java.util.Date;
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
    public Notification createdNotification(CreateNotificationDTO createNotificationDTO) {
        User sender = userService.getUserById(createNotificationDTO.getSender());
        User receiver = userService.getUserById(createNotificationDTO.getReceiver());
        try {
             Notification notification = new Notification(
                     sender,
                     receiver,
                     createNotificationDTO.getMessage()
             );
            Date date = new Date();
            notification.setCreatedAt(date);
            notification.setRead(false);
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
    @Transactional
    public Notification markAsRead(UUID notId) {
        Notification notification = this.getNotificationById(notId);
        try {
            notification.setRead(true);
            return notification;
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public List<Notification> markAllAsRead(UUID receiverId) {
        User user = userService.getUserById(receiverId);
        try {
            List<Notification> notifications = notificationRepository.findAllByReceiver(user);
            notifications.forEach(notification -> {
                notification.setRead(true);
            });
            return notifications;
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public List<Notification> getAllReadByReceiver(UUID receiverId) {
        User receiver = userService.getUserById(receiverId);
        try {
            return notificationRepository.findAllByReceiverAndRead(receiver , true);
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public List<Notification> getAllReadBySender(UUID senderId) {
        User sender = userService.getUserById(senderId);
        try {
            return notificationRepository.findAllBySenderAndRead(sender , true);
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public List<Notification> getAllUnReadByReceiver(UUID receiverId) {
        User receiver = userService.getUserById(receiverId);
        try {
            return notificationRepository.findAllByReceiverAndRead(receiver , false);
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public List<Notification> getAllUnReadBySender(UUID senderId) {
        User sender = userService.getUserById(senderId);
        try {
            return notificationRepository.findAllBySenderAndRead(sender , false);
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public List<Notification> getAllNotificationsBySenderUser(UUID senderId) {
        User sender = userService.getUserById(senderId);
        try {
            return notificationRepository.findAllBySender(sender);
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public List<Notification> getAllNotificationsByReceiverUser(UUID receiverId) {
        User receiver = userService.getUserById(receiverId);
        try {
            return notificationRepository.findAllByReceiver(receiver);
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

}
