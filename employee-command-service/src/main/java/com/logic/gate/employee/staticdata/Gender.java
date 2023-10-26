package com.logic.gate.employee.staticdata;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Gender {
    MALE("Male"),
    FEMALE("Female");

    private final String gender;

    public String getGender() {
        return gender;
    }
}
