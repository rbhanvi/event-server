package com.tietoevry.serverskeletonjava.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventDTO {

    private String socSecNum;
    private String eventType;
    @JsonDeserialize(using = ValueDeserializer.class)
    private JsonNode value; // Can be a string or a Person object
    private Long sequenceNumber;
    private String timestamp;
}

