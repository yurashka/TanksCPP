package tanks;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

/**
 * Created by yurashka on 14.03.2016.
 */
public class Player extends SpriteBase implements Serializable {
    public static boolean ALIVE;
    double playerShipMinX;
    double playerShipMaxX;
    double playerShipMinY;
    double playerShipMaxY;

    transient Input input;
    double speed;

    double cannonChargeTime = 60; // the cannon can fire every n frames
    double cannonChargeCounter = cannonChargeTime; // initially the cannon is charged
    double cannonChargeCounterDelta = 1; // counter is increased by this value each frame
    double cannonBullets = 5;
    // number of bullets which the cannon can fire in 1 shot (center, left, right)

    //addformissles
    double missileChargeTime = 120.0;
    double missileChargeCounterDelta = 1.0;
    double missileChargeCounter = missileChargeTime;
    double missileSpeed = 4.0;
    int missileSlot = 0;
    int playerNumber = 0;

    //    private void writeObject(ObjectOutputStream out) throws IOException{
    //        out.defaultWriteObject();
    //        out.writeObject(getX());
    //        out.writeObject(getY());
    //        out.writeObject(getDirection());
    //        out.writeObject(getDx());
    //        out.writeObject(getDy());
    //        out.writeObject(getDirectionOffset());
    //        out.writeObject(getHealth());
    //        out.writeObject(playerNumber);
    //        out.writeObject(missileChargeCounter);
    //        out.writeObject(cannonChargeCounter);
    //        out.writeObject(speed);
    //    }


    public Player(Pane layer, Image image, double x, double y, double direction, double dx,
        double dy, double directionOffset, double health, double damage, double speed,
        Input input) {

        super(layer, image, x, y, direction, dx, dy, directionOffset, health, damage);

        this.speed = speed;
        this.input = input;

        init();
    }

    /**
     * charge secondary weapon every frame
     */
    public void chargeSecondaryWeapon() {

        // limit fire
        // ---------------------------
        // charge weapon: increase a counter by some delta. once it reaches a limit, the weapon is considered charged
        missileChargeCounter += missileChargeCounterDelta;
        if (missileChargeCounter > missileChargeTime) {
            missileChargeCounter = missileChargeTime;
        }

    }


    /**
     * returs player number
     */
    public int getPlayerNumber() {
        return playerNumber;
    }

    /**
     * get secondary weapon position x
     */
    public double getSecondaryWeaponX() {

        if (missileSlot == 0) {
            return x + 10; // just a value that looked right in relation to the sprite image
        } else {
            return x + 34;  // just a value that looked right in relation to the sprite image
        }
    }

    /**
     * get secondary weapon position y
     */
    public double getSecondaryWeaponY() {
        return y + 10; // just a value that looked right in relation to the sprite image
    }

    /**
     * get secondary weapon missile speed
     */
    public double getSecondaryWeaponMissileSpeed() {
        return missileSpeed;
    }

    /**
     * uncharge secondary weapon
     */
    public void unchargeSecondaryWeapon() {

        // player bullet uncharged
        missileChargeCounter = 0;

        // next slot
        missileSlot++;
        missileSlot = missileSlot % 2;

    }

    /**
     * initialise borders
     */
    private void init() {

        // calculate movement bounds of the player ship
        // allow half of the ship to be outside of the screen
        playerShipMinX = 0 - image.getWidth() / 2.0;
        playerShipMaxX = Settings.SCENE_WIDTH - image.getWidth() / 2.0;
        playerShipMinY = 0 - image.getHeight() / 2.0;
        playerShipMaxY = Settings.SCENE_HEIGHT - image.getHeight() / 2.0;

    }

    /**
     * process players inputs
     */
    public void processInput() {

        // ------------------------------------
        // movement
        // ------------------------------------

        // vertical direction
        if (input.isMoveUp() && super.canMoveUp) {
            dy = -speed;
            directionOffset = 0;
        } else if (input.isMoveDown() && super.canMoveDown) {
            dy = speed;
            directionOffset = 180;
        } else {
            dy = 0d;
        }

        // horizontal direction
        if (input.isMoveLeft() && super.canMoveLeft) {
            dx = -speed;
            directionOffset = -90;
        } else if (input.isMoveRight() && super.canMoveRight) {
            dx = speed;
            directionOffset = 90;
        } else {
            dx = 0d;
        }

        if (input.isFirePrimaryWeapon()) {
            Settings.FIRE = true;
        }

    }

    /**
     * move object
     */
    public void move() {

        super.move();
        // ensure the ship can't move outside of the screen
        checkBounds();


    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    /**
     * check bounds of object and playfield
     */
    private void checkBounds() {

        // vertical
        if (Double.compare(y, playerShipMinY) < 0) {
            y = playerShipMinY;
        } else if (Double.compare(y, playerShipMaxY) > 0) {
            y = playerShipMaxY;
        }

        // horizontal
        if (Double.compare(x, playerShipMinX) < 0) {
            x = playerShipMinX;
        } else if (Double.compare(x, playerShipMaxX) > 0) {
            x = playerShipMaxX;
        }

    }


    @Override
    /**
     * check Removability
     */ public void checkRemovability() {
        if (!this.isAlive())
            this.setRemovable(true);
        //  Auto-generated method stub
    }

    /**
     * return primary weapon firing
     */
    public boolean isFirePrimaryWeapon() {

        boolean isCannonCharged = cannonChargeCounter >= cannonChargeTime;

        return input.isFirePrimaryWeapon() && isCannonCharged;

    }

    /**
     * return secondary weapon firing
     */
    public boolean isFireSecondaryWeapon() {

        boolean isMissileCharged = missileChargeCounter >= missileChargeTime;

        return input.isFireSecondaryWeapon() && isMissileCharged;

    }

    /**
     * charge primary weapon every frame
     */
    public void chargePrimaryWeapon() {
        // limit weapon fire
        // ---------------------------
        // charge weapon: increase a counter by some delta. once it reaches a limit, the weapon is considered charged
        cannonChargeCounter += cannonChargeCounterDelta;
        if (cannonChargeCounter > cannonChargeTime) {
            cannonChargeCounter = cannonChargeTime;
        }

    }

    /**
     * uncharge primary weapon
     */
    public void unchargePrimaryWeapon() {

        // player bullet uncharged
        cannonChargeCounter = 0;

    }

    SpriteBase target;
    double turnRate = 0.6;

    /**
     * return  current target
     */
    public SpriteBase getTarget() {
        return target;
    }

    /**
     * set target
     */
    public void setTarget(SpriteBase target) {
        this.target = target;
    }

    /**
     * found closest target to object
     */
    public void findTarget(List<EnemyTank> targetList) {
        // we already have a target
        if (getTarget() != null && target.isAlive()) {
            return;
        }
        //        if (target.isAlive())
        //            return;
        SpriteBase closestTarget = null;

        double closestDistance = 0.0;

        for (SpriteBase target : targetList) {

            if (!target.isAlive())
                continue;

            //get distance between follower and target
            double distanceX = target.getCenterX() - getCenterX();
            double distanceY = target.getCenterY() - getCenterY();

            //get total distance as one number
            double distanceTotal = Math.sqrt(distanceX * distanceX + distanceY * distanceY);


            if (closestTarget == null) {

                closestTarget = target;
                closestDistance = distanceTotal;

            } else if (Double.compare(distanceTotal, closestDistance) < 0) {

                closestTarget = target;
                closestDistance = distanceTotal;

            }
        }

        setTarget(closestTarget);

    }

    /**
     * movement for bot
     */
    public void autoMove() {

        SpriteBase follower = this;

        if (target != null) {
            //get distance between follower and target
            double distanceX = target.getCenterX() - follower.getCenterX();
            double distanceY = target.getCenterY() - follower.getCenterY();

            //get total distance as one number
            double distanceTotal = Math.sqrt(distanceX * distanceX + distanceY * distanceY);

            //calculate how much to move
            double moveDistanceX = this.turnRate * distanceX / distanceTotal;
            double moveDistanceY = this.turnRate * distanceY / distanceTotal;

            //increase current speed
            follower.dx = moveDistanceX;
            follower.dy = moveDistanceY;

            //get total move distance
            double totalmove = Math.sqrt(follower.dx * follower.dx + follower.dy * follower.dy);

            //apply easing
            follower.dx = 2 * follower.dx / totalmove;
            follower.dy = 2 * follower.dy / totalmove;

        }
        //        move follower
        if (canMoveLeft && canMoveRight) {
            follower.x += follower.dx;
        }
        if (canMoveUp && canMoveDown) {
            follower.y += follower.dy;
        }

        if (Double.compare(dy, dx) > 0) {
            if (Double.compare(dy, 0) < 0) {
                direction = 0;
            }
            if (Double.compare(dy, 0) > 0) {
                direction = 180;
            }
        } else {
            if (Double.compare(dx, 0) < 0) {
                direction = -90;
            }
            if (Double.compare(dx, 0) > 0) {
                direction = 90;
            }
        }
    }
}
