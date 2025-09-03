package org.example.contactmanager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ContactData {
    private String fileName = "contact.txt";
    private ObservableList<Contact> contactList = FXCollections.observableArrayList();;
    private static ContactData instance = new ContactData();

    private ContactData(){}

    public static ContactData getInstance(){
        return instance;
    }

    public ObservableList<Contact> getContactList(){
        return this.contactList;
    }

    public void loadContact() throws IOException{
        Path path = Paths.get(fileName);
        BufferedReader br = Files.newBufferedReader(path);
        String line = br.readLine();
            while(line != null) {
                String[] pieces = line.split("\\|");
                String[] names = pieces[0].split(" ");
                Contact c = new Contact(names[0], names[1], pieces[1], pieces[2]);
                contactList.add(c);
                line = br.readLine();
            }
        if (br != null){
            br.close();
        }
    }
}
