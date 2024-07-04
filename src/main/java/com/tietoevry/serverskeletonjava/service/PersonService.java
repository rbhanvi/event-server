package com.tietoevry.serverskeletonjava.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.tietoevry.serverskeletonjava.dao.PersonDAO;
import com.tietoevry.serverskeletonjava.dto.EventDTO;
import com.tietoevry.serverskeletonjava.dto.PersonDTO;
import com.tietoevry.serverskeletonjava.model.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
@Slf4j
public class PersonService {

    @Autowired
    private PersonDAO personDAO;

    private Person parsePerson(JsonNode value) {
        try {
            return new ObjectMapper().treeToValue(value, Person.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updatePerson(EventDTO event) throws Exception {

        Optional<Person> personOptional = personDAO.findById(event.getSocSecNum());
        Person person = personOptional.orElseGet(() -> new Person());

        log.info("Person details - {}, Person name - {}", person.getSocSecNum(), person.getName());

        switch (event.getEventType()) {
            case "PERSON_CREATED":
                Person newPerson = parsePerson(event.getValue());
                newPerson.setSocSecNum(event.getSocSecNum());
                personDAO.save(newPerson);
                break;
            case "SOCSECNUM_CHANGE":
                person.setDeleted(true);

                Person updatePerson = new Person();
                updatePerson.setName(person.getName());
                updatePerson.setAddress(person.getAddress());
                updatePerson.setEmail(person.getEmail());
                updatePerson.setPhone(person.getPhone());

                updatePerson.setSocSecNum(event.getValue().asText());
                personDAO.save(updatePerson);
                break;
            case "ADDRESS_CHANGE":
                person.setAddress(event.getValue().asText());
                personDAO.save(person);
                break;
            case "NAME_CHANGE":
                person.setName(event.getValue().asText());
                personDAO.save(person);
                break;
            case "EMAIL_CHANGE":
                person.setEmail(event.getValue().asText());
                personDAO.save(person);
                break;
            case "PHONE_CHANGE":
                person.setPhone(event.getValue().asText());
                personDAO.save(person);
                break;

        }
    }
}

