package software_project.EventManagement;

public class EventService {
    private int id;
    private String title;
    private String details;
    private String eventCategory;
    private String price;
    private String place;
    private String startTime;
    private String endTime;
    private String bookingTime;

    public EventService() {
        this.id = 0;
        this.title = "";
        this.details = "";
        this.eventCategory = "";
        this.price = "";
        this.place = "";
        this.startTime = "";
        this.endTime = "";
        this.bookingTime = "";
    }

    public EventService(String title , String details , String eventCategory , String price , String place , String startTime , String endTime , String bookingTime)
    {
        setDetails(details);
        setTitle(title);
        setEventCategory(eventCategory);
        setPrice(price);
        setPlace(place);
        setStartTime(startTime);
        setEndTime(endTime);
        setBookingTime(bookingTime);
    }


    public String getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(String eventCategory) {
        this.eventCategory = eventCategory;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}