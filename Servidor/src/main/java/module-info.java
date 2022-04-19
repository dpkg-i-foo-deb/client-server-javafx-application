module co.edu.uniquindio.application
{
	requires transitive javafx.graphics;
    requires javafx.controls;
    exports co.edu.uniquindio.servidor.application;
	requires java.desktop;
	requires java.logging;
	
	exports co.edu.uniquindio.farmacia.model;
}