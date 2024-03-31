package softwareproject.helper;

import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;
import softwareproject.database.DBConnection;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;



import javax.swing.*;

public class JasperReportGenerator {

    private String status;

    DBConnection connection = new DBConnection();
    public void generateReport(String jrxmlFilePath, String outputFile) throws SQLException, IOException, JRException {
        try (Connection con = connection.getCon();
             InputStream input = new FileInputStream(new File(jrxmlFilePath));
             OutputStream output = new FileOutputStream(new File(outputFile))) {

            JasperDesign jasperDesign = JRXmlLoader.load(input);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, con);

            JasperExportManager.exportReportToPdfStream(jasperPrint, output);

            JFrame frame = new JFrame("Report");
            frame.getContentPane().add(new JRViewer(jasperPrint));
            frame.pack();
            frame.setVisible(true);
            setStatus("Successful Generated");
        }
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}