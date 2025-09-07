package org.example.contactmanager;

import javafx.collections.transformation.FilteredList;

public class AddContactCommand implements ICommand{

    private Contact contact;

    public AddContactCommand(Contact contact) {
        this.contact = contact;
    }

    @Override
    public void execute() {
        ContactData.getInstance().getContactList().add(contact);
    }

    @Override
    public void undo() {
        ContactData.getInstance().getContactList().remove(contact);
    }
}
