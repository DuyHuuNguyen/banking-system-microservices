package com.banking_app.auth_service.infrastructure.rest.interceptor;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@Log4j2
public class ExampleHandlerFilterFunction
        implements HandlerFilterFunction<ServerResponse, ServerResponse> {

    @Override
    public Mono<ServerResponse> filter(ServerRequest serverRequest,
                                       HandlerFunction<ServerResponse> handlerFunction) {
//        if (serverRequest.pathVariable("name").equalsIgnoreCase("test")) {
//            return ServerResponse.status(FORBIDDEN).build();
//        }
        log.info(" Nhu ccc");
        return handlerFunction.handle(serverRequest);
    }
}