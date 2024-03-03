package software_project.EventManagement;

import java.util.List;

public class Places {
    private String name;
    private String capacity;
    private String amenities;

    public Places() {
        this.name = "";
        this.capacity = "";
        this.amenities = "";
    }




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getAmenities() {
        return amenities;
    }

    public void setAmenities(String amenities) {
        this.amenities = amenities;
    }

}
