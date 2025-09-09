package org.example.contactmanager;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;

public class Contact {
    private StringProperty firstName = new SimpleStringProperty();
    private StringProperty lastName = new SimpleStringProperty();
    private StringProperty phoneNumber = new SimpleStringProperty();
    private StringProperty notes = new SimpleStringProperty();
    private StringProperty imageName = new SimpleStringProperty() ;

    public Contact(String imageName, String firstName, String lastName, String phoneNumber, String notes) {
        this.imageName.setValue(imageName);
        this.firstName.setValue(firstName);
        this.lastName.setValue(lastName);
        this.phoneNumber.setValue(phoneNumber);
        this.notes.setValue(notes);
    }

    public String getImageName() {
        return imageName.get();
    }

    public StringProperty imageNameProperty() {
        return imageName;
    }

    public void setImageName(String imagePath) {
        this.imageName.set(imagePath);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public StringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public String getNotes() {
        return notes.get();
    }

    public StringProperty notesProperty() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes.set(notes);
    }


    @Override
    public String toString() {
        return "Contact{" +
                "firstName=" + firstName +
                ", lastName=" + lastName +
                ", phoneNumber=" + phoneNumber +
                ", notes=" + notes +
                ", imageName=" + imageName +
                '}';
    }
}
