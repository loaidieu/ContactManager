package org.example.contactmanager;

public class EditContactCommand implements ICommand{
    private Contact contact;
    private String oldFN;
    private String oldLN;
    private String oldPN;
    private String oldNotes;

    private String newFN;
    private String newLN;
    private String newPN;
    private String newNotes;

    public EditContactCommand(Contact contact, String newFN, String newLN, String newPN, String newNotes) {
        this.contact = contact;
        this.oldFN = contact.getFirstName();
        this.oldLN = contact.getLastName();
        this.oldPN = contact.getPhoneNumber();
        this.oldNotes = contact.getNotes();

        this.newFN = newFN;
        this.newLN = newLN;
        this.newPN = newPN;
        this.newNotes = newNotes;
    }

    @Override
    public void execute() {
        apply(newFN, newLN, newPN, newNotes);
    }

    @Override
    public void undo() {
        apply(oldFN, oldLN, oldPN, oldNotes);
    }

    private void apply(String fn, String ln, String phone, String notes){
        contact.setFirstName(fn);
        contact.setLastName(ln);
        contact.setPhoneNumber(phone);
        contact.setNotes(notes);
    }
}
