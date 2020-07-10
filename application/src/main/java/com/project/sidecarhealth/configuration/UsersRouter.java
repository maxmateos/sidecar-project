package com.project.sidecarhealth.configuration;//package com.project.sidecarhealth.configuration;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import com.project.sidecarhealth.controller.UsersController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class UsersRouter {

    @Bean
    public RouterFunction<ServerResponse> userRouter(UsersController usersController) {

        return route(POST("/v1/users").and(accept(MediaType.APPLICATION_JSON)), usersController::createUser)
                .andRoute(GET("/v1/users").and(accept(MediaType.APPLICATION_JSON)), usersController::getAllUsers);
    }
}
