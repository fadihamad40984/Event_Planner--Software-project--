package software_project.helper;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import software_project.DataBase.DB_Connection;
import software_project.DataBase.retrieve.retrieveuser;
import software_project.UserManagement.User;

import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PDFReportGenerator {


    public PDFReportGenerator() {
        try {
           Document document = new Document();

            PdfWriter.getInstance(document, new FileOutputStream("report.pdf"));

            document.open();

            addHeader(document);
            addLine(document);

            document.close();




        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addHeader(Document document) {
        try {
            com.itextpdf.text.Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.RED);

            Paragraph header = new Paragraph("EliteEvent Management", fontHeader);
            header.setAlignment(Element.ALIGN_CENTER);
            document.add(header);

            Image logo = Image.getInstance("logo.png");
            logo.scaleToFit(100, 100);
            logo.setAlignment(Element.ALIGN_LEFT);
            document.add(logo);

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            String dateTime = "Date and Time: " + dateFormat.format(date);
            Font fontDateTime = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLUE);
            Paragraph dateTimeParagraph = new Paragraph(dateTime, fontDateTime);
            dateTimeParagraph.setAlignment(Element.ALIGN_RIGHT);
            document.add(dateTimeParagraph);
            document.add(Chunk.NEWLINE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addLine(Document document) {
        try {
            LineSeparator line = new LineSeparator();
            document.add(new Chunk(line));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addContent(Document document , List <User> userList) {

    }

    public static void main(String[] args) {
        PDFReportGenerator pdfReportGenerator = new PDFReportGenerator();

    }
}
