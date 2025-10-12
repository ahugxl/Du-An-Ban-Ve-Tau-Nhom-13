module BanVeTau {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.media;
	requires java.sql;
	requires sqljdbc42;
	requires javafx.base;
	requires javafx.graphics;
	opens control to javafx.fxml;
	exports control;
	opens entity to javafx.base;
	opens application to javafx.graphics, javafx.fxml, javafx.media;
}
