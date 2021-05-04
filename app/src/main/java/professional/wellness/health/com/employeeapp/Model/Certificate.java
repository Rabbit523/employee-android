package professional.wellness.health.com.employeeapp.Model;

/**
 * Created by Admin on 13-07-2017.
 */

public class Certificate {
    private String certificate_id;
    private String user_id;
    private String subject;
    private String description;
    private String type;
    private String status;
    private String date;
    private String created_at;
    private String updated_at;
    private String file_path;


    public Certificate(String certificate_id, String user_id, String subject, String description, String type, String status, String date, String created_at, String updated_at, String file_path) {
        this.certificate_id = certificate_id;
        this.user_id = user_id;
        this.subject = subject;
        this.description = description;
        this.type = type;
        this.status = status;
        this.date = date;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.file_path = file_path;
    }

    public String getCertificate_id() {
        return certificate_id;
    }

    public void setCertificate_id(String certificate_id) {
        this.certificate_id = certificate_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }
}
