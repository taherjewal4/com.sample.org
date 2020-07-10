package com.sample.org.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ServiceType {
    NANE("name"),All("all"), CODE("code");

    @Getter
    private String value;

    public String toString() {
        return value;
    }


}
