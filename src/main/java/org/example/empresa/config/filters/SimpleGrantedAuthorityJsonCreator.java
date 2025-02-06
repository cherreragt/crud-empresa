package org.example.empresa.config.filters;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class SimpleGrantedAuthorityJsonCreator {
    protected SimpleGrantedAuthorityJsonCreator(@JsonProperty("authority") String role) {}
}
