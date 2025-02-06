package org.example.empresa.config.filters;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class UsernameJsonCreator {
    @JsonProperty("username")
    private String name;

    public UsernameJsonCreator(String name) {
        this.name = name;
    }
}
