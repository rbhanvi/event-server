package com.tietoevry.serverskeletonjava.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Event {
    private String socSecNum;
    private String eventType;
    private String value;
    @Id
    private Long sequenceNumber;
    private LocalDateTime timestamp;
}
