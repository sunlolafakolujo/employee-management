package com.logic.gate.employee.staticdata;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Title {
    MR("Mr"),
    MRS("Mrs"),
    MISS("Miss"),
    MS("Ms");

    private final String title;

    public String getTitle() {
        return title;
    }
}
