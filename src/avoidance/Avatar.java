package avoidance;

import javafx.scene.shape.Polygon;

public class Avatar extends Character {
    public Avatar(int x, int y) {
        super(new Polygon(-15, -15, 20, 0, -15, 15), x, y);
    }

}
