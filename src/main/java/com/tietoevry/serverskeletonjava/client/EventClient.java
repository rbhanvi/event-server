package com.tietoevry.serverskeletonjava.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tietoevry.serverskeletonjava.dto.EventDTO;
import com.tietoevry.serverskeletonjava.service.EventService;
import com.tietoevry.serverskeletonjava.service.PersonService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
@Slf4j
public class EventClient {

    @Autowired
    TokenClient tokenClient;

    @Autowired
    private PersonService personService;

    @Autowired
    private EventService eventService;


    WebClient webClient = WebClient.builder().build();

    public Flux<EventDTO> fetchAndStoreEvents(String url, boolean specificEvent) throws Exception {
        Flux<EventDTO> event = getEvents(url, specificEvent);
        processEvents(event);
        return event;
    }

    private Flux<EventDTO> getEvents(String url, boolean specificEvent) throws JsonProcessingException {

        String token = tokenClient.getToken();

        MediaType accept = MediaType.TEXT_EVENT_STREAM;
        if(specificEvent){
            accept = MediaType.APPLICATION_JSON;
        }

        return webClient.get()
                .uri(url)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .accept(accept)
                .retrieve()
                .bodyToFlux(EventDTO.class);
    }

    @SneakyThrows
    public void processEvents(Flux<EventDTO> eventDTOFlux) {
        eventDTOFlux.subscribe(event -> {

            try {
                personService.updatePerson(event);
                log.info("Person processed successfully!");
            } catch (Exception e) {
                log.info("Some error occurred while updating person data in DB - {}", e);
                throw new RuntimeException(e);
            }

            try {
                eventService.saveEvent(







                        event);
            } catch (Exception e) {
                log.info("Some error occurred while updating event data in DB - {}", e);
                throw new RuntimeException(e);
            }
        });
    }





}
