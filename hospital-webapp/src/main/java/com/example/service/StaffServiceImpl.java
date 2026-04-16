package com.example.service;

import com.example.model.StaffMember;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of StaffService.
 * In a real app, this would use a Database/Hibernate.
 * Here, we use a simple in-memory list for demonstration.
 */
public class StaffServiceImpl implements StaffService {

    private static final List<StaffMember> STAFF_DATABASE = new ArrayList<>();

    static {
        // Mock data
        STAFF_DATABASE.add(new StaffMember("Dr. John Doe", "john.doe@sanitycare.com"));
        STAFF_DATABASE.add(new StaffMember("Dr. Jane Smith", "jane.smith@sanitycare.com"));
        STAFF_DATABASE.add(new StaffMember("Nurse Emily Mbogo", "e.mbogo@sanitycare.com"));
    }

    @Override
    public void addStaff(StaffMember member) {
        STAFF_DATABASE.add(member);
    }

    @Override
    public List<StaffMember> getAllStaff() {
        return new ArrayList<>(STAFF_DATABASE);
    }
}
