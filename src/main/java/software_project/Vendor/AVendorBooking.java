package software_project.Vendor;

public class AVendorBooking {
    private String vendor_user_name;
    private String start_time;
    private String booking_time;
    private String booking_date;
    public AVendorBooking(){
        this.vendor_user_name = "";
        this.start_time = "";
        this.booking_time = "";
        this.booking_date = "";
    }

    public String getVendor_user_name() {
        return vendor_user_name;
    }

    public void setVendor_user_name(String vendor_user_name) {
        this.vendor_user_name = vendor_user_name;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getBooking_time() {
        return booking_time;
    }

    public void setBooking_time(String booking_time) {
        this.booking_time = booking_time;
    }

    public String getBooking_date() {
        return booking_date;
    }

    public void setBooking_date(String booking_date) {
        this.booking_date = booking_date;
    }
}
