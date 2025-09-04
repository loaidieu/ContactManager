package org.example.contactmanager;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyEvent;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.google.i18n.phonenumbers.NumberParseException;
import javafx.scene.input.MouseEvent;

public class DialogController {
    private boolean isPhoneNumberValid = false;
    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtPhoneNumber;
    @FXML
    private TextArea txtNotes;

    @FXML
    public void initialize(){
        // set up  the focus listener for phonenumber textfield
        txtPhoneNumber.textProperty().addListener((observableValue, aBoolean, t1) -> {
                PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
                try{
                    Phonenumber.PhoneNumber phone = phoneUtil.parse(txtPhoneNumber.getText(), "US");
                    boolean isValid = phoneUtil.isValidNumber(phone);
                    if(!isValid){
                        txtPhoneNumber.setStyle("-fx-text-fill: red");
                        isPhoneNumberValid = false;
                    }else{
                        txtPhoneNumber.setStyle("-fx-text-fill: black");
                        String formatted = phoneUtil.format(phone, PhoneNumberUtil.PhoneNumberFormat.NATIONAL);
                        txtPhoneNumber.setText(formatted);
                        isPhoneNumberValid = true;
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
        });

    }

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
        if(!isPhoneNumberValid){
            sb.append("Phone number is invalid.");
            valid = false;
        }
        return new Result(valid, sb.toString());
    }

}
