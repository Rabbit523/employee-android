package professional.wellness.health.com.employeeapp.Model;

/**
 * Created by Admin on 17-07-2017.
 */

public class HomeFeed {
    private String id;
    private String title;
    private String image;
    private String description;
    private String visible_providers;
    private String visible_cities;
    private String notification_alert;
    private String email_alert;
    private String stable_time;
    private String status;
    private String created_at;
    private String updated_at;

    private String primary_name;
    private String medtech_name;

    private String image_path;
    private String provider_id;
    private String name;
    private String phone;
    private String location_name;
    private String latitude;
    private String longitude;
    private String time;
    private String date;
    private String clock_in;
    private String clock_out;
    private String type;
    private String prep_time;
    private String estimated_duration;
    private String personnel;
    private String service_provider;
    private String jay_walters;
    private String clocked;

    public HomeFeed(String id, String title, String image, String description, String visible_providers, String visible_cities, String notification_alert, String email_alert, String stable_time, String status, String created_at, String updated_at, String primary_name, String medtech_name, String image_path, String provider_id, String name, String phone, String location_name, String latitude, String longitude, String time, String date, String clock_in, String clock_out, String type, String prep_time, String estimated_duration, String personnel, String service_provider, String jay_walters, String clocked) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.description = description;
        this.visible_providers = visible_providers;
        this.visible_cities = visible_cities;
        this.notification_alert = notification_alert;
        this.email_alert = email_alert;
        this.stable_time = stable_time;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.primary_name = primary_name;
        this.medtech_name = medtech_name;
        this.image_path = image_path;
        this.provider_id = provider_id;
        this.name = name;
        this.phone = phone;
        this.location_name = location_name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.time = time;
        this.date = date;
        this.clock_in = clock_in;
        this.clock_out = clock_out;
        this.type = type;
        this.prep_time = prep_time;
        this.estimated_duration = estimated_duration;
        this.personnel = personnel;
        this.service_provider = service_provider;
        this.jay_walters = jay_walters;
        this.clocked = clocked;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getVisible_cities() {
        return visible_cities;
    }

    public void setVisible_cities(String visible_cities) {
        this.visible_cities = visible_cities;
    }

    public String getNotification_alert() {
        return notification_alert;
    }

    public void setNotification_alert(String notification_alert) {
        this.notification_alert = notification_alert;
    }

    public String getEmail_alert() {
        return email_alert;
    }

    public void setEmail_alert(String email_alert) {
        this.email_alert = email_alert;
    }

    public String getStable_time() {
        return stable_time;
    }

    public void setStable_time(String stable_time) {
        this.stable_time = stable_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getPrimary_name() {
        return primary_name;
    }

    public void setPrimary_name(String primary_name) {
        this.primary_name = primary_name;
    }

    public String getMedtech_name() {
        return medtech_name;
    }

    public void setMedtech_name(String medtech_name) {
        this.medtech_name = medtech_name;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public String getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(String provider_id) {
        this.provider_id = provider_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getClock_in() {
        return clock_in;
    }

    public void setClock_in(String clock_in) {
        this.clock_in = clock_in;
    }

    public String getClock_out() {
        return clock_out;
    }

    public void setClock_out(String clock_out) {
        this.clock_out = clock_out;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrep_time() {
        return prep_time;
    }

    public void setPrep_time(String prep_time) {
        this.prep_time = prep_time;
    }

    public String getEstimated_duration() {
        return estimated_duration;
    }

    public void setEstimated_duration(String estimated_duration) {
        this.estimated_duration = estimated_duration;
    }

    public String getPersonnel() {
        return personnel;
    }

    public void setPersonnel(String personnel) {
        this.personnel = personnel;
    }

    public String getService_provider() {
        return service_provider;
    }

    public void setService_provider(String service_provider) {
        this.service_provider = service_provider;
    }

    public String getJay_walters() {
        return jay_walters;
    }

    public void setJay_walters(String jay_walters) {
        this.jay_walters = jay_walters;
    }

    public String getClocked() {
        return clocked;
    }

    public void setClocked(String clocked) {
        this.clocked = clocked;
    }
}
