package backbone.dao;

import backbone.entity.Notification;

import java.util.List;

public interface NotificationDao {

    List<Notification> findAll();
    Notification create(Notification notification);
    void delete(Long id);
}
