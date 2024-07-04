package com.tietoevry.serverskeletonjava.service;

import com.tietoevry.serverskeletonjava.dao.EventDAO;
import com.tietoevry.serverskeletonjava.dao.PersonDAO;
import com.tietoevry.serverskeletonjava.dto.EventDTO;
import com.tietoevry.serverskeletonjava.model.Event;
import com.tietoevry.serverskeletonjava.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import com.tietoevry.serverskeletonjava.dto.PersonEventsDTO;

@Service
public class EventService {

    @Autowired
    private EventDAO eventDAO;

    @Autowired
    private PersonDAO personDAO;

    public void saveEvent(EventDTO eventDto) {
        Event event = new Event();
        event.setEventType(eventDto.getEventType());
        event.setValue(eventDto.getValue().toString());
        event.setTimestamp(LocalDateTime.parse(eventDto.getTimestamp()));
        event.setSequenceNumber(eventDto.getSequenceNumber());
        event.setSocSecNum(eventDto.getSocSecNum());

        eventDAO.save(event);
    }


    public List<Event> getEventsFromSequence(Long startSequenceNumber) {
        // Implement logic to fetch events from startSequenceNumber
        return eventDAO.findBySequenceNumberGreaterThanEqual(startSequenceNumber);
    }

    public Event getEventBySequence(Long sequenceNumber) {
        // Implement logic to fetch event by sequenceNumber
        return eventDAO.findBySequenceNumber(sequenceNumber);
    }

    public List<Event> getAllEvents() {
        return eventDAO.findAll();
    }


    public PersonEventsDTO getPersonWithEvents(String socSecNum) {
        PersonEventsDTO personEventsDTO = new PersonEventsDTO();
        // Fetch person by socSecNum excluding logically deleted records
        Person person = personDAO.findBySocSecNumAndDeletedFalse(socSecNum).orElse(null);

        if (person != null) {
            // Fetch events associated with the person
            List<Event> events = eventDAO.findBySocSecNum(socSecNum);
            personEventsDTO.setEvents(events);
            personEventsDTO.setAddress(person.getAddress());
            personEventsDTO.setEmail(person.getEmail());
            personEventsDTO.setName(person.getName());
            personEventsDTO.setPhone(person.getPhone());
            personEventsDTO.setSocSecNum(person.getSocSecNum());
        }
        return personEventsDTO;
    }

    public Person getPerson(String socSecNum) {

        return personDAO.findBySocSecNumAndDeletedFalse(socSecNum).orElse(null);
    }
}

