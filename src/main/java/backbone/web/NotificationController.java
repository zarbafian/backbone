package backbone.web;

import backbone.dao.NotificationDao;
import backbone.entity.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NotificationController {

    @Autowired
    private NotificationDao notificationDao;

    @RequestMapping(
            value = "/api/notifications",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Notification>> listAllNotifications() {

        return new ResponseEntity<>(notificationDao.findAll(), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/notifications",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Notification> createNotification(@RequestBody Notification notification) {

        Notification persistedNotification = notificationDao.create(notification);

        return new ResponseEntity<>(notification, HttpStatus.CREATED);
    }

    @RequestMapping(
            value = "/api/notifications/{id}",
            method = RequestMethod.DELETE
    )
    public ResponseEntity<Notification> deleteNotification(@PathVariable(value = "id")Long id) {

        notificationDao.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
