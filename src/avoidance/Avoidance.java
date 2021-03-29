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
        Pane mainMenuPane = new Pane();
        Pane highScorePane = new Pane();
        Pane gamePane = new Pane();
        Pane gameOverPane = new Pane();

        //create scenes
        Scene mainMenuScene = new Scene(mainMenuPane);
        Scene highScoreScene = new Scene(highScorePane);
        Scene gameScene = new Scene(gamePane);
        Scene gameOverScene = new Scene(gameOverPane);

        //create buttons
        Button viewHighScoreBtn = new Button("View High Scores");
        viewHighScoreBtn.setOnAction(e -> {
            stage.setScene(highScoreScene);
        });

        Button viewScoresBtn = new Button("View High Scores");
        viewScoresBtn.setOnAction(e -> {
            stage.setScene(highScoreScene);
        });

        Button replayBtn = new Button("Play Again?");
        replayBtn.setOnAction((e) -> {
            //reset everything and start game over
            System.out.println("works");
        });

        Button mainMenuBtn = new Button("Main Menu");
        mainMenuBtn.setOnAction((e) -> {
            stage.setScene(mainMenuScene);
        });

        // main menu pane
        mainMenuPane.setPrefSize(W, H);
        Text sample = new Text(150, 150, "On Main Menu");
        mainMenuPane.getChildren().add(sample);
        mainMenuPane.getChildren().add(viewScoresBtn);

        //game pane
        Text score = new Text(10, 20, "Time: ");
        gamePane.setPrefSize(W, H);
        gamePane.getChildren().add(score);
        gamePane.getChildren().add(avatar.getCharacter());
        enemies.forEach(enemy -> gamePane.getChildren().add(enemy.getCharacter()));

        enemies.forEach(enemy -> {
            enemy.turnRight();
            enemy.accelerate();
        });

        //high scores pane
        highScorePane.setPrefSize(W, H);
        Text highScoreTestString = new Text(200, 200, "On HighScores");
        highScorePane.getChildren().add(highScoreTestString);
        highScorePane.getChildren().add(mainMenuBtn);

        //game over pane
        gameOverPane.setPrefSize(W, H);


        //init scene and set scene to the stage, and other attrs

        //key events in the scene
        Map<KeyCode, Boolean> pressedKeys = new HashMap<>();

        gameScene.setOnKeyPressed(event -> {
            pressedKeys.put(event.getCode(), Boolean.TRUE);
        });

        gameScene.setOnKeyReleased(event -> {
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

                        gameOverPane.getChildren().add(replayBtn);
                        viewHighScoreBtn.setLayoutX((W/2) - 45);
                        viewHighScoreBtn.setLayoutY((H/2) + 60);
                        gameOverPane.getChildren().add(viewHighScoreBtn);
                        stage.setScene(gameOverScene);
                        stage.show();
                    }
                });


            }

        }.start();

        stage.setScene(gameScene);
        stage.setTitle("Avoidance");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
