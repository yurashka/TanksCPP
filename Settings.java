package tanks;

/**
 * Created by yurashka on 14.03.2016.
 */
public class Settings {
    public static final double SCENE_WIDTH = 600;
    public static final double SCENE_HEIGHT = 600;
    public static double playerShipSpeed = 4.0;
    public static double playerShipHealth = 1000.0;
    public static double playerMissileSpeed = 15.0;
    public static double playerMissileHealth = 50.0;
    public static int player1Lives = 3;
    public static boolean player1IsAlive = false;
    public static int player2Lives = 3;
    public static boolean player2IsAlive = false;
    public static boolean gamePaused = false;
    public static int player1Score = 0;
    public static int player2Score = 0;
    public static boolean automove = false;
    public static final int ENEMY_SPAWN_RANDOMNESS = 100;
    public static int enemy = 24;
    public static int enemyCount = 0;
    public static int enemyTotal = 0;
    public static int BulletsAct = 1;
    public static boolean FIRE = true;
    public static int fontsSize = 24;
    public static boolean repeat = false;
    public static boolean saveFirstTime = true;
}

