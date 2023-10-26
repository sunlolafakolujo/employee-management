package com.logic.gate.employee.staticdata;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum EmployeeStatus {
    DELETED("Deleted"),
    ACTIVE("Active"),
    RETIRED("Retired"),
    RESIGNED("Resigned"),
    SACKED("Sacked");

    private final String employeeStatus;

    public String getEmployeeStatus() {
        return employeeStatus;
    }
}
