module org.monopoly{
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens org.monopoly.controller to javafx.fxml;
    exports org.monopoly;
    exports org.monopoly.controller;
}