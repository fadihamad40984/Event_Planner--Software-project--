package software_project.EventManagement;

import java.util.List;

public class Places {
    private int id;
    private String name;
    private int capacity;
    private String amenities;

    public Places() {
        this.id = 0;
        this.name = "";
        this.capacity = 0;
        this.amenities = "";
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getAmenities() {
        return amenities;
    }

    public void setAmenities(String amenities) {
        this.amenities = amenities;
    }

}
