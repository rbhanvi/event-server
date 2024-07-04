package com.tietoevry.serverskeletonjava.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Person {

    private String name;

    @Id
    private String socSecNum;
    private String address;
    private String email;
    private String phone;

    @JsonIgnore
    private boolean deleted;
}
