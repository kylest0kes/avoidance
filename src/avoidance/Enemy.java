package avoidance;

import javafx.scene.shape.Polygon;
import java.util.Random;

public class Enemy extends Character{
    private double rotateMovement;

    public Enemy(int x, int y) {
        super(new Polygon(20, -20, 20, 20, -20, 20, -20, -20), x, y);

        Random random = new Random();

        super.getCharacter().setRotate(random.nextInt(360));

        int accAmount = 1 + random.nextInt(10);
        for (int i = 0; i < accAmount; i++) {
            accelerate();
        }

        this.rotateMovement = 0.5 - random.nextDouble();
    }

    @Override
    public void move() {
        super.move();
        super.getCharacter().setRotate(super.getCharacter().getRotate() + rotateMovement);
    }
}
