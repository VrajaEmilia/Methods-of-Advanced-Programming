module gui.mapgui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens gui.mapgui to javafx.fxml;
    exports gui.mapgui;
}