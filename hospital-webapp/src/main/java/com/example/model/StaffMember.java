package com.example.model;

import com.example.framework.HospitalForm;
import com.example.framework.HospitalFormField;
import com.example.framework.HospitalTable;
import com.example.framework.HospitalTableCol;
import java.io.Serializable;

/**
 * A simple POJO to represent a Hospital Staff Member.
 * Implements Serializable for potential session persistence.
 */
@HospitalForm(label = "Staff Enrollment", actionUrl = "./staff_enroll")
@HospitalTable(label = "Hospital Staff", tableUrl = "./staff_lists", registerUrl = "./staff_enroll")
public class StaffMember implements Serializable {

    @HospitalFormField(label = "Full Name", placeholder = "e.g. Dr. Jane Smith")
    @HospitalTableCol(label = "Full Name")
    private String fullName;

    @HospitalFormField(label = "Email Address", placeholder = "e.g. j.smith@sanitycare.com", type = "email")
    @HospitalTableCol(label = "Email")
    private String email;

    public StaffMember() {
    }

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
