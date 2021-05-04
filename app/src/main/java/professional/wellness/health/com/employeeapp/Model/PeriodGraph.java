package professional.wellness.health.com.employeeapp.Model;

/**
 * Created by Akshat Ajmera on 04-Aug-17.
 */

public class PeriodGraph {
/*
    "mileage":190,
            "drive_time":260,
            "hours_time":"4.93",
            "income":296,
            "period":"16th-31th"*/

    private String mileage;
    private String drive_time;
    private String hours_time;
    private String period;
    private String income;

    public PeriodGraph(String mileage, String drive_time, String hours_time, String period, String income) {
        this.mileage = mileage;
        this.drive_time = drive_time;
        this.hours_time = hours_time;
        this.period = period;
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

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }


}
