package com.fam.model;

public enum Role {
    CHILD("Child"),
    PARENT("Parent");

    private final String value;

    private Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


}