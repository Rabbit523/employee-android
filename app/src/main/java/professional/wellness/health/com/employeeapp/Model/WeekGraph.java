package professional.wellness.health.com.employeeapp.Model;

/**
 * Created by Akshat Ajmera on 04-Aug-17.
/* *//*
"mileage":150,
        "drive_time":180,
        "hours_time":"3.00",
        "income":180,
        "week":"2nd week"*/
public class WeekGraph {

    private String mileage;
    private String drive_time;
    private String hours_time;
    private String week;
    private String income;


    public WeekGraph(String mileage, String drive_time, String hours_time, String week, String income) {
        this.mileage = mileage;
        this.drive_time = drive_time;
        this.hours_time = hours_time;
        this.week = week;
        this.income = income;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }


    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
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
}
