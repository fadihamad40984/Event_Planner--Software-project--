package software_project.helper;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import software_project.DataBase.DB_Connection;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.SQLException;
import java.util.logging.Logger;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException, SQLException {
        URL url = Paths.get("C:\\Users\\User\\IdeaProjects\\software_project\\src\\main\\java\\software_project\\FXML\\register.fxml").toUri().toURL();
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root, 608, 837);
        stage.setTitle("LoungeBound");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}