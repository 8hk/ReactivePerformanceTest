package com.hko.reactiveperformancetest;

import com.hko.reactiveperformancetest.client.PersonWebClient;
import com.hko.reactiveperformancetest.model.Person;
import com.hko.reactiveperformancetest.reader.FileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class ReactiveperformancetestApplication {
    private static Logger logger = LoggerFactory.getLogger(ReactiveperformancetestApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(ReactiveperformancetestApplication.class, args);

        try {
            logger.info("Operation started");
            FileReader reader = new FileReader("/home/hko/Documents/repo/reactiveperformancetest/text.txt");
            reader.run();
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.toString());
        }
        logger.info("Operation finished");
//        PersonWebClient client = new PersonWebClient();
//        Person dummyPerson= new Person("hasawn","keremww");
//        Mono<String> response =client.createPerson(dummyPerson);

    }

}
