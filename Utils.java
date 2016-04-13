package tanks;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * Created by yurashka on 28.03.2016.
 */
public class Utils {
    /**
     * Take a screenshot of the scene in the given stage, open file save dialog and save it.
     * @param stage
     */
    public static void screenshot( Stage stage) {

        // take screenshot
        WritableImage image = stage.getScene().snapshot( null);

        // create file save dialog
        FileChooser fileChooser = new FileChooser();

        // title
        fileChooser.setTitle("Save Image");

        // initial directory
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );

        // extension filter
        fileChooser.getExtensionFilters().addAll(
                // new FileChooser.ExtensionFilter("All Images", "*.*"),
                // new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );

        // show dialog
        File file = fileChooser.showSaveDialog( stage);
        if (file != null) {

            try {

                // save file
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);

            } catch (IOException ex) {

                System.err.println(ex.getMessage());

            }
        }
    }
}
