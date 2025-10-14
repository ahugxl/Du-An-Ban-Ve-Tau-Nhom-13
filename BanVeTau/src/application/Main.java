package application;
	
import java.util.Objects;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
//			Parent root = FXMLLoader.load(getClass().getResource("/gui/GD_DangNhap.fxml"));
//			String css=this.getClass().getResource("/gui/GD_DangNhap.css").toExternalForm();
			Parent root = FXMLLoader.load(getClass().getResource("/gui/GD_Chinh.fxml"));
			String css=this.getClass().getResource("/gui/GD_Chinh.css").toExternalForm();
			Scene scene = new Scene(root);
//			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			scene.getStylesheets().add(css);	
			primaryStage.setScene(scene);
			Image icon = new Image(
			    Objects.requireNonNull(getClass().getResource("/image/icon_logo.png"))
			            .toExternalForm()
			);

			primaryStage.getIcons().add(icon);
			primaryStage.setTitle("Phần mềm bán vé tàu");
			primaryStage.setMaximized(true);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
