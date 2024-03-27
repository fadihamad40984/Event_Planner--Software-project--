package software_project.Vendor;

import software_project.DataBase.insert.InsertData;
import software_project.helper.Validation;

import java.sql.Connection;
import java.sql.SQLException;

public class VendorManipulation {

    private String status;
    private final InsertData insertVS;


    public VendorManipulation(Connection con){
        insertVS = new InsertData(con);

    }
    public void addVendorService(VendorService vs) {
        String st = Validation.vendorServiceValidationTest(vs);
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

    public void addVendorReview(VendorReview vr) throws SQLException {
            insertVS.insertVendorReview(vr);
            setStatus("vendor review confirmed successfully");
    }
}
