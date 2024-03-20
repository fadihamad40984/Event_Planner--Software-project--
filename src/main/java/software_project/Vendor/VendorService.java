package software_project.Vendor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class VendorService {
    private String vendorUserName;
    private String serviceType;
    private String serviceDescription;
    private String servicePrice;
    private String serviceAvailability;
    private int averageRating;

    public VendorService() {
        this.averageRating = 0;
        this.vendorUserName = "";
        this.serviceType = "";
        this.serviceDescription = "";
        this.servicePrice = "";
        this.serviceAvailability = "";
    }

    public String getVendorUserName() {
        return vendorUserName;
    }

    public void setVendorUserName(String vendorUserName) {
        this.vendorUserName = vendorUserName;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public String getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(String servicePrice) {
        this.servicePrice = servicePrice;
    }

    public String getServiceAvailability() {
        return serviceAvailability;
    }

    public void setServiceAvailability(String serviceAvailability) {
        this.serviceAvailability = serviceAvailability;
    }

    public int getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(int averageRating) {
        this.averageRating = averageRating;
    }
}
