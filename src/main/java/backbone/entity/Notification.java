package backbone.entity;

public class Notification {

    private Long id;
    private String title;
    private String content;
    private NotificationStatus status;

    public Notification() {
    }

    public Notification(Long id, String title, String content, NotificationStatus status) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public NotificationStatus getStatus() {
        return status;
    }

    public void setStatus(NotificationStatus status) {
        this.status = status;
    }
}
