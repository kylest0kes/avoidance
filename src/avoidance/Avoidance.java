package avoidance;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;

import java.util.*;

public class Avoidance extends Application {

    public static int W = 600;
    public static int H = 400;
    long startTime = System.currentTimeMillis();
    boolean running = false;

    @Override
    public void start(Stage stage) throws Exception{
        running = true;
        Parent root = FXMLLoader.load(getClass().getResource("avoidance.fxml"));
        // create ship and set size
        Avatar avatar = new Avatar(150, 100);
        List<Enemy> enemies = new ArrayList<>();
        for (int i = 0; i < 20; i++){
            Random random = new Random();
            Enemy enemy = new Enemy(random.nextInt(100), random.nextInt(100));
            enemies.add(enemy);
        }

        //create panes
        // main menu pane
        Pane mainMenuPane = new Pane();
        Text sample = new Text(50, 50, "Sample");
        mainMenuPane.setPrefSize(W, H);
        mainMenuPane.getChildren().add(sample);

        //game pane
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

        //game over pane
        Pane gameOverPane = new Pane();
        gameOverPane.setPrefSize(W, H);
        Button replayBtn = new Button("Play Again?");
        Button viewHighScore = new Button("View High Scores");

        //high scores pane
        Pane highScoresPane = new Pane();
        highScoresPane.setPrefSize(W, H);
        Button mainMenu = new Button("Main Menu");
        mainMenu.setOnAction((e) -> {

        });

        //init scene and set scene to the stage, and other attrs
        Scene scene = new Scene(pane);
        Scene gameOverScene = new Scene(gameOverPane);
        Scene mainMenuScene = new Scene(mainMenuPane);

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

                long elapsedTime = System.currentTimeMillis() - startTime;
                long timeScore = elapsedTime / 1000;

                score.setText("Time: " + timeScore);

                avatar.move();
                enemies.forEach(Enemy::move);

                enemies.forEach(enemy -> {
                    if (avatar.collide(enemy)) {
                        running = false;
                        stop();
                        Text gameOverText = new Text((W/2) - 25, (H/2) - 30, "Game Over");
                        Text gameOverScore = new Text((W/2) - 29, H/2, "Final Score: " + timeScore);
                        gameOverPane.getChildren().add(gameOverText);
                        gameOverPane.getChildren().add(gameOverScore);
                        replayBtn.setLayoutX((W/2) - 32);
                        replayBtn.setLayoutY((H/2) + 20);
                        replayBtn.setOnAction((e) -> {
                            //reset everything and start game over
                            System.out.println("works");

                        });
                        gameOverPane.getChildren().add(replayBtn);
                        viewHighScore.setLayoutX((W/2) - 45);
                        viewHighScore.setLayoutY((H/2) + 60);
                        gameOverPane.getChildren().add(viewHighScore);
                        stage.setScene(gameOverScene);
                        stage.show();
                    }
                });


            }

        }.start();

        stage.setScene(mainMenuScene);
        stage.setTitle("Avoidance");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
