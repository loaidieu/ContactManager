package org.example.contactmanager;

public class Result {
    private boolean successful;
    private String message;

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Result(boolean successful, String message) {
        this.successful = successful;
        this.message = message;
    }
}
