module BanVeTau {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.media;
	requires java.sql;
	requires sqljdbc42;
	opens control to javafx.fxml;
	opens application to javafx.graphics, javafx.fxml, javafx.media;
}
