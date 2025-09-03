package org.example.contactmanager;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class HelloController {
    @FXML
    private TableColumn<Contact, String> columnFirstName;
    @FXML
    private TableColumn<Contact, String> columnLastName;
    @FXML
    private TableColumn<Contact, String> columnPhoneNumber;
    @FXML
    private TableColumn<Contact, String> columnNotes;
    @FXML
    private TableView tableView;

    @FXML
    public void initialize(){
        //Binding the column with model properties
        columnFirstName.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        columnLastName.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        columnPhoneNumber.setCellValueFactory(cellData -> cellData.getValue().phoneNumberProperty());
        columnNotes.setCellValueFactory(cellData -> cellData.getValue().notesProperty());
        System.out.println("running");
        //Binding tableView with contact list
        tableView.setItems(ContactData.getInstance().getContactList());

    }
}
