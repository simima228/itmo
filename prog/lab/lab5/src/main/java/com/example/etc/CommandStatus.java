package com.example.etc;

public class CommandStatus {
    private String message;
    private boolean status;
    public CommandStatus(boolean status, String message) {
        this.message = message;
        this.status = status;
    }

}
