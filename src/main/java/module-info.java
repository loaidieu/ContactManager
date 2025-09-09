module org.example.contactmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires libphonenumber;
    requires java.xml;
    requires jdk.unsupported.desktop;


    opens org.example.contactmanager to javafx.fxml;
    exports org.example.contactmanager;
}