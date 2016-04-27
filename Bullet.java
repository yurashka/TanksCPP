package tanks;

import java.io.Serializable;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

/**
 * Created by yurashka on 14.03.2016.
 */
public class Bullet extends SpriteBase implements Serializable {
    public Bullet(Pane layer, Image image, double x, double y, double direction, double dx,
        double dy, double directionOffset, double health, double damage) {
        super(layer, image, x, y, direction, dx, dy, directionOffset, health, damage);
    }

    public void checkRemovability() {

        if (Double.compare(getY(), Settings.SCENE_HEIGHT) > 0 || (
            Double.compare(getX(), Settings.SCENE_WIDTH) > 0) || (Double.compare(getX(), 0) < 0
            || Double.compare(getY(), 0) < 0)) {
            setRemovable(true);
            Settings.BulletsAct--;
        }
    }

    public void move() {
        if (!canMove)
            return;
        if (direction == 0) {
            dy = -directionOffset;
            dx = 0;
        }
        if (direction == 180) {
            dy = directionOffset;
            dx = 0;
        }
        if (direction == 90) {
            dy = 0;
            dx = directionOffset;
        }
        if (direction == -90) {
            dy = 0;
            dx = -directionOffset;
        }
        y += dy;
        x += dx;
    }
}
