package backbone.dao;

import backbone.entity.Notification;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Component
public class NotificationDaoInMemory implements NotificationDao {

    private AtomicLong nextId = new AtomicLong(3);

    private List<Notification> notifications = new ArrayList<>();

    @PostConstruct
    public void init() {
        notifications.add( new Notification(1L, "Hello,\nI need to switch to winter tires.\nBest Regards,\nJohn"));
        notifications.add(new Notification(2L, "Bonjour, Le voyant SERVICE s'est allumé. Paul."));
    }

    @Override
    public List<Notification> findAll() {
        return new ArrayList<>(notifications);
    }

    @Override
    public Notification create(Notification notification) {
        notification.setId(nextId.getAndIncrement());
        notifications.add(notification);
        return notification;
    }

    @Override
    public void delete(Long id) {
        notifications = notifications.stream().filter(notification -> notification.getId() != id).collect(Collectors.toList());
    }
}
