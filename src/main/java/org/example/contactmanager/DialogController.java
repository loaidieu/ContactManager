package org.example.contactmanager;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.google.i18n.phonenumbers.NumberParseException;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.*;

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
    private Button btnLoadImage;
    @FXML
    private DialogPane dialogPane;
    @FXML
    private ImageView lblImageView;

    public static final String ABSOLUTE_PATH = "C:/Users/loaid/IdeaProjects/ContactManager/src/main/resources/org/example/contactmanager/Images/";

    @FXML
    public void initialize(){
        //Add on action button load image
        btnLoadImage.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FileChooser chooser = new FileChooser();
                chooser.setTitle("Image Chooser");
                File file = chooser.showOpenDialog(dialogPane.getScene().getWindow());
                try{
                    Files.copy(Paths.get(file.getPath()), Paths.get(ABSOLUTE_PATH+ file.getName()), StandardCopyOption.REPLACE_EXISTING);
                }
                catch(Exception e){
                    e.printStackTrace();
                    System.out.println("Can't load image.");
                }
                lblImageView.setImage(new Image(((File)(new File(ABSOLUTE_PATH + file.getName()))).toURI().toString()));
            }
        });
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
            Path path = Paths.get(lblImageView.getImage().getUrl());
            File f = new File(path.toUri());
            CommandManager.getInstance().execute(new AddContactCommand(new Contact(f.getName(), txtFirstName.getText().trim()
                    , txtLastName.getText().trim(), txtPhoneNumber.getText().trim(), txtNotes.getText().trim())));
            return new Result(true, "sucessfully created new contact.");
        }
        else{
            return new Result(false, res.getMessage());
        }
    }

    @FXML
    public Result saveEditContact(Contact contact) throws URISyntaxException {
        Result res = checkInput();
        if(res.isSuccessful()){
            String fn = txtFirstName.getText();
            String ln = txtLastName.getText();
            String phone = txtPhoneNumber.getText();
            String notes = txtNotes.getText();
            File f = new File(new java.net.URI(lblImageView.getImage().getUrl()));
            CommandManager.getInstance().execute(new EditContactCommand(contact, f.getName(), fn, ln, phone, notes));
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
        try{
            lblImageView.setImage(new Image(new File(DialogController.ABSOLUTE_PATH + contact.getImageName()).toURI().toString()));
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("cannot load image");
        }

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
