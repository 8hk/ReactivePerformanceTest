package com.hko.reactiveperformancetest.client;

import com.hko.reactiveperformancetest.model.Person;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import org.slf4j.*;
public class PersonWebClient {

    private static Logger logger = LoggerFactory.getLogger(PersonWebClient.class);


    WebClient client;

//    private static ExchangeFilterFunction logRequest() {
//        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
//            logger.info("Request: {} {}", clientRequest.method(), clientRequest.url());
//            clientRequest.headers().forEach((name, values) -> values.forEach(value -> logger.info("{}={}", name, value)));
//            return Mono.just(clientRequest);
//        });
//    }

    public PersonWebClient(){
        HttpClient httpClient = HttpClient.create()
                .tcpConfiguration(client ->
                        client.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                                .doOnConnected(conn -> conn
                                        .addHandlerLast(new ReadTimeoutHandler(10))
                                        .addHandlerLast(new WriteTimeoutHandler(10))));

        ClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);

        client = WebClient.builder()
                .baseUrl("http://localhost:8080")
                .clientConnector(connector)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .filter(logRequest())
//                .filter()
                .build();
    }


    public Mono<String> createPerson(Person person){
        Mono<String> result = client.post()
                .uri("/api/person")
                .header("Content-Type","application/json;charset=UTF-8")
                .body(Mono.just(person),Person.class)
                    .accept( MediaType.ALL )
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, this::get4xxError)
                .onStatus(HttpStatus::is5xxServerError, this::get5xxError)
                .bodyToMono(String.class);

        String response= result.block();
        logger.info("Response: {}", response);
        return result;
    }

    private <T> Mono<T> get4xxError(ClientResponse clientResponse) {

        return Mono.error(new Throwable("Client Error"));
    }

    private <T> Mono<T> get5xxError(ClientResponse clientResponse) {

        return Mono.error(new Throwable("Server Error"));
    }

//    public void send(Person person){
//        ClientResponse clientResponse =client.post()
//                .uri("/api/person")
//                .accept(MediaType.ALL)
//                .header("Content-Type", "application/json;charset=UTF-8")
//                .body(Mono.just(person),Person.class)
//                .exchange()
//                .block();
//        logger.info("Response: {}", clientResponse.toEntity(String.class).block());
//    }



}
