package professional.wellness.health.com.employeeapp.Model;

/**
 * Created by Admin on 17-08-2017.
 */

public class ViewListHomeFeed {
    private String id;
    private String name;

    public ViewListHomeFeed(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
