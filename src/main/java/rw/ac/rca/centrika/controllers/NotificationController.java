package rw.ac.rca.centrika.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.rca.centrika.dtos.requests.CreateNotificationDTO;
import rw.ac.rca.centrika.dtos.requests.UpdateNotificationDTO;
import rw.ac.rca.centrika.models.Notification;
import rw.ac.rca.centrika.services.NotificationService;
import rw.ac.rca.centrika.utils.ApiResponse;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.OPTIONS})
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public ResponseEntity getAllNotifications() {
        List<Notification> notifications = notificationService.getAllNotifications();
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully fetched all notifications",
                notifications
        ));
    }

    @GetMapping("/{notId}")
    public ResponseEntity getNotificationById(@PathVariable UUID notId) {
        Notification notification = notificationService.getNotificationById(notId);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully fetched notification by id",
                notification
        ));
    }

    @PostMapping("/create")
    public ResponseEntity createNotification(@RequestBody CreateNotificationDTO createNotificationDTO) {
        Notification notification = notificationService.createdNotification(createNotificationDTO);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully created notification",
                notification
        ));
    }

    @PutMapping("/{notId}")
    public ResponseEntity updateNotification(@PathVariable UUID notId, @RequestBody UpdateNotificationDTO updateNotificationDTO) {
        Notification notification = notificationService.updateNotification(notId, updateNotificationDTO);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully updated notification",
                notification
        ));
    }

    @DeleteMapping("/{notId}")
    public ResponseEntity deleteNotification(@PathVariable UUID notId) {
        Notification notification = notificationService.deleteNotification(notId);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully deleted notification",
                notification
        ));
    }

    @PutMapping("/mark-as-read/{notId}")
    public ResponseEntity markAsRead(@PathVariable UUID notId) {
        Notification notification = notificationService.markAsRead(notId);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully marked notification as read",
                notification
        ));
    }

    @PutMapping("/mark-all-as-read/{receiverId}")
    public ResponseEntity markAllAsRead(@PathVariable UUID receiverId) {
        List<Notification> notifications = notificationService.markAllAsRead(receiverId);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully marked all notifications as read",
                notifications
        ));
    }

    @GetMapping("/read-by-receiver/{receiverId}")
    public ResponseEntity getAllReadByReceiver(@PathVariable UUID receiverId) {
        List<Notification> notifications = notificationService.getAllReadByReceiver(receiverId);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully fetched all read notifications by receiver",
                notifications
        ));
    }

    @GetMapping("/read-by-sender/{senderId}")
    public ResponseEntity getAllReadBySender(@PathVariable UUID senderId) {
        List<Notification> notifications = notificationService.getAllReadBySender(senderId);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully fetched all read notifications by sender",
                notifications
        ));
    }

    @GetMapping("/unread-by-receiver/{receiverId}")
    public ResponseEntity getAllUnReadByReceiver(@PathVariable UUID receiverId) {
        List<Notification> notifications = notificationService.getAllUnReadByReceiver(receiverId);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully fetched all unread notifications by receiver",
                notifications
        ));
    }

    @GetMapping("/unread-by-sender/{senderId}")
    public ResponseEntity getAllUnReadBySender(@PathVariable UUID senderId) {
        List<Notification> notifications = notificationService.getAllUnReadBySender(senderId);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully fetched all unread notifications by sender",
                notifications
        ));
    }

    @GetMapping("/by-sender/{senderId}")
    public ResponseEntity getAllNotificationsBySenderUser(@PathVariable UUID senderId) {
        List<Notification> notifications = notificationService.getAllNotificationsBySenderUser(senderId);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully fetched all notifications by sender user",
                notifications
        ));
    }

    @GetMapping("/by-receiver/{receiverId}")
    public ResponseEntity getAllNotificationsByReceiverUser(@PathVariable UUID receiverId) {
        List<Notification> notifications = notificationService.getAllNotificationsByReceiverUser(receiverId);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully fetched all notifications by receiver user",
                notifications
        ));
    }
}
