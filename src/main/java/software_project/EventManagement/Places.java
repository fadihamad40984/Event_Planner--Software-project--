package software_project.EventManagement;


public class Places {
    private int id;
    private String name;
    private String capacity;
    private String amenities;

    public Places() {
        this.name = "";
        this.capacity = "";
        this.amenities = "";
        this.id=0;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}