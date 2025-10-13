package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
//			Parent root = FXMLLoader.load(getClass().getResource("/gui/GD_DangNhap.fxml"));
//			String css=this.getClass().getResource("/gui/GD_DangNhap.css").toExternalForm();
			Parent root = FXMLLoader.load(getClass().getResource("/gui/GiaoDienChonVe.fxml"));
			String css=this.getClass().getResource("/gui/GiaoDienChonVe.css").toExternalForm();
			Scene scene = new Scene(root);
//			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			scene.getStylesheets().add(css);	
			primaryStage.setScene(scene);
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
