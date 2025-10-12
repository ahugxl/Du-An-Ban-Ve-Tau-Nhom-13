package utils;

import javafx.scene.layout.*;
import javafx.scene.image.Image;
import java.io.File;
import java.net.URL;

public final class BgUtils {
    private BgUtils() {}

    public static void setBackgroundFromResource(Region region, String resourcePath) {
        URL url = region.getClass().getResource(resourcePath);
        if (url == null) { System.err.println("Missing: " + resourcePath); return; }
        setBackgroundFromUrl(region, url.toExternalForm());
    }

    public static void setBackgroundFromFile(Region region, File file) {
        if (file == null || !file.exists()) { System.err.println("File not found"); return; }
        setBackgroundFromUrl(region, file.toURI().toString());
    }

    public static void clearBackground(Region region) {
        region.setBackground(Background.EMPTY);
    }

    private static void setBackgroundFromUrl(Region region, String url) {
        Image img = new Image(url, true);
        BackgroundSize size = new BackgroundSize(100, 100, true, true, false, false);
        BackgroundImage bg = new BackgroundImage(
                img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, size
        );
        region.setBackground(new Background(bg));
    }
}
