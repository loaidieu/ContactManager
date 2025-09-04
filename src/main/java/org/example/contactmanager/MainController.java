package org.example.contactmanager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Optional;

public class MainController {
    @FXML
    private BorderPane mainPane;
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
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    public void showNewContactDialog(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(this.mainPane.getScene().getWindow());
        dialog.setTitle("Adding new contact.");
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("contactDialog.fxml"));
        try{
            dialog.getDialogPane().setContent((Node)loader.load());
        }catch(IOException e){
            System.out.println("Could not load the dialog");
            e.printStackTrace();
            return;
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && ((ButtonType)result.get()).equals(ButtonType.OK)){
            DialogController ctrl = loader.getController();
            Result res = ctrl.saveNewContact();
            Alert messageBox = new Alert(Alert.AlertType.INFORMATION);
            messageBox.setTitle("Result on creating new Contact");
            messageBox.setContentText(res.getMessage());
        }
    }

    public void showEditContactDialog(){
        Contact contact = (Contact)tableView.getSelectionModel().getSelectedItem();
        if (contact == null)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initOwner(this.mainPane.getScene().getWindow());
            alert.setTitle("Error");
            alert.setContentText("Please select a contact first.");
            alert.showAndWait();
            return;
        }
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(this.mainPane.getScene().getWindow());
        dialog.setTitle(String.format("Editing contact of %s", contact.getFirstName()));
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApplication.class.getResource("contactDialog.fxml"));
        try{
            dialog.getDialogPane().setContent((Node)loader.load());
        }
        catch (IOException e){
            System.out.println("Could not load the dialog");
            e.printStackTrace();
            return;
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        DialogController controller = (DialogController)loader.getController();
        controller.loadSelectedContact(contact);
        Optional<ButtonType> res = dialog.showAndWait();
        if (res.isPresent() && ((ButtonType)res.get()).equals(ButtonType.OK)){
            Result response = controller.saveEditContact(contact);
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Result from editting a contact.");
            a.setContentText(response.getMessage());
            a.showAndWait();
        }
    }

    public void deleteContact(){
        Contact contact = (Contact)tableView.getSelectionModel().getSelectedItem();
        if(contact == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error in deleting a contact");
            alert.setContentText("Please select a contact first.");
            alert.showAndWait();
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.initOwner(this.mainPane.getScene().getWindow());
        confirm.setTitle("Deleting a contact.");
        confirm.setContentText(String.format("Are you sure to delete %s from your contact list?", contact.getFirstName() + " " + contact.getLastName()));
        Optional<ButtonType> answer = confirm.showAndWait();
        if(answer.isPresent() && ((ButtonType)answer.get()).equals(ButtonType.OK)){
            boolean success = ContactData.getInstance().deleteContact(contact);
            if(success){
                Alert con = new Alert(Alert.AlertType.INFORMATION);
                con.initOwner(mainPane.getScene().getWindow());
                con.setTitle("Deleting a contact");
                con.setContentText("Successfully deleting a contact.");
                con.showAndWait();
            }
            else{
                Alert con = new Alert(Alert.AlertType.INFORMATION);
                con.initOwner(mainPane.getScene().getWindow());
                con.setTitle("Deleting a contact");
                con.setContentText("Unsuccessfully deleting a contact.");
                con.showAndWait();
            }
        }
    }
}
