package com.tietoevry.serverskeletonjava.dao;

import com.tietoevry.serverskeletonjava.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventDAO extends JpaRepository<Event, Long> {

    List<Event> findBySequenceNumberGreaterThanEqual(Long sequenceNumber);

    Event findBySequenceNumber(Long sequenceNumber);

    List<Event> findAll();

    List<Event> findBySocSecNum(String socSecNum);
}

