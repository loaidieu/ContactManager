package org.example.contactmanager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends javafx.application.Application {
    private ContactData contactData = ContactData.getInstance();

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("mainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900 , 500);
        stage.setTitle("My Contacts");
        stage.setScene(scene);
        stage.show();
        ContactData.getInstance().loadContact();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        ContactData.getInstance().storeContacts();
        System.out.println("exit");
    }
}
