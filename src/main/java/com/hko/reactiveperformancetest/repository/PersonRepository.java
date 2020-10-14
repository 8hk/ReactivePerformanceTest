package com.hko.reactiveperformancetest.repository;

import com.hko.reactiveperformancetest.model.Person;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface  PersonRepository extends ReactiveCrudRepository <Person,Long> {
}
