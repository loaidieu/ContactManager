package org.example.contactmanager;

public class EditContactCommand implements ICommand{
    private Contact contact;
    private String oldFN;
    private String oldLN;
    private String oldPN;
    private String oldNotes;
    private String oldImageName;

    private String newFN;
    private String newLN;
    private String newPN;
    private String newNotes;
    private String newImageName;

    public EditContactCommand(Contact contact, String imageName,String newFN, String newLN, String newPN, String newNotes) {
        this.contact = contact;
        this.oldImageName = contact.getImageName();;
        this.oldFN = contact.getFirstName();
        this.oldLN = contact.getLastName();
        this.oldPN = contact.getPhoneNumber();
        this.oldNotes = contact.getNotes();

        this.newImageName = imageName;
        this.newFN = newFN;
        this.newLN = newLN;
        this.newPN = newPN;
        this.newNotes = newNotes;
    }

    @Override
    public void execute() {
        apply(newImageName, newFN, newLN, newPN, newNotes);
    }

    @Override
    public void undo() {
        apply(oldImageName, oldFN, oldLN, oldPN, oldNotes);
    }

    private void apply(String imagePath, String fn, String ln, String phone, String notes){
        contact.setImageName(imagePath);
        contact.setFirstName(fn);
        contact.setLastName(ln);
        contact.setPhoneNumber(phone);
        contact.setNotes(notes);
    }
}
