package com.tietoevry.serverskeletonjava.dao;

import com.tietoevry.serverskeletonjava.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonDAO extends JpaRepository<Person, String> {

    Optional<Person> findBySocSecNumAndDeletedFalse(String socSecNum);
}
