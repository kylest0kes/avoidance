package avoidance;

import javafx.scene.shape.Polygon;

public class Enemy extends Character{
    public Enemy(int x, int y) {
        super(new Polygon(20, -20, 20, 20, -20, 20, -20, -20), x, y);
    }
}
