package professional.wellness.health.com.employeeapp.Model;

/**
 * Created by Admin on 20-07-2017.
 */

public class AvailableNew {
    public String id;
    public String provider_id;
    public String name;
    public String phone;
    public String location_name;
    public String latitude;
    public String longitude;
    public String time;
    public String prep_time;
    public String date;
    public String estimated_duration;
    public String personnel;
    public String service_provider;
    public String number_of_provider;
    public String jay_walters;
/*    public String clock_in;
    public String clock_out;*/
    public String status;
    public String created_at;
    public String updated_at;
    public String type;
    public String status_name;

    public AvailableNew(String id, String provider_id, String name, String phone, String location_name, String latitude, String longitude, String time, String prep_time, String date, String estimated_duration, String personnel, String service_provider, String number_of_provider, String jay_walters, String status, String created_at, String updated_at, String type, String status_name) {
        this.id = id;
        this.provider_id = provider_id;
        this.name = name;
        this.phone = phone;
        this.location_name = location_name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.time = time;
        this.prep_time = prep_time;
        this.date = date;
        this.estimated_duration = estimated_duration;
        this.personnel = personnel;
        this.service_provider = service_provider;
        this.number_of_provider = number_of_provider;
        this.jay_walters = jay_walters;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.type = type;
        this.status_name = status_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPrep_time() {
        return prep_time;
    }

    public void setPrep_time(String prep_time) {
        this.prep_time = prep_time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getNumber_of_provider() {
        return number_of_provider;
    }

    public void setNumber_of_provider(String number_of_provider) {
        this.number_of_provider = number_of_provider;
    }

    public String getJay_walters() {
        return jay_walters;
    }

    public void setJay_walters(String jay_walters) {
        this.jay_walters = jay_walters;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }
}
