package org.example.contactmanager;

public class DeleteContactCommand implements ICommand{
    private Contact contact;

    public DeleteContactCommand(Contact contact) {
        this.contact = contact;
    }

    @Override
    public void execute() {
        ContactData.getInstance().getContactList().remove(contact);
    }

    @Override
    public void undo() {
        ContactData.getInstance().getContactList().add(contact);
    }
}
