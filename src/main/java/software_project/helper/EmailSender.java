package software_project.helper;

import java.security.SecureRandom;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class EmailSender {

    private String status;

    private boolean validEmail;

    String to;
    String from;
    SecureRandom random;
   public int verificationCode;

    public EmailSender(String to){
        this.to = to;
        from="fadi3business@gmail.com";
        random = new SecureRandom();
        verificationCode = 10000 + random.nextInt(90000);
    }



    private void sending(String subject, String text){
        try {
            Properties properties = System.getProperties();
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "587");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");

            Session session = Session.getDefaultInstance(properties,new javax.mail.Authenticator(){
                @Override
                protected  PasswordAuthentication getPasswordAuthentication(){
                    return new PasswordAuthentication("fadi3business@gmail.com","tgac jvkm eoak eles");
                }
            });
            session.setDebug(false);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to,false));
            message.setSubject(subject);
            message.setText(text);
            Transport.send(message);
            setStatus("Email Send Successfully");
            setValidEmail(true);
        }
        catch (MessagingException ppp) {
            ppp.printStackTrace();

            setStatus("Email doesnt Send Successfully");
           setValidEmail(false);
        }

    }

    public void sendVerificationCode(){
        String subject = "This is your verification code for Event Planner";
        String text = "Your code is "+verificationCode +"\n"+"Please don't share this code with anyone";
        sending(subject,text);

    }

    public void sendEmail(String subject,String text) {
        sending(subject,text);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isValidEmail() {
        return validEmail;
    }

    public void setValidEmail(boolean validEmail) {
        this.validEmail = validEmail;
    }
}
