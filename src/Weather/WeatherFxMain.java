package Weather;
// this class displays the weather.fxml file
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class WeatherFxMain extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Weather.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            Scene scene = new Scene(root);

            primaryStage.setTitle("Weather");
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch(Exception e)    {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}

