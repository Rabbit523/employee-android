package professional.wellness.health.com.employeeapp.Model;

/**
 * Created by Admin on 04-08-2017.
 */

public class GraphdataList {
private String mileage;
private String drive_time;
private String hours_time;
private String day;
private String status;
private String income;

    public GraphdataList(String mileage, String drive_time, String hours_time, String day, String status, String income) {
        this.mileage = mileage;
        this.drive_time = drive_time;
        this.hours_time = hours_time;
        this.day = day;
        this.status = status;
        this.income = income;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getDrive_time() {
        return drive_time;
    }

    public void setDrive_time(String drive_time) {
        this.drive_time = drive_time;
    }

    public String getHours_time() {
        return hours_time;
    }

    public void setHours_time(String hours_time) {
        this.hours_time = hours_time;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }
}
