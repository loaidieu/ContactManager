package org.example.contactmanager;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Contact {
    private StringProperty firstName = new SimpleStringProperty();
    private StringProperty lastName = new SimpleStringProperty();
    private StringProperty phoneNumber = new SimpleStringProperty();
    private StringProperty notes = new SimpleStringProperty();

    public Contact(String firstName, String lastName, String phoneNumber, String notes) {
        this.firstName.setValue(firstName);
        this.lastName.setValue(lastName);
        this.phoneNumber.setValue(phoneNumber);
        this.notes.setValue(notes);
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
                '}';
    }
}
