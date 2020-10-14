package com.hko.reactiveperformancetest.controller;

import com.hko.reactiveperformancetest.model.Person;
import com.hko.reactiveperformancetest.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@RestController
@RequestMapping(value = "/api/person")
public class PersonController {
    @Autowired
    PersonRepository personRepository;

    @GetMapping("")
    public Flux<Person> getPerson() {

        return personRepository.findAll();

    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Person> createPerson(@RequestBody Person person) {

        return personRepository.save(person);
    }

    @PutMapping("")
    public Mono<Person> updatePerson(@RequestBody Person person) {

        return personRepository.save(person);

    }

    @DeleteMapping("")
    public boolean deletePerson(@RequestBody Person person) {

        try {
            personRepository.deleteById(person.getId()).block(); // Note this!
            return true;

        } catch (Exception e) {

            return false;
        }
    }

}
