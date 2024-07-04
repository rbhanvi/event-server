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

        if(null != person){
            log.info("Person details - {}, Person name - {}", person.getSocSecNum(), person.getName());
        }
        switch (event.getEventType()) {
            case "PERSON_CREATED": //TODO- person should be null here and all other cases should be not null add check.
                ObjectMapper mapper = new ObjectMapper();
                Person newPerson = parsePerson(event.getValue());
                newPerson.setSocSecNum(event.getSocSecNum());
                personDAO.save(newPerson);
                break;
            case "SOCSECNUM_CHANGE":
                person.setDeleted(true); // Assuming you have a 'deleted' flag

                // Create a new Person entity with updated socSecNum
                Person updatePerson = new Person();
                updatePerson.setName(person.getName()); // Copy other attributes as needed
                updatePerson.setAddress(person.getAddress());
                updatePerson.setEmail(person.getEmail());
                updatePerson.setPhone(person.getPhone());

                // Save the new person
                updatePerson.setSocSecNum(event.getValue().asText());
                personDAO.save(updatePerson);
//                personDAO.save(person);
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

    public Person findPersonBySocSecNum(String socSecNum) {
        return personDAO.findById(socSecNum).orElse(null);
    }

    public Person getPersonBySocSecNum(String socSecNum) {
        Optional<Person> personOptional = personDAO.findBySocSecNumAndDeletedFalse(socSecNum);
        Person person = new Person();
        if (personOptional.isPresent()) {
            person = personOptional.get();
        }

        return person;
    }
}

