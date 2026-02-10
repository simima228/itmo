package com.example.etc;

public class CommandStatus {
    private String message;
    private boolean status;
    public CommandStatus(boolean status, String message) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public boolean getStatus() {
        return status;
    }

}
