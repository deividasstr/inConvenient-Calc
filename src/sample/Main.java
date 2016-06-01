package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Font.loadFont(getClass().getResource("/sample/Resources/ComingSoon.ttf").toExternalForm(), 10);
        Parent root = FXMLLoader.load(getClass().getResource("/sample/sample.fxml"));
        primaryStage.setTitle("Good luck with this thing    v0.08215742c");
        primaryStage.setScene(new Scene(root, 380, 560));
        primaryStage.setResizable(false);
        primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, k -> {
            if (k.getCode() == KeyCode.SPACE) {
                k.consume();
            }
        });
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);

    }
}
