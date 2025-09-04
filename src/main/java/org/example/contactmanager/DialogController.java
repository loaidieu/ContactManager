package org.example.contactmanager;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DialogController {
    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtPhoneNumber;
    @FXML
    private TextArea txtNotes;

    @FXML
    public Result saveNewContact(){
        Result res = checkInput();
        if(res.isSuccessful()){
            ContactData.getInstance().getContactList().add(new Contact(txtFirstName.getText().trim()
            , txtLastName.getText().trim(), txtPhoneNumber.getText().trim(), txtNotes.getText().trim()));
            return new Result(true, "sucessfully created new contact.");
        }
        else{
            return new Result(false, res.getMessage());
        }
    }

    @FXML
    public Result saveEditContact(Contact contact){
        Result res = checkInput();
        if(res.isSuccessful()){
            contact.setFirstName(txtFirstName.getText());
            contact.setLastName(txtLastName.getText());
            contact.setPhoneNumber(txtPhoneNumber.getText());
            contact.setNotes(txtNotes.getText());
            return new Result(true, "Successfully edited a contact.");
        }
        else{
            return res;
        }
    }

    @FXML
    public void loadSelectedContact(Contact contact) {
        txtFirstName.setText(contact.getFirstName());
        txtLastName.setText(contact.getLastName());
        txtPhoneNumber.setText(contact.getPhoneNumber());
        txtNotes.setText(contact.getNotes());
    }

    private Result checkInput(){
        StringBuilder sb = new StringBuilder("Error: ");
        boolean valid = true;
        if(txtFirstName.getText().trim().equals("")){
            sb.append("First name is left empty.");
            valid = false;
        }
        if(txtLastName.getText().trim().equals("")){
            sb.append("Last name is left empty.");
            valid = false;
        }
        if(txtPhoneNumber.getText().trim().equals("")){
            sb.append("First name is left empty.");
            valid = false;
        }
        return new Result(valid, sb.toString());
    }
}
