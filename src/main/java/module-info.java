module org.example.contactmanager {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.contactmanager to javafx.fxml;
    exports org.example.contactmanager;
}