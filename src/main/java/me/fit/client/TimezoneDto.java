package me.fit.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TimezoneDto {
    public String timeZone;

    @JsonProperty("dateTime")
    public String currentLocalTime;
}