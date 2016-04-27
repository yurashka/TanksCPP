package tanks;

/**
 * Created by yurashka on 14.03.2016.
 */
import java.io.Serializable;
import java.util.BitSet;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Input {

    /**
     * Bitset which registers if any {@link KeyCode} keeps being pressed or if it is released.
     */
    private BitSet keyboardBitSet = new BitSet();

    // -------------------------------------------------
    // default key codes
    // will vary when you let the user customize the key codes or when you add support for a 2nd player
    // -------------------------------------------------

    private KeyCode upKey = KeyCode.UP;
    private KeyCode downKey = KeyCode.DOWN;
    private KeyCode leftKey = KeyCode.LEFT;
    private KeyCode rightKey = KeyCode.RIGHT;
    private KeyCode primaryWeaponKey = KeyCode.NUMPAD0;
    private KeyCode secondaryWeaponKey = KeyCode.SHIFT;
    private KeyCode menuKey = KeyCode.ESCAPE;
    /**
     * Set up key
     */
    public void  setupKey (KeyCode k)
    {
     upKey=k;
    }
    /**
     * Set down key
     */
    public void  setdownKey (KeyCode k)
    {
    downKey=k;
    }
    /**
     * Set left key
     */
    public void  setleftKey (KeyCode k)
    {
leftKey=k;
    }
    /**
     * Set right key
     */
    public void  setrightKey (KeyCode k)
    {
rightKey=k;
    }
    /**
     * Set primary weapon key
     */
    public void  setprimaryWeaponKey (KeyCode k) {
        primaryWeaponKey=k;
    }
    /**
     * Set secondary weapon key
     */
    public void  setsecondaryWeaponKeyKey (KeyCode k)
    {
secondaryWeaponKey=k;
    }

    transient Scene scene;
    /**
     * Bing scene and input
     */
    public Input( Scene scene) {
        this.scene = scene;
    }
    /**
     * add Listeners
     */
    public void addListeners() {

        scene.addEventFilter(KeyEvent.KEY_PRESSED, keyPressedEventHandler);
        scene.addEventFilter(KeyEvent.KEY_RELEASED, keyReleasedEventHandler);

    }
    /**
     * remove listeners
     */
    public void removeListeners() {

        scene.removeEventFilter(KeyEvent.KEY_PRESSED, keyPressedEventHandler);
        scene.removeEventFilter(KeyEvent.KEY_RELEASED, keyReleasedEventHandler);

    }

    /**
     * "Key Pressed" handler for all input events: register pressed key in the bitset
     */
    transient private EventHandler<KeyEvent> keyPressedEventHandler = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {

            // register key down
            keyboardBitSet.set(event.getCode().ordinal(), true);

        }
    };

    /**
     * "Key Released" handler for all input events: unregister released key in the bitset
     *
     * */
    transient private EventHandler<KeyEvent> keyReleasedEventHandler = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            // register key up
            keyboardBitSet.set(event.getCode().ordinal(), false);
        }
    };


    // -------------------------------------------------
    // Evaluate bitset of pressed keys and return the player input.
    // If direction and its opposite direction are pressed simultaneously, then the direction isn't handled.
    // -------------------------------------------------
    /**
     * Move up state
     */
    public boolean isMoveUp() {
        return keyboardBitSet.get( upKey.ordinal()) && !keyboardBitSet.get( downKey.ordinal()) && !keyboardBitSet.get( rightKey.ordinal()) && !keyboardBitSet.get( leftKey.ordinal());
    }
    /**
     * Move down state
     */
    public boolean isMoveDown() {
        return keyboardBitSet.get( downKey.ordinal()) && !keyboardBitSet.get( upKey.ordinal()) && !keyboardBitSet.get( rightKey.ordinal()) && !keyboardBitSet.get( leftKey.ordinal());
    }
    /**
     * Move left state
     */
    public boolean isMoveLeft() {
        return keyboardBitSet.get( leftKey.ordinal()) && !keyboardBitSet.get( rightKey.ordinal());// && !keyboardBitSet.get( upKey.ordinal()) && !keyboardBitSet.get( downKey.ordinal());
    }
    /**
     * Move roght state
     */
    public boolean isMoveRight() {
        return keyboardBitSet.get( rightKey.ordinal()) && !keyboardBitSet.get( leftKey.ordinal());// && !keyboardBitSet.get( upKey.ordinal()) && !keyboardBitSet.get( downKey.ordinal());
    }
    /**
     * Fire primary weapon
     */
    public boolean isFirePrimaryWeapon() {
        return keyboardBitSet.get( primaryWeaponKey.ordinal());
    }
    /**
     * Fire secondary weapon
     */
    public boolean isFireSecondaryWeapon() {

        return keyboardBitSet.get( secondaryWeaponKey.ordinal());
    }

}