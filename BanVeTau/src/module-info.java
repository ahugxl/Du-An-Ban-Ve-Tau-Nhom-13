module BanVeTau {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.media;
	
	opens control to javafx.fxml;
	opens application to javafx.graphics, javafx.fxml, javafx.media;
}
