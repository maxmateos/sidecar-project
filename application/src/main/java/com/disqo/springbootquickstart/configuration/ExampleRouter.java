package com.disqo.springbootquickstart.configuration;

import com.disqo.springbootquickstart.controller.ExampleController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ExampleRouter {

    @Bean
    public RouterFunction<ServerResponse> exampleRouters(ExampleController exampleController) {

        return route(GET("/v1/example1/{param1}/example2/{param2}")
          .and(accept(MediaType.APPLICATION_JSON)), exampleController::getExampleEndpoint);
    }
}
