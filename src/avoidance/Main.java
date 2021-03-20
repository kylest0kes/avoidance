package avoidance;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.Map;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("avoidance.fxml"));
        // create ship and set size
        Avatar avatar = new Avatar(150, 100);
        Enemy enemy = new Enemy(40, 40);

        //create pane set size and put ship in pane
        Pane pane = new Pane();
        pane.setPrefSize(600, 400);
        pane.getChildren().add(avatar.getCharacter());
        pane.getChildren().add(enemy.getCharacter());

        enemy.turnRight();
        enemy.accelerate();

        //init scene and set scene to the stage, and other attrs
        Scene scene = new Scene(pane);

        //key events in the scene
        Map<KeyCode, Boolean> pressedKeys = new HashMap<>();

        scene.setOnKeyPressed(event -> {
            pressedKeys.put(event.getCode(), Boolean.TRUE);
        });

        scene.setOnKeyReleased(event -> {
            pressedKeys.put(event.getCode(), Boolean.FALSE);
        });

        new AnimationTimer() {

            @Override
            public void handle(long now) {
                if(pressedKeys.getOrDefault(KeyCode.LEFT, false)) {
                    avatar.turnLeft();
                }

                if(pressedKeys.getOrDefault(KeyCode.RIGHT, false)) {
                    avatar.turnRight();
                }

                if (pressedKeys.getOrDefault(KeyCode.UP, false)) {
                    avatar.accelerate();
                }

                avatar.move();
                enemy.move();

                if (avatar.collide(enemy)) {
                    stop();
                }
            }

        }.start();

        stage.setScene(scene);
        stage.setTitle("Avoidance");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
