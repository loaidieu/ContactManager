package org.example.contactmanager;

import java.util.Collections;

public class SwapCommand implements ICommand{
    private int fromIndex;
    private int toIndex;

    public SwapCommand(int fromIndex, int toIndex) {
        this.fromIndex = fromIndex;
        this.toIndex = toIndex;
    }

    public int getFromIndex() {
        return fromIndex;
    }

    public void setFromIndex(int fromIndex) {
        this.fromIndex = fromIndex;
    }

    public int getToIndex() {
        return toIndex;
    }

    public void setToIndex(int toIndex) {
        this.toIndex = toIndex;
    }

    @Override
    public void execute() {
        Collections.swap(ContactData.getInstance().getContactList(), fromIndex, toIndex);
    }

    @Override
    public void undo() {
        Collections.swap(ContactData.getInstance().getContactList(), toIndex, fromIndex);
    }
}
