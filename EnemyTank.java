package tanks;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.util.Random;

/**
 * Created by yurashka on 14.03.2016.
 */
public class EnemyTank extends SpriteBase {

    double EnemyShipMinX;
    double EnemyShipMaxX;
    double EnemyShipMinY;
    double EnemyShipMaxY;

    double movementChangeTime = 120; // the cannon can fire every n frames
    double movementChangeDirection = movementChangeTime; // initially the cannon is charged
    double movementChangeDirectionDelta = 1; // counter is increased by this value each frame

    public EnemyTank(Pane layer, Image image, double x, double y, double r, double dx, double dy, double dr, double health, double damage) {
        super(layer, image, x, y, r, dx, dy, dr, health, damage);
    }

    /**
     * check Removability
     */
    public void checkRemovability() {

        if (!this.isAlive())
            setRemovable(true);

            //check up/down left/right
        if (Double.compare(getY(), Settings.SCENE_HEIGHT) > 0 || (Double.compare(getY(), 0) < 0)) {
            if (Double.compare(getX(),Settings.SCENE_WIDTH ) >0  || (Double.compare(getX(),0 ) <0)) {
                setRemovable(true);
            }
        }

//        if ((Double.compare(getY(), Settings.SCENE_HEIGHT) > 0 && this.canMove)||(Double.compare(getY(), 0) < 0 || this.canMove)) {
//            //dy=-dy;
//        }
//        else
//            setRemovable(true);

    }
    /**
     * move object
     */
    public void move() {


        if( !canMove)
            return;
        x += dx;
        y += dy;
       if (this.changeDirection()) {
            Random rnd = new Random();
            int R = rnd.nextInt(12);
            switch (R) {
                case 1:
                case 2:
                case 3:
                   this.moveLeft();
                    break;
                case 4:
                case 5:
                case 6:
                    this.moveRight();
                    break;
                case 7:
                case 8:
                case 9:
                    this.moveUp();
                    break;
                case 10:
                case 11:
                case 12:
                    this.moveDown();
                    break;
                default:
                    this.moveUp();
                    break;
            }
            this.unchargeChangeMOvement();
        }
        if (Double.compare( dy,0) <0)
            r=0;
        if  (Double.compare( dy,0) >0)
            r=180;
        if (Double.compare(dx,0) <0)
            r=-90;
        if (Double.compare(dx,0) >0)
            r=90;
        if (!this.canMoveRight)
            this.moveLeft();
        if (!this.canMoveLeft)
            this.moveRight();
        if (!this.canMoveUp)
            this.moveDown();
        if (!this.canMoveDown)
            this.moveUp();


        checkBounds();
    }
    /**
     * change Direction
     */
    public boolean changeDirection() {

        boolean changeDirection = movementChangeDirection >= movementChangeTime;

        return changeDirection;

    }
    /**
     * charge Direction change
     */
    public void chargeChangeMovement() {
        // limit weapon fire
        // ---------------------------
        // charge weapon: increase a counter by some delta. once it reaches a limit, the weapon is considered charged
        movementChangeDirection += movementChangeDirectionDelta;
        if( movementChangeDirection > movementChangeTime) {
            movementChangeDirection = movementChangeTime;
        }

    }
    /**
     * uncharge Direction change
     */
    public void unchargeChangeMOvement() {

        // player bullet uncharged
        movementChangeDirection = 0;

    }

    /**
     * check position of object on playfield
     */
    private void checkBounds() {
        //vertical
        if (Double.compare( dy, 0) > 0 || Double.compare( dy, 0) < 0 ){
        if( (Double.compare( y, Settings.SCENE_HEIGHT) < 0) && (Double.compare( y, 0) > 0)) {
            return;
        }
        if( (Double.compare( y, Settings.SCENE_HEIGHT) >= 0) && (Double.compare( dy, 0) > 0)) {
            dy=-Settings.PLAYER_SHIP_SPEED;
            return;
        }
        if( (Double.compare( y, 0) < 0) && (Double.compare( dy, 0) < 0)) {
            dy=Settings.PLAYER_SHIP_SPEED;
            return;
        }}

        //horisontal
        if (Double.compare( dx, 0) > 0 || Double.compare( dx, 0) < 0) {
            if ((Double.compare(x, Settings.SCENE_WIDTH) < 0) && (Double.compare(x, 0) > 0)) {
                return;
            }
            if ((Double.compare(x, Settings.SCENE_WIDTH - 40) >= 0) && (Double.compare(dx, 0) > 0)) {
                dx = -Settings.PLAYER_SHIP_SPEED;
                return;
            }
            if ((Double.compare(x, 0) <= 0) && (Double.compare(dx, 0) < 0)) {
                dx = Settings.PLAYER_SHIP_SPEED;
            }
        }



    }
}