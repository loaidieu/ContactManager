module org.example.contactmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires libphonenumber;


    opens org.example.contactmanager to javafx.fxml;
    exports org.example.contactmanager;
}