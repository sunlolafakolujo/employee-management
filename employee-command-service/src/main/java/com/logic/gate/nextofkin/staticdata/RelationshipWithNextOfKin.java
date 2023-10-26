package com.logic.gate.nextofkin.staticdata;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum RelationshipWithNextOfKin {
    PARENT("Parent"),
    GUARDIAN("Guardian"),
    SIBLING("Sibling"),
    COUSIN("Cousin");

    private final String relationshipWithNextOfKin;

    public String getRelationshipWithNextOfKin() {
        return relationshipWithNextOfKin;
    }
}
