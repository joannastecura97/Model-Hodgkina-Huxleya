package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Class used for launching and creating the application window.
 */
public class Main extends Application {

    /**
     * Method used for setting properties to the window and loading the fxml file.
     * @param primaryStage stage of the application.
     * @throws Exception when an error occurs.
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("hh.fxml"));
        primaryStage.setTitle("Model Hodgkina-Huxleya");
        primaryStage.setScene(new Scene(root, 1200, 700));
        primaryStage.show();
    }


    /**
     * Method used for launching the application.
     * @param args Programmable arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
