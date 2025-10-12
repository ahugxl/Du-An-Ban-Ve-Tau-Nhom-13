package control;

import java.io.File;
import java.net.URL;

import javafx.fxml.FXML;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

public class ControllerFunVideo {

    @FXML private MediaView myVideo;

    private MediaPlayer mediaPlayer;

    @FXML
    public void initialize() {
        // 1) Ưu tiên video trong classpath: đặt file ở resources/video/vd1.mp4
        String source = null;
        URL url = getClass().getResource("/video/vd1.mp4");
        if (url != null) {
            source = url.toExternalForm();
        } else {
            // 2) Fallback: file ngoài dự án (cùng thư mục chạy hoặc đường dẫn tùy bạn)
            File f = new File("video/vd1.mp4");
            if (!f.exists()) {
                System.err.println("Không tìm thấy video (classpath hay ngoài): " + f.getAbsolutePath());
                return;
            }
            source = f.toURI().toString();
        }

        Media media = new Media(source);
        media.setOnError(() ->
            System.err.println("Media error: " + media.getError())
        );

        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setOnError(() ->
            System.err.println("MediaPlayer error: " + mediaPlayer.getError())
        );

        // 3) Gắn player cho MediaView trước khi play
        myVideo.setMediaPlayer(mediaPlayer);
        myVideo.setPreserveRatio(true); // giữ tỉ lệ

        // 4) Tự co giãn theo Scene (initialize có thể Scene chưa sẵn sàng)
        myVideo.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                myVideo.fitWidthProperty().bind(newScene.widthProperty());
                myVideo.fitHeightProperty().bind(newScene.heightProperty());
            }
        });

        // 5) Lặp vô hạn + tự phát
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setAutoPlay(true);
        // mediaPlayer.play(); // không bắt buộc vì đã setAutoPlay(true)
     // Khi Scene/Window sẵn sàng, gắn xử lý đóng:
        myVideo.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                // nếu Window đã có ngay
                Window w = newScene.getWindow();
                if (w != null) {
                    w.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, e -> dispose());
                    w.addEventHandler(WindowEvent.WINDOW_HIDDEN, e -> dispose()); // phòng khi bị hide
                }
                // hoặc đợi khi Window được gán sau đó
                newScene.windowProperty().addListener((o, oldW, newW) -> {
                    if (newW != null) {
                        newW.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, e -> dispose());
                        newW.addEventHandler(WindowEvent.WINDOW_HIDDEN, e -> dispose());
                    }
                });
            }
        });
    }

    // (tuỳ chọn) Hàm dọn tài nguyên khi rời màn hình
    public void dispose() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
        }
    }
}
