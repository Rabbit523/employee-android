package professional.wellness.health.com.employeeapp.Model;

import java.io.Serializable;

/**
 * Created by Admin on 21-08-2017.
 */

public class ViewList implements Serializable{
    private String eid;
    private String name;

    public ViewList(String eid, String name) {
        this.eid = eid;
        this.name = name;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
