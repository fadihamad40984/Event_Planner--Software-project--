package softwareproject.vendor;

public class VendorReview {
    private String vendorUserName;
    private String customerUserName;
    private String rating;
    private String feedBackText;
    public VendorReview() {
        this.vendorUserName = "";
        this.customerUserName = "";
        this.rating = "";
        this.feedBackText = "";
    }

    public String getVendorUserName() {
        return vendorUserName;
    }

    public void setVendorUserName(String vendorUserName) {
        this.vendorUserName = vendorUserName;
    }

    public String getCustomerUserName() {
        return customerUserName;
    }

    public void setCustomerUserName(String customerUserName) {
        this.customerUserName = customerUserName;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getFeedBackText() {
        return feedBackText;
    }

    public void setFeedBackText(String feedBackText) {
        this.feedBackText = feedBackText;
    }
}
