module co.edu.uniquindio.farmacia.application {
    requires transitive javafx.controls;
    requires javafx.fxml;
	requires java.desktop;
	requires java.logging;
	requires javafx.graphics;
	requires javafx.base;

    opens co.edu.uniquindio.farmacia.application to javafx.fxml;
    opens co.edu.uniquindio.farmacia.controllers to javafx.fxml;
    exports co.edu.uniquindio.farmacia.controllers to javafx.fxml;
    exports co.edu.uniquindio.farmacia.application;
    exports co.edu.uniquindio.farmacia.model;
}