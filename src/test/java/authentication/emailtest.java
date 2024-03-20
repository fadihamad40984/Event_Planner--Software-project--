package authentication;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import software_project.helper.EmailSender;

import javax.mail.MessagingException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class emailtest {


    private String to = "momentsgolden806@gmail.com";
    private String subject;
    private String body;
    EmailSender em=new EmailSender(to);

    @Given("the user is ready to send an email")
    public void the_user_is_ready_to_send_an_email() {

        assert(true);
    }

    @When("they send an email with subject {string} and body {string}")
    public void they_send_an_email_with_subject_and_body(String string, String string2) throws MessagingException {
        this.subject = string;
        this.body = string2;

        em.sendEmail(subject,body);
    }

    @Then("the email should be sent successfully")
    public void the_email_should_be_sent_successfully() {
        assertEquals("Email Send Successfully",em.getStatus());
    }


}
