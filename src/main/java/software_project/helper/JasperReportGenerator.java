package software_project.helper;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRSaver;
import software_project.DataBase.DBConnection;
import java.util.HashMap;
import java.util.Map;

public class JasperReportGenerator {

    DBConnection connection = new DBConnection();
    public void generateReport(String jrxmlFilePath , String outputFile) {
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlFilePath);

            JRSaver.saveObject(jasperReport, "compiled_report.jasper");

            Map<String, Object> parameters = new HashMap<>();

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection.getCon());

            JasperExportManager.exportReportToPdfFile(jasperPrint, outputFile);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JasperReportGenerator reportGenerator = new JasperReportGenerator();
        reportGenerator.generateReport("userreport.jrxml","output");
    }
}