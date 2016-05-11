package tanks;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;

import java.io.*;
import java.util.Date;
import java.util.List;

/**
 * Created by yurashka on 28.03.2016.
 */
class AppendingObjectOutputStream extends ObjectOutputStream {

    public AppendingObjectOutputStream(OutputStream out) throws IOException {
        super(out);
    }

    @Override protected void writeStreamHeader() throws IOException {
        // do not write a header, but reset:
        // this line added after another question
        // showed a problem with the original
        reset();
    }

}


public class Utils {
    /**
     * Take a screenshot of the scene in the given stage, open file save dialog and save it.
     */
    public static void screenshot(Scene scene) {
        // take screenshot
        WritableImage image = scene.snapshot(null);
        int x = 0;
        File file = new File("screenshot" + x + ".png");
        // create file save dialog
        //        FileChooser fileChooser = new FileChooser();
        //
        //        // title
        //        fileChooser.setTitle("Save Image");
        //
        //        // initial directory
        //        fileChooser.setInitialDirectory(
        //                new File(System.getProperty("user.home"))
        //        );
        //
        //        // extension filter
        //        fileChooser.getExtensionFilters().addAll(
        //                new FileChooser.ExtensionFilter("All Images", "*.*"),
        //                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
        //                new FileChooser.ExtensionFilter("PNG", "*.png")
        //        );

        // show dialog
        //File file = fileChooser.showSaveDialog( stage);
        //if (file != null) {

        try {

            // save file
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);

        } catch (IOException ex) {

            System.err.println(ex.getMessage());

        }
        //        }
    }


    public static void save(List<Object> gameState) {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream("objectRepeatArray.dat"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int x = 0;
        Date now = new Date(System.currentTimeMillis());
        try {
            //out.writeInt(gameState.size());
            for (x = 0; x < gameState.size(); x++) {

                out.writeObject(gameState.get(x));
                x++;
                out.writeObject(gameState.get(x));
                x++;
                out.writeObject(gameState.get(x));
                x++;
                out.writeObject(gameState.get(x));
                x++;
                out.writeObject(gameState.get(x));
                x++;
                out.writeObject(gameState.get(x));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Saved Repeat" + " objectRepeatArray.dat");
    }



    public static void saveGame(String fileName, List<Player> player, List<EnemyTank> enemy,
        List<Bullet> bullets, List<Bullet> player1bullets, List<Bullet> player2bullets,
        List<Missile> missiles) {
        ObjectOutputStream out = null;
        File saveFile = new File(fileName);
        try {
            out = new ObjectOutputStream(new FileOutputStream(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.writeObject(player);
            out.writeObject(enemy);
            out.writeObject(bullets);
            out.writeObject(player1bullets);
            out.writeObject(player2bullets);
            out.writeObject(missiles);
            out.writeObject(Settings.playerShipSpeed);
            out.writeObject(Settings.playerShipHealth);
            out.writeObject(Settings.playerMissileSpeed);
            out.writeObject(Settings.playerMissileHealth);
            out.writeObject(Settings.player1Lives);
            out.writeObject(Settings.player1IsAlive);
            out.writeObject(Settings.player2Lives);
            out.writeObject(Settings.player2IsAlive);
            out.writeObject(Settings.gamePaused);
            out.writeObject(Settings.player1Score);
            out.writeObject(Settings.player2Score);
            out.writeObject(Settings.automove);
            out.writeObject(Settings.enemy);
            out.writeObject(Settings.enemyCount);
            out.writeObject(Settings.enemyTotal);
            out.writeObject(Settings.BulletsAct);
            out.writeObject(Settings.FIRE);
            out.writeObject(Settings.fontsSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Saved single game");
    }

    public static void saveRepeat(String fileName, List<Player> player, List<EnemyTank> enemy, List<Bullet> bullets,
        List<Bullet> player1bullets, List<Bullet> player2bullets, List<Missile> missiles) {
        AppendingObjectOutputStream out = null;
        try {
            out = new AppendingObjectOutputStream(new FileOutputStream(fileName, true));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.writeObject(player);
            out.writeObject(enemy);
            out.writeObject(bullets);
            out.writeObject(player1bullets);
            out.writeObject(player2bullets);
            out.writeObject(missiles);
            out.writeObject(Settings.playerShipSpeed);
            out.writeObject(Settings.playerShipHealth);
            out.writeObject(Settings.playerMissileSpeed);
            out.writeObject(Settings.playerMissileHealth);
            out.writeObject(Settings.player1Lives);
            out.writeObject(Settings.player1IsAlive);
            out.writeObject(Settings.player2Lives);
            out.writeObject(Settings.player2IsAlive);
            out.writeObject(Settings.gamePaused);
            out.writeObject(Settings.player1Score);
            out.writeObject(Settings.player2Score);
            out.writeObject(Settings.automove);
            out.writeObject(Settings.enemy);
            out.writeObject(Settings.enemyCount);
            out.writeObject(Settings.enemyTotal);
            out.writeObject(Settings.BulletsAct);
            out.writeObject(Settings.FIRE);
            out.writeObject(Settings.fontsSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ;
        System.out.println("Saved repeat 2");
    }
}


