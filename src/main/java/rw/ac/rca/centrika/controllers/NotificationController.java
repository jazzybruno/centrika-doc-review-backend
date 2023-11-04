package rw.ac.rca.centrika.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.rca.centrika.dtos.requests.CreateNotificationDTO;
import rw.ac.rca.centrika.dtos.requests.UpdateNotificationDTO;
import rw.ac.rca.centrika.models.Notification;
import rw.ac.rca.centrika.services.NotificationService;
import rw.ac.rca.centrika.utils.ApResponse;
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
                "Successfully fetched all the notifications",
                notifications
        ));
    }

    @GetMapping("/{notificationId}")
    public ResponseEntity getNotificationById(@PathVariable UUID notificationId) {
        Notification notification = notificationService.getNotificationById(notificationId);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully fetched the notification by id",
                notification
        ));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity getAllNotificationsByUser(@PathVariable UUID userId) {
        List<Notification> notifications = notificationService.getAllNotificationsByUser(userId);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully fetched all the notifications by user",
                notifications
        ));
    }

    @GetMapping("/user/read/{userId}")
    public ResponseEntity getAllNotificationsByUserRead(@PathVariable UUID userId) {
        List<Notification> notifications = notificationService.getAllRead(userId);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully fetched all the read notifications of the user",
                notifications
        ));
    }

    @GetMapping("/user/unread/{userId}")
    public ResponseEntity getAllNotificationsByUserUnRead(@PathVariable UUID userId) {
        List<Notification> notifications = notificationService.getAllUnRead(userId);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully fetched all the unread notifications",
                notifications
        ));
    }

    @PostMapping("/create")
    public ResponseEntity createNotification(@RequestBody CreateNotificationDTO createNotificationDTO) {
        Notification notification = notificationService.createdNotification(createNotificationDTO);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully created the notification",
                notification
        ));
    }

    @PutMapping("/{notificationId}")
    public ResponseEntity updateNotification(@PathVariable UUID notificationId, @RequestBody UpdateNotificationDTO updateNotificationDTO) {
        Notification notification = notificationService.updateNotification(notificationId, updateNotificationDTO);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully updated the notification",
                notification
        ));
    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity deleteNotification(@PathVariable UUID notificationId) {
        Notification notification = notificationService.deleteNotification(notificationId);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully deleted the notifications",
                notification
        ));
    }

    @PutMapping("/mark-as-read/{notificationId}")
    public ResponseEntity markNotificationAsRead(@PathVariable UUID notificationId) {
        Notification notification = notificationService.markAsRead(notificationId);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully marked the notification as read",
                notification
        ));
    }

    @PutMapping("/user/mark-as-read/all/{userId}")
    public ResponseEntity markNotificationAllAsRead(@PathVariable UUID userId) {
        List<Notification> notifications = notificationService.markAllAsRead(userId);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully marked all the notifications as read",
                notifications
        ));
    }
}
