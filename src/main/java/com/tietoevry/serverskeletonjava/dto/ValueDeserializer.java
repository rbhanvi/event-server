package com.tietoevry.serverskeletonjava.dto;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ValueDeserializer extends JsonDeserializer<JsonNode> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public JsonNode deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return objectMapper.readTree(p);
    }
}