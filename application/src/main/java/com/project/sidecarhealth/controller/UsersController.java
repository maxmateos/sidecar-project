package com.project.sidecarhealth.controller;

import com.project.sidecarhealth.dto.CreateUserRequestDTO;
import com.project.sidecarhealth.service.UserService;
import com.project.sidecarhealth.transformer.UserTransformers;
import com.project.sidecarhealth.validator.CreateUserRequestValidator;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

@Component
public class UsersController implements BaseController {

  private static final Logger logger = org.slf4j.LoggerFactory.getLogger(UsersController.class);
  private static final String REQUEST_PLACEHOLDER = "REQUEST: '{}'";

  private CreateUserRequestValidator createUsersRequestValidator;
  private UserTransformers userTransformers;
  private UserService userService;

  @Autowired
  public UsersController(CreateUserRequestValidator createUserRequestValidator, UserTransformers userTransformers, UserService userService) {
    this.createUsersRequestValidator = createUserRequestValidator;
    this.userTransformers = userTransformers;
    this.userService = userService;
  }

  public Mono<ServerResponse> createUser(ServerRequest request) {

    logger.info(REQUEST_PLACEHOLDER, request);

    final Mono<CreateUserRequestDTO> createUserRequestDTOMono = request.bodyToMono(CreateUserRequestDTO.class);

    return createUserRequestDTOMono
        .defaultIfEmpty(new CreateUserRequestDTO())
        .doOnNext(body -> validator(createUsersRequestValidator, body))
        .map(body -> userTransformers.transform(body))
        .map(userService::createUser)
        .map(userTransformers::transform)
        .flatMap(userResponseDTO -> ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .bodyValue(userResponseDTO));
  }

  public Mono<ServerResponse> getAllUsers(ServerRequest request) {

    logger.info(REQUEST_PLACEHOLDER, request);

    return ServerResponse.ok()
            .contentType(APPLICATION_JSON)
            .bodyValue(userTransformers.transform(userService.getUsers()));
  }
}
