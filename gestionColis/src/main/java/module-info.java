module com.example.gestioncolis {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;

    opens com.example.gestioncolis to javafx.fxml;
    exports com.example.gestioncolis;
    exports com.example.gestioncolis.service;
    opens com.example.gestioncolis.service to javafx.fxml;
    opens com.example.gestioncolis.entities to javafx.base;
}