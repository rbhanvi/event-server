package com.tietoevry.serverskeletonjava.controller;

import com.tietoevry.serverskeletonjava.dto.PersonEventsDTO;
import com.tietoevry.serverskeletonjava.model.Person;
import com.tietoevry.serverskeletonjava.service.EventService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private EventService eventService;


    @GetMapping("/{socSecNum}")
    public ResponseEntity<Person> getPerson(HttpServletRequest request, @PathVariable String socSecNum) {

        String token = request.getHeader("Authorization");
        if (token == null || !token.equals("Dummy.Token.For.Your.Convenience")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Person person = eventService.getPerson(socSecNum);
        if (person == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
       return ResponseEntity.ok(person);
    }

    @GetMapping("/{socSecNum}/events")
    public ResponseEntity<PersonEventsDTO> getPersonEvents(@PathVariable String socSecNum) {
        PersonEventsDTO personEventsDTO = eventService.getPersonWithEvents(socSecNum);

        if (personEventsDTO != null) {
            return ResponseEntity.ok(personEventsDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}


