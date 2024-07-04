package com.tietoevry.serverskeletonjava.controller;

import com.tietoevry.serverskeletonjava.client.EventClient;
import com.tietoevry.serverskeletonjava.dto.EventDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/events")
public class EventController {


    @Autowired
    private EventClient eventClient;

    @GetMapping("/")
    public Flux<EventDTO> simulateEvents() throws Exception {
        String url = "http://localhost:8080/events";
        return eventClient.fetchAndStoreEvents(url, false);
    }

    @GetMapping("/from/{eventId}")
    public Flux<EventDTO> simulateEventsFromId(@PathVariable String eventId) throws Exception {
        String url = "http://localhost:8080/events/from/" + eventId;
        return eventClient.fetchAndStoreEvents(url, false);
    }

    @GetMapping("/{eventId}")
    public Flux<EventDTO> simulateSpecificEvent(@PathVariable String eventId) throws Exception {
        String url = "http://localhost:8080/events/"+ eventId;
        return eventClient.fetchAndStoreEvents(url, true);
    }
}
