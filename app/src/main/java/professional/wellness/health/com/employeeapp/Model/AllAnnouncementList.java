package professional.wellness.health.com.employeeapp.Model;

/**
 * Created by Fujitsu on 13-07-2017.
 */

public class AllAnnouncementList {

    private String id = "";
    private String title = "";
    private String description = "";
    private String visible_providers = "";
    private String email_alert = "";
    private String notification_alert = "";
    private String status = "";
    private String stable_time = "";
    private String created_at = "";
    private String image_path = "";

    public AllAnnouncementList(String id, String title, String description, String visible_providers, String email_alert, String notification_alert, String status, String stable_time, String created_at, String image_path) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.visible_providers = visible_providers;
        this.email_alert = email_alert;
        this.notification_alert = notification_alert;
        this.status = status;
        this.stable_time = stable_time;
        this.created_at = created_at;
        this.image_path = image_path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVisible_providers() {
        return visible_providers;
    }

    public void setVisible_providers(String visible_providers) {
        this.visible_providers = visible_providers;
    }

    public String getEmail_alert() {
        return email_alert;
    }

    public void setEmail_alert(String email_alert) {
        this.email_alert = email_alert;
    }

    public String getNotification_alert() {
        return notification_alert;
    }

    public void setNotification_alert(String notification_alert) {
        this.notification_alert = notification_alert;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStable_time() {
        return stable_time;
    }

    public void setStable_time(String stable_time) {
        this.stable_time = stable_time;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }
}
