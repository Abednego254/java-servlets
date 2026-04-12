package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Concrete implementation of StaffService.
 * Uses a static thread-safe list to mimic a database.
 */
public class StaffServiceImpl implements StaffService {

    // Using CopyOnWriteArrayList for thread-safe concurrent access across sessions
    private static final List<StaffMember> staffDirectory = new CopyOnWriteArrayList<>();

    // Initialize with some seed data
    static {
        staffDirectory.add(new StaffMember("Dr. Victor Von Doom", "doom@sanitycare.com"));
        staffDirectory.add(new StaffMember("Nurse Joy", "joy@sanitycare.com"));
    }

    @Override
    public void addStaff(StaffMember member) {
        staffDirectory.add(member);
        System.out.println("==> StaffService: Registered new staff member: " + member.getFullName());
    }

    @Override
    public List<StaffMember> getAllStaff() {
        return new ArrayList<>(staffDirectory);
    }
}
