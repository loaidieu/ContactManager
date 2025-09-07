package org.example.contactmanager;

import javafx.scene.control.Alert;

import java.util.Stack;

public class CommandManager {
    private CommandManager(){}
    private static CommandManager instance = new CommandManager();
    private Stack<ICommand> undoStack = new Stack<>();
    private Stack<ICommand> redoStack = new Stack<>();

    public static CommandManager getInstance(){
        return instance;
    }

    public void execute(ICommand command){
        undoStack.push(command);
        command.execute();
    }

    public void undo(){
        if(undoStack.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Undo Error");
            alert.setContentText("Could not undo since there is no action to be undone.");
            alert.showAndWait();
        }
        else{
            ICommand command = undoStack.pop();
            redoStack.push(command);
            command.undo();
        }
    }

    public void redo(){
        if(redoStack.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Redo Error");
            alert.setContentText("Could not redo since there is no action to be redone.");
            alert.showAndWait();
        }
        else{
            ICommand command = redoStack.pop();
            undoStack.push(command);
            command.execute();
        }
    }

}
