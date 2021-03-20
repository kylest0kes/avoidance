package avoidance;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;

public class Avatar {

    private Polygon avatar;
    private Point2D movement;

    public Avatar(int x, int y) {
        this.avatar = new Polygon(-5, -5, 10, 0, -5, 5);
        this.avatar.setTranslateX(x);
        this.avatar.setTranslateY(y);

        this.movement = new Point2D(0, 0);
    }

    public Polygon getAvatar() {
        return avatar;
    }

    public void turnLeft() {
        this.avatar.setRotate(this.avatar.getRotate() - 5);
    }

    public void turnRight() {
        this.avatar.setRotate(this.avatar.getRotate() + 5);
    }

    public void move() {
        this.avatar.setTranslateX(this.avatar.getTranslateX() + this.movement.getX());
        this.avatar.setTranslateY(this.avatar.getTranslateY() + this.movement.getY());
    }
}
