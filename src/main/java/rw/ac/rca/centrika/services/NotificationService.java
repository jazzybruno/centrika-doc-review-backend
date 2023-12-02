package rw.ac.rca.centrika.services;

import rw.ac.rca.centrika.dtos.requests.CreateNotificationDTO;
import rw.ac.rca.centrika.dtos.requests.UpdateNotificationDTO;
import rw.ac.rca.centrika.models.Notification;

import java.util.List;
import java.util.UUID;

public interface NotificationService {
    public List<Notification> getAllNotifications();
    public Notification getNotificationById(UUID notId);
    public Notification createdNotification(CreateNotificationDTO createNotificationDTO);
    public Notification updateNotification(UUID notId , UpdateNotificationDTO updateNotificationDTO);
    public Notification deleteNotification(UUID notId);

    // other methods
    public Notification markAsRead(UUID notId);
    public List<Notification> markAllAsRead(UUID userId);
    public List<Notification> getAllRead(UUID userId);
    public List<Notification> getAllUnRead(UUID userId);
    public List<Notification> getAllNotificationsBySenderUser(UUID senderId);
    public List<Notification> getAllNotificationsByReceiverUser(UUID receiverId);
}
