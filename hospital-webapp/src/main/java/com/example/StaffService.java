package com.example;

import java.util.List;

/**
 * Service interface for managing Hospital Staff members.
 */
public interface StaffService {
    void addStaff(StaffMember member);
    List<StaffMember> getAllStaff();
}
