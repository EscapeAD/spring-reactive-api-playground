package com.itsadamtse.reactivemongo.router;

import com.itsadamtse.reactivemongo.handler.ExampleHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class exampleRouterConfig {

    @Bean
    public RouterFunction<ServerResponse> route(ExampleHandler handler){
        return RouterFunctions.route(GET("/func/flux")
        .and(accept(MediaType.APPLICATION_JSON)), handler::flux)
                .andRoute(GET("/func/mono")
                        .and(accept(MediaType.APPLICATION_JSON)), handler::mono);
    }
}
