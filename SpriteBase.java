package tanks;

/**
 * Created by yurashka on 14.03.2016.
 */
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public abstract class SpriteBase {

    Image image;
    ImageView imageView;

    Pane layer;

    double x;
    double y;
    double direction;

    double dx;
    double dy;
    double directionOffset;

    double health;
    double damage;

    boolean removable = false;

    double w;
    double h;

    boolean canMove = true;
    boolean canMoveUp = true;
    boolean canMoveDown = true;
    boolean canMoveLeft = true;
    boolean canMoveRight = true;
    /**
     * Create object with parameters Pane - layer where obj will be added, Image - picture of object, x,y,z - coordinates
     * dx,dy,directionOffset - offsets for movement
     */
    public SpriteBase(Pane layer, Image image, double x, double y, double direction, double dx, double dy, double directionOffset, double health, double damage) {

        this.layer = layer;
        this.image = image;
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.dx = dx;
        this.dy = dy;
        this.directionOffset = directionOffset;

        this.health = health;
        this.damage = damage;

        this.imageView = new ImageView(image);
        this.imageView.relocate(x, y);
        this.imageView.setRotate(direction);

        this.w = image.getWidth(); // imageView.getBoundsInParent().getWidth();
        this.h = image.getHeight(); // imageView.getBoundsInParent().getHeight();

        addToLayer();

    }
    /**
     * Add object to selected layer
     */
    public void addToLayer() {
        this.layer.getChildren().add(this.imageView);
    }
    /**
     * Remove object from selected layer
     */
    public void removeFromLayer() {
        this.layer.getChildren().remove(this.imageView);
    }
    /**
     * Get current layer where object is
     */
    public Pane getLayer() {
        return layer;
    }
    /**
     * Set object to selected layer
     */
    public void setLayer(Pane layer) {
        this.layer = layer;
    }
    /**
     * Get x coordinate
     */
    public double getX() {
        return x;
    }
    /**
     * Set x coordinate
     */
    public void setX(double x) {
        this.x = x;
    }
    /**
     * Get y coordinate
     */
    public double getY() {
        return y;
    }
    /**
     * Set y coordinate
     */
    public void setY(double y) {
        this.y = y;
    }
    /**
     * Get r - turnrate
     */
    public double getDirection() {
        return direction;
    }
    /**
     * Set R - turnrate
     */
    public void setDirection(double r) {
        this.direction = r;
    }
    /**
     * Get dx offset from current x
     */
    public double getDx() {
        return dx;
    }
    /**
     * Set dx offset from current x
     */
    public void setDx(double dx) {
        this.dx = dx;
    }
    /**
     * Get dy offset from current y
     */
    public double getDy() {
        return dy;
    }
    /**
     * Set dy offset from current y
     */
    public void setDy(double dy) {
        this.dy = dy;
    }
    /**
     * Get dr offset from current r
     */
    public double getDirectionOffset() {
        return directionOffset;
    }
    /**
     * Set dr offset from current r
     */
    public void setDirectionOffset(double directionOffset) {this.directionOffset = directionOffset;
    }
    /**
     * Get object health
     */
    public double getHealth() {
        return health;
    }
    /**
     * Get object damage
     */
    public double getDamage() {
        return damage;
    }
    /**
     * Set object damage
     */
    public void setDamage(double damage) {
        this.damage = damage;
    }
    /**
     * Set object health
     */
    public void setHealth(double health) {
        this.health = health;
    }
    /**
     * Check for removability
     */
    public boolean isRemovable() {
        return removable;
    }
    /**
     * Set removable(bool) where bool - is true or false
     */
    public void setRemovable(boolean removable) {
        this.removable = removable;
    }
    /**
     * Move function of object
     */
    public void move() {

        if( !canMove)
            return;
//        Random rnd = new Random();
        x += dx;
        y += dy;
        direction = directionOffset;
    }
    /**
     * MoveUP function of object
     */
    public void moveUp() {

        if( !canMoveUp)
            return;
        dx=0;
        dy=Settings.PLAYER_SHIP_SPEED;
        if (dy>0) dy=-dy;
        x += dx;
        y += dy;
        direction = 0;

    }
    /**
     * MoveDOWN function of object
     */
    public void moveDown() {

        if( !canMoveDown)
            return;
        dx=0;
        dy=Settings.PLAYER_SHIP_SPEED;
        if (dy>0) dy=-dy;
        x += dx;
        y += dy;
        direction = 180;

    }
    /**
     * MoveLEFT function of object
     */
    public void moveLeft() {

        if( !canMoveLeft)
            return;
        dx=Settings.PLAYER_SHIP_SPEED;
        if (dx>0) dx=-dx;
        dy=0;

        x += dx;
        y += dy;
        direction = 90;

    }
    /**
     * MoveRIGHT function of object
     */
    public void moveRight() {

        if( !canMoveRight)
            return;
        dx=Settings.PLAYER_SHIP_SPEED;
        if (dx<0) dx=-dx;
        dy=0;
        x += dx;
        y += dy;
        direction = -90;

    }

    /**
     * check Alive or not
     */
    public boolean isAlive() {
        return Double.compare(health, 0) > 0;
    }
    /**
     * Return current picture
     */
    public ImageView getView() {
        return imageView;
    }
    /**
     * UpdateUI
     */
    public void updateUI() {

        imageView.relocate(x, y);
        imageView.setRotate(direction);

    }
    /**
     * Get current width of object
     */
    public double getWidth() {
        return w;
    }
    /**
     * Get current height of object
     */
    public double getHeight() {
        return h;
    }
    /**
     * Get current CenterX of object
     */
    public double getCenterX() {
        return x + w * 0.5;
    }
    /**
     * Get current CenterY of oblect
     */
    public double getCenterY() {
        return y + h * 0.5;
    }
    /**
     * check collision
     */
    public boolean collidesWith( SpriteBase otherSprite) {
       return ( otherSprite.x + otherSprite.w >= x && otherSprite.y + otherSprite.h >= y && otherSprite.x <= x + w && otherSprite.y <= y + h);
    }
    /**
     *
     * Reduce health by the amount of damage that the given sprite can inflict
     * @param sprite
     */
    public void getDamagedBy( SpriteBase sprite) {
        health -= sprite.getDamage();
    }

    /**
     * Set health to 0
     */
    public void kill() {
        setHealth( 0);
    }

    /**
     * Set flag that the sprite can be removed from the UI.
     */
    public void remove() {
        setRemovable(true);
    }

    /**
     * Set flag that the sprite can't move anymore.
     */
    public void stopMovement() {
        this.canMove = false;
    }

    public abstract void checkRemovability();

}