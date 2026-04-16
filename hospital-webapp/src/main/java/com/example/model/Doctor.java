package com.example.model;

import com.example.framework.HospitalForm;
import com.example.framework.HospitalFormField;
import com.example.framework.HospitalTable;
import com.example.framework.HospitalTableCol;
import java.io.Serializable;

@HospitalForm(label = "Doctor Enrollment", actionUrl = "./reg_doctor")
@HospitalTable(label = "Hospital Doctors", tableUrl = "./doctor_lists", registerUrl = "./reg_doctor")
public class Doctor implements Serializable {

    @HospitalFormField(label = "SPECIALTY")
    @HospitalTableCol(label = "SPECIALIZATION")
    private String specialty;

    @HospitalFormField(label = "LICENCE ID")
    @HospitalTableCol(label = "ID")
    private String licenceId;

    public Doctor() {}

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getLicenceId() {
        return licenceId;
    }

    public void setLicenceId(String licenceId) {
        this.licenceId = licenceId;
    }
}
