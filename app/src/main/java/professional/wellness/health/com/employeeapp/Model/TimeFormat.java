package professional.wellness.health.com.employeeapp.Model;

/**
 * Created by Admin on 24-08-2017.
 */

public class TimeFormat {
    private String name;
    private String value;

    public TimeFormat(String name, String value) {
        this.name = name;
        this.value = value;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
