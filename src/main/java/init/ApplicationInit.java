package init;/**
 * Created by Rangel on 20/11/2016.
 */

import javafx.application.Application;
import javafx.stage.Stage;
import Services.CreateWindowService;

public class ApplicationInit extends Application {

    private CreateWindowService createWindowService;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        createWindowService = new CreateWindowService("ApplicationView");
        createWindowService.setStage(primaryStage);
        createWindowService.setBtnMin(true);
        createWindowService.setScene();
        createWindowService.show();
    }
}
