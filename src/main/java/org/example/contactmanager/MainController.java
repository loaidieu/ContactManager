package org.example.contactmanager;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Predicate;

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
    private TextField txtSearch;
    @FXML
    private TableView tableView;
    private FilteredList<Contact> filteredList;

    @FXML
    public void initialize(){
        //Binding the column with model properties
        columnFirstName.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        columnLastName.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        columnPhoneNumber.setCellValueFactory(cellData -> cellData.getValue().phoneNumberProperty());
        columnNotes.setCellValueFactory(cellData -> cellData.getValue().notesProperty());
        //adding highlighting for all columns
        addHighlightMatch(columnFirstName, txtSearch);
        addHighlightMatch(columnLastName, txtSearch);
        addHighlightMatch(columnPhoneNumber, txtSearch);
        addHighlightMatch(columnNotes, txtSearch);
        //Binding tableView with contact list
        filteredList = new FilteredList<>(ContactData.getInstance().getContactList());
        filteredList.setPredicate(new Predicate<Contact>() {
            @Override
            public boolean test(Contact contact) {
                return true;
            }
        });
        tableView.setItems(filteredList);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tableView.setFixedCellSize(21);

        //Search Box text property listener
        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                filteredList.setPredicate(new Predicate<Contact>() {
                    @Override
                    public boolean test(Contact contact) {
                        if (txtSearch.getText().trim().equals("")){
                            return true;
                        }
                        String t1_low = t1.toLowerCase();
                        if (contact.getFirstName().toLowerCase().contains(t1_low) ||
                            contact.getLastName().toLowerCase().contains(t1_low) ||
                            contact.getPhoneNumber().toLowerCase().contains(t1_low) ||
                            contact.getNotes().toLowerCase().contains(t1_low)){
                            return true;
                        }
                        return false;
                    }
                });
            }
        });
    }
    //generic method to add the highlight to the match while searching
    private void addHighlightMatch(TableColumn<Contact, String> column, TextField searchField){
        column.setCellFactory(col -> new TableCell<Contact, String>(){
            @Override
            protected void updateItem(String item, boolean empty){
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    String search = searchField.getText().toLowerCase();

                    if (!search.isEmpty() && item.toLowerCase().contains(search)) {
                        int start = item.toLowerCase().indexOf(search);
                        int end = start + search.length();

                        Text before = new Text(item.substring(0, start));
                        Label match = new Label(item.substring(start, end));
                        Text after = new Text(item.substring(end));

                        // style the highlighted part
                        match.setStyle("-fx-font-weight: bold; -fx-background-color: yellow; -fx-text-fill: black; -fx-padding: 0; -fx-label-padding: 0");

                        TextFlow flow = new TextFlow();
                        flow.getChildren().addAll(before, match, after);
                        setGraphic(flow);
                        setText(null); // we must clear text if we use a graphic
                    } else {
                        // no match → just show normal text
                        setText(item);
                        setGraphic(null);
            }}}
        });
    }

    //menu
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
            Alert messageBox = new Alert(Alert.AlertType.ERROR);
            messageBox.setTitle("Result on creating new Contact");
            messageBox.setContentText(res.getMessage());
            messageBox.showAndWait();
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

    //phone number text field property

}
