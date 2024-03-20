package software_project.Vendor;

import software_project.DataBase.delete.deleteEvent;
import software_project.DataBase.insert.insertData;
import software_project.helper.validation;

import java.sql.Connection;

public class VendorManipulation {

    private String status;
    private insertData insertVS;


    public VendorManipulation(Connection con){
        insertVS = new insertData(con);

    }
    public void addVendorService(VendorService vs) {
        String st = validation.vendorServiceValidationTest(vs);
        setStatus(st);
        if(getStatus().equals("Valid")) {
            insertVS.insertVendorService(vs);
            setStatus("service confirmed to vendor successfully");
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void addVendorReview(VendorReview vr) {
            insertVS.insertVendorReview(vr);
            setStatus("vendor review confirmed successfully");
    }
}
