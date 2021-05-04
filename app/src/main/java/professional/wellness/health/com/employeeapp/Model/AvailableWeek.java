package professional.wellness.health.com.employeeapp.Model;

/**
 * Created by Admin on 10-08-2017.
 */

public class AvailableWeek {
    private String id;
    private String provider_id;
    private String name;
    private String phone;
    private String location_name;
    private String latitude;
    private String longitude;
    private String time;
    private String prep_time;
    private String date;
    private String create_timestamp;
    private String estimated_duration;
    private String personnel;
    private String service_provider;
    private String number_of_provider;
    private String jay_walters;
    private String status;
    private String created_at;
    private String updated_at;
    private String clinic_name;
    private String start_year;
    private String start_month;
    private String start_day;
    private String end_year;
    private String end_month;
    private String end_day;
    private String start_hour;

    private String start_minute;
    private String start_meridiem;
    private String end_hour;
    private String end_minute;
    private String end_meridiem;
    private String type;
    private String status_name;
    private String duration;

    private String mileage_required;
    private String drive_time_required;
    private String mileage_status;
    private String drive_time_status;

    public AvailableWeek(String id, String provider_id, String name, String phone, String location_name, String latitude, String longitude, String time, String prep_time, String date, String create_timestamp, String estimated_duration, String personnel, String service_provider, String number_of_provider, String jay_walters, String status, String created_at, String updated_at, String clinic_name, String start_year, String start_month, String start_day, String end_year, String end_month, String end_day, String start_hour, String start_minute, String start_meridiem, String end_hour, String end_minute, String end_meridiem, String type, String status_name, String duration, String mileage_required, String drive_time_required, String mileage_status, String drive_time_status) {
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
        this.create_timestamp = create_timestamp;
        this.estimated_duration = estimated_duration;
        this.personnel = personnel;
        this.service_provider = service_provider;
        this.number_of_provider = number_of_provider;
        this.jay_walters = jay_walters;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.clinic_name = clinic_name;
        this.start_year = start_year;
        this.start_month = start_month;
        this.start_day = start_day;
        this.end_year = end_year;
        this.end_month = end_month;
        this.end_day = end_day;
        this.start_hour = start_hour;
        this.start_minute = start_minute;
        this.start_meridiem = start_meridiem;
        this.end_hour = end_hour;
        this.end_minute = end_minute;
        this.end_meridiem = end_meridiem;
        this.type = type;
        this.status_name = status_name;
        this.duration = duration;
        this.mileage_required = mileage_required;
        this.drive_time_required = drive_time_required;
        this.mileage_status = mileage_status;
        this.drive_time_status = drive_time_status;
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

    public String getCreate_timestamp() {
        return create_timestamp;
    }

    public void setCreate_timestamp(String create_timestamp) {
        this.create_timestamp = create_timestamp;
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

    public String getClinic_name() {
        return clinic_name;
    }

    public void setClinic_name(String clinic_name) {
        this.clinic_name = clinic_name;
    }

    public String getStart_year() {
        return start_year;
    }

    public void setStart_year(String start_year) {
        this.start_year = start_year;
    }

    public String getStart_month() {
        return start_month;
    }

    public void setStart_month(String start_month) {
        this.start_month = start_month;
    }

    public String getStart_day() {
        return start_day;
    }

    public void setStart_day(String start_day) {
        this.start_day = start_day;
    }

    public String getEnd_year() {
        return end_year;
    }

    public void setEnd_year(String end_year) {
        this.end_year = end_year;
    }

    public String getEnd_month() {
        return end_month;
    }

    public void setEnd_month(String end_month) {
        this.end_month = end_month;
    }

    public String getEnd_day() {
        return end_day;
    }

    public void setEnd_day(String end_day) {
        this.end_day = end_day;
    }

    public String getStart_hour() {
        return start_hour;
    }

    public void setStart_hour(String start_hour) {
        this.start_hour = start_hour;
    }

    public String getStart_minute() {
        return start_minute;
    }

    public void setStart_minute(String start_minute) {
        this.start_minute = start_minute;
    }

    public String getStart_meridiem() {
        return start_meridiem;
    }

    public void setStart_meridiem(String start_meridiem) {
        this.start_meridiem = start_meridiem;
    }

    public String getEnd_hour() {
        return end_hour;
    }

    public void setEnd_hour(String end_hour) {
        this.end_hour = end_hour;
    }

    public String getEnd_minute() {
        return end_minute;
    }

    public void setEnd_minute(String end_minute) {
        this.end_minute = end_minute;
    }

    public String getEnd_meridiem() {
        return end_meridiem;
    }

    public void setEnd_meridiem(String end_meridiem) {
        this.end_meridiem = end_meridiem;
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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getMileage_required() {
        return mileage_required;
    }

    public void setMileage_required(String mileage_required) {
        this.mileage_required = mileage_required;
    }

    public String getDrive_time_required() {
        return drive_time_required;
    }

    public void setDrive_time_required(String drive_time_required) {
        this.drive_time_required = drive_time_required;
    }

    public String getMileage_status() {
        return mileage_status;
    }

    public void setMileage_status(String mileage_status) {
        this.mileage_status = mileage_status;
    }

    public String getDrive_time_status() {
        return drive_time_status;
    }

    public void setDrive_time_status(String drive_time_status) {
        this.drive_time_status = drive_time_status;
    }
}
