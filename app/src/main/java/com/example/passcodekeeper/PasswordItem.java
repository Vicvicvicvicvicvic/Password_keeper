package com.example.passcodekeeper;

public class PasswordItem {
    String password;
    String description;

    public PasswordItem(String password, String description) {
        this.password = password;
        this.description = description;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
