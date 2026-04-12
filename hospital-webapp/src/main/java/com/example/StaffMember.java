package com.example;

import java.io.Serializable;

/**
 * A simple POJO to represent a Hospital Staff Member.
 * Implements Serializable for potential session persistence.
 */
public class StaffMember implements Serializable {
    private String fullName;
    private String email;

    public StaffMember(String fullName, String email) {
        this.fullName = fullName;
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
