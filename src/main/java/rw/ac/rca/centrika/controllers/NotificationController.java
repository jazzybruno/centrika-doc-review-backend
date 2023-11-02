package rw.ac.rca.centrika.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.rca.centrika.dtos.requests.CreateNotificationDTO;
import rw.ac.rca.centrika.dtos.requests.UpdateNotificationDTO;
import rw.ac.rca.centrika.models.Notification;
import rw.ac.rca.centrika.services.NotificationService;
import rw.ac.rca.centrika.utils.ApResponse;

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
        return ResponseEntity.ok(ApResponse.success(notifications));
    }

    @GetMapping("/{notificationId}")
    public ResponseEntity getNotificationById(@PathVariable UUID notificationId) {
        Notification notification = notificationService.getNotificationById(notificationId);
        return ResponseEntity.ok(ApResponse.success(notification));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity getAllNotificationsByUser(@PathVariable UUID userId) {
        List<Notification> notifications = notificationService.getAllNotificationsByUser(userId);
        return ResponseEntity.ok(ApResponse.success(notifications));
    }

    @PostMapping("/create")
    public ResponseEntity createNotification(@RequestBody CreateNotificationDTO createNotificationDTO) {
        Notification notification = notificationService.createdNotification(createNotificationDTO);
        return ResponseEntity.ok(ApResponse.success(notification));
    }

    @PutMapping("/{notificationId}")
    public ResponseEntity updateNotification(@PathVariable UUID notificationId, @RequestBody UpdateNotificationDTO updateNotificationDTO) {
        Notification notification = notificationService.updateNotification(notificationId, updateNotificationDTO);
        return ResponseEntity.ok(ApResponse.success(notification));
    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity deleteNotification(@PathVariable UUID notificationId) {
        Notification notification = notificationService.deleteNotification(notificationId);
        return ResponseEntity.ok(ApResponse.success(notification));
    }

    @PutMapping("/mark-as-read/{notificationId}")
    public ResponseEntity markNotificationAsRead(@PathVariable UUID notificationId) {
        Notification notification = notificationService.markAsRead(notificationId);
        return ResponseEntity.ok(ApResponse.success(notification));
    }
}
