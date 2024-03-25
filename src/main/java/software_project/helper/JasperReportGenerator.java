package software_project.helper;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import software_project.DataBase.DB_Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class JasperReportGenerator {

 DB_Connection connection = new DB_Connection();
    public void generateReport(String jrxmlFilePath , String outputFile) {
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlFilePath);

            JRSaver.saveObject(jasperReport, "compiled_report.jasper");

            Map<String, Object> parameters = new HashMap<>();

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection.getCon());

            JasperExportManager.exportReportToPdfFile(jasperPrint, "output_report.pdf");
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JasperReportGenerator reportGenerator = new JasperReportGenerator();
        reportGenerator.generateReport("userreport.jrxml","output");
    }
}
