package com.project.sidecarhealth.configuration;

import static com.project.sidecarhealth.constant.ParamNameConstants.USER_ID;
import static com.project.sidecarhealth.constant.ParamNameConstants.USER_LAST_NAME;
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

  private static final String USERS_ENDPOINT = "/v1/users";
  private static final String USER_BY_ID_ENDPOINT = USERS_ENDPOINT + "/{" + USER_ID + "}";
  private static final String USER_BY_LASTNAME_ENDPOINT =
      USERS_ENDPOINT + "/lastname/{" + USER_LAST_NAME + "}";

  @Bean
  public RouterFunction<ServerResponse> userRouter(UsersController usersController) {

    return route(
            POST(USERS_ENDPOINT).and(accept(MediaType.APPLICATION_JSON)),
            usersController::createUser)
        .andRoute(
            GET(USERS_ENDPOINT).and(accept(MediaType.APPLICATION_JSON)),
            usersController::getAllUsers)
        .andRoute(
            GET(USER_BY_ID_ENDPOINT).and(accept(MediaType.APPLICATION_JSON)),
            usersController::getUserById)
        .andRoute(
            GET(USER_BY_LASTNAME_ENDPOINT).and(accept(MediaType.APPLICATION_JSON)),
            usersController::getUserByLastname)
        .andRoute(
            PATCH(USER_BY_ID_ENDPOINT).and(accept(MediaType.APPLICATION_JSON)),
            usersController::updateUser)
        .andRoute(
            DELETE(USER_BY_ID_ENDPOINT).and(accept(MediaType.APPLICATION_JSON)),
            usersController::deleteUser);
  }
}
