module org.example.assignment {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.datatransfer;
    requires java.desktop;


    opens org.example.assignment to javafx.fxml;
    exports org.example.assignment;
}