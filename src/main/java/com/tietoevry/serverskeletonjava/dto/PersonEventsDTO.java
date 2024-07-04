package com.tietoevry.serverskeletonjava.dto;

import com.tietoevry.serverskeletonjava.model.Event;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PersonEventsDTO extends PersonDTO {

    private List<Event> events;

}
