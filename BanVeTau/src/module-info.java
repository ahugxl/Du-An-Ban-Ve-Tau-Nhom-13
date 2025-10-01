module BanVeTau {
	requires javafx.controls;
	requires javafx.fxml;
	opens control to javafx.fxml;
	opens application to javafx.graphics, javafx.fxml;
}
