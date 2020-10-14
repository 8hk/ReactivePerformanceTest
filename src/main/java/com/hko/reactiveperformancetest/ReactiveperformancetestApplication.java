package com.hko.reactiveperformancetest;

import com.hko.reactiveperformancetest.client.PersonWebClient;
import com.hko.reactiveperformancetest.model.Person;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class ReactiveperformancetestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveperformancetestApplication.class, args);

        PersonWebClient client = new PersonWebClient();
        Person dummyPerson= new Person("hasawn","keremww");
        Mono<String> response =client.createPerson(dummyPerson);
    }

}
