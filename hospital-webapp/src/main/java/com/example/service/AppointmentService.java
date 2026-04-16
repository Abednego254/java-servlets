package com.example.service;

public interface AppointmentService {

    String bookAppointment(String firstName, String lastName, String email, String phone, 
                           String date, String time, String department, String reason);

    String getDepartmentDisplayName(String departmentCode);
}
