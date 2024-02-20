package software_project.Controller;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Objects;

public class LoginController extends Application {


    public TextField usernameTextField;
    public PasswordField passwordTextField;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        URL url = Paths.get("C:\\Users\\User\\IdeaProjects\\software_project\\src\\main\\java\\software_project\\FXML\\Login.fxml").toUri().toURL();

        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login Form");
        primaryStage.show();
    }


    public void onLoginClick(ActionEvent actionEvent) {
    }

    public void onSignupClick(ActionEvent actionEvent) {
    }
}

















