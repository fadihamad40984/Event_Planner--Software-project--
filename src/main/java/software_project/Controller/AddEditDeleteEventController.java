package software_project.Controller;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
public class AddEditDeleteEventController extends Application {

    @FXML
    private TabPane EventTabs;
    @FXML
    private Tab Tab1;
    @FXML
    private Tab Tab2;
    @FXML
    private Tab Tab3;
    @FXML
    private Tab Tab4;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        URL url = Paths.get("C:\\Users\\User\\Downloads\\CRC\\software_project\\src\\main\\java\\software_project\\FXML\\AddEditDeleteEvent.fxml").toUri().toURL();

        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login Form");
        primaryStage.show();
    }

    public void OpenAdditionPage(javafx.event.ActionEvent actionEvent) {
        EventTabs.getSelectionModel().select(Tab2);
    }
}
