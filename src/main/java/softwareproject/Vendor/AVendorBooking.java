package softwareproject.Vendor;

public class AVendorBooking {
    private String vendorUserName;
    private String startTime;
    private String bookingTime;
    private String bookingDate;
    public AVendorBooking(){
        this.vendorUserName = "";
        this.startTime = "";
        this.bookingTime = "";
        this.bookingDate = "";
    }

    public String getVendorusername() {
        return vendorUserName;
    }

    public void setVendorusername(String vendorUsername) {
        this.vendorUserName = vendorUsername;
    }

    public String getStarttime() {
        return startTime;
    }

    public void setStarttime(String startTime) {
        this.startTime = startTime;
    }

    public String getBookingtime() {
        return bookingTime;
    }

    public void setBookingtime(String bookingTime) {
        this.bookingTime = bookingTime;
    }

    public String getBookingdate() {
        return bookingDate;
    }

    public void setBookingdate(String bookingDate) {
        this.bookingDate = bookingDate;
    }
}
