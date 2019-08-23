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
public class CustomerController {

    @Autowired
    private NotificationDao notificationDao;

    @RequestMapping(
            value = "/cmc/notifications",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Notification> createNotification(@RequestBody Notification notification) {

        Notification persistedNotification = notificationDao.create(notification);

        return new ResponseEntity<>(notification, HttpStatus.CREATED);
    }
}
