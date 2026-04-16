package com.example.service;

public class    AppointmentServiceImpl implements AppointmentService {

    @Override
    public String bookAppointment(String firstName, String lastName, String email, String phone, 
                                  String date, String time, String department, String reason) {
        
        // Basic business logic validation
        if (firstName == null || firstName.trim().isEmpty() ||
            lastName  == null || lastName.trim().isEmpty()  ||
            email     == null || email.trim().isEmpty()     ||
            department == null || department.trim().isEmpty()) {
            throw new IllegalArgumentException("Missing required fields.");
        }

        // Generate a unique appointment reference.
        // In a real system this would persist to a database and auto-increment.
        int randId = (int)(Math.random() * 900000 + 100000);
        return "SCH-" + randId;
    }

    @Override
    public String getDepartmentDisplayName(String departmentCode) {
        if (departmentCode == null) return "Unknown";
        switch (departmentCode) {
            case "cardiology":    return "Cardiology";
            case "neurology":     return "Neurology & Neurosurgery";
            case "orthopaedics":  return "Orthopaedics & Trauma";
            case "paediatrics":   return "Paediatrics";
            case "obs-gynae":     return "Obstetrics & Gynaecology";
            case "oncology":      return "Oncology";
            case "general":       return "General Medicine (GP)";
            default:              return departmentCode;
        }
    }
}
