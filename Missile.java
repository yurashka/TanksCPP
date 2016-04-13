package tanks;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.util.List;

/**
 * Created by yurashka on 28.03.2016.
 */
public class Missile extends SpriteBase {


    SpriteBase target;

    double turnRate = 0.6;
    double missileSpeed = 6.0;
    double missileHealth = 60;

    public Missile(Pane layer, Image image, double x, double y) {
        super(layer, image, x, y, 0, 0, 0, 0, 1, 1);
        init();
    }

    public Missile(Pane layer, Image image, double x, double y, double r, double dx, double dy, double dr, double health, double damage) {
        super(layer, image, x, y, r, dx, dy, dr, health, damage);
        init();
    }

    private void init() {
        // initially move upwards => dy = -speed
        setDy( -missileSpeed);
        // limit missile alive time (consider it as fuel)
        setHealth( missileHealth);
    }

    public SpriteBase getTarget() {
        return target;
    }

    public void setTarget(SpriteBase target) {
        this.target = target;
    }

    /**
     * Find closest target
     * @param targetList
     */
    public void findTarget( List<EnemyTank> targetList) {


        // we already have a target
        if( getTarget() != null) {
            return;
        }

        SpriteBase closestTarget = null;
        double closestDistance = 0.0;

        for (SpriteBase target: targetList) {

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

    @Override
    public void move() {

        SpriteBase follower = this;

        if( target != null)
        {
            //get distance between follower and target
            double distanceX = target.getCenterX() - follower.getCenterX();
            double distanceY = target.getCenterY() - follower.getCenterY();

            //get total distance as one number
            double distanceTotal = Math.sqrt(distanceX * distanceX + distanceY * distanceY);

            //calculate how much to move
            double moveDistanceX = this.turnRate * distanceX / distanceTotal;
            double moveDistanceY = this.turnRate * distanceY / distanceTotal;

            //increase current speed
            follower.dx += moveDistanceX;
            follower.dy += moveDistanceY;

            //get total move distance
            double totalmove = Math.sqrt(follower.dx * follower.dx + follower.dy * follower.dy);

            //apply easing
            follower.dx = missileSpeed * follower.dx/totalmove;
            follower.dy = missileSpeed * follower.dy/totalmove;

        }

        //move follower
        follower.x += follower.dx;
        follower.y += follower.dy;

        //rotate follower toward target
        double angle = Math.atan2(follower.dy, follower.dx);
        double degrees = Math.toDegrees(angle) + 90;

        follower.r = degrees;

    }

    @Override
    public void checkRemovability() {

        health--; // TODO: let it explode on health 0

        if( Double.compare( health, 0) < 0) {
            setTarget(null);
            setRemovable(true);
        }

    }

}