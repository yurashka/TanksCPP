package tanks;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

/**
 * Created by yurashka on 14.03.2016.
 */
public class Bullet extends SpriteBase {
    public Bullet(Pane layer, Image image, double x, double y, double r, double dx, double dy, double dr, double health, double damage) {
        super(layer, image, x, y, r, dx, dy, dr, health, damage);
    }
    public void checkRemovability() {

        if (Double.compare(getY(), Settings.SCENE_HEIGHT) > 0 || (Double.compare(getX(),Settings.SCENE_WIDTH ) >0 ) || (Double.compare(getX(),0 ) <0 || Double.compare(getY(), 0) < 0))
        {
            setRemovable(true);
            Settings.BulletsAct--;
        }
    }
    public void move() {
    if( !canMove)
        return;
    if (r==0) {
        dy = -dr;
        dx = 0;
        }
    if (r==180) {
        dy = dr;
        dx = 0;
        }
    if (r==90) {
        dy=0;
        dx=dr;
        }
    if (r==-90) {
        dy=0;
        dx=-dr;
        }
    y +=dy;
    x +=dx;
    }
}
