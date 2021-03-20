package avoidance;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;

import java.util.*;

public class Avoidance extends Application {

    public static int W = 600;
    public static int H = 400;
    long startTime = System.currentTimeMillis();


    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("avoidance.fxml"));
        // create ship and set size
        Avatar avatar = new Avatar(150, 100);
        List<Enemy> enemies = new ArrayList<>();
        for (int i = 0; i < 15; i++){
            Random random = new Random();
            Enemy enemy = new Enemy(random.nextInt(100), random.nextInt(100));
            enemies.add(enemy);
        }

        //create pane set size and put elements in pane
        Pane pane = new Pane();
        Text score = new Text(10, 20, "Time: ");
        pane.setPrefSize(W, H);
        pane.getChildren().add(score);
        pane.getChildren().add(avatar.getCharacter());
        enemies.forEach(enemy -> pane.getChildren().add(enemy.getCharacter()));

        enemies.forEach(enemy -> {
            enemy.turnRight();
            enemy.accelerate();
        });

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
                enemies.forEach(Enemy::move);

                enemies.forEach(enemy -> {
                    if (avatar.collide(enemy)) {
                        stop();
                    }
                });
                long elapsedTime = System.currentTimeMillis() - startTime;
                long timeScore = elapsedTime / 1000;

                score.setText("Time: " + timeScore);

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
