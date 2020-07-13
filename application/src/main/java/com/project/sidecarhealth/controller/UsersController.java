package com.project.sidecarhealth.controller;

import static com.project.sidecarhealth.constant.ParamNameConstants.USER_ID;
import static com.project.sidecarhealth.constant.ParamNameConstants.USER_LAST_NAME;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import com.project.sidecarhealth.dto.UserRequestDTO;
import com.project.sidecarhealth.entity.User;
import com.project.sidecarhealth.service.UserService;
import com.project.sidecarhealth.transformer.UserTransformers;
import com.project.sidecarhealth.validator.implementation.CreateUserRequestValidator;
import com.project.sidecarhealth.validator.implementation.UpdateUserRequestValidator;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class UsersController implements BaseController {

  private CreateUserRequestValidator createUserRequestValidator;
  private UpdateUserRequestValidator updateUserRequestValidator;
  private UserTransformers userTransformers;
  private UserService userService;

  @Autowired
  public UsersController(
      CreateUserRequestValidator createUserRequestValidator,
      UpdateUserRequestValidator updateUserRequestValidator,
      UserTransformers userTransformers,
      UserService userService) {
    this.createUserRequestValidator = createUserRequestValidator;
    this.updateUserRequestValidator = updateUserRequestValidator;
    this.userTransformers = userTransformers;
    this.userService = userService;
  }

  public Mono<ServerResponse> createUser(ServerRequest request) {

    logger.info(REQUEST_PLACEHOLDER, request);

    final Mono<UserRequestDTO> userRequestDTOMono = request.bodyToMono(UserRequestDTO.class);

    return userRequestDTOMono
        .defaultIfEmpty(new UserRequestDTO())
        .doOnNext(body -> validator(createUserRequestValidator, body))
        .map(userTransformers::transform)
        .map(userService::createUser)
        .map(userTransformers::transform)
        .flatMap(
            body ->
                ServerResponse.created(URI.create("/v1/users/" + body.getId()))
                    .contentType(APPLICATION_JSON)
                    .bodyValue(body));
  }

  public Mono<ServerResponse> getAllUsers(ServerRequest request) {

    logger.info(REQUEST_PLACEHOLDER, request);

    final List<User> userList = userService.getUsers();

    return ServerResponse.ok()
        .contentType(APPLICATION_JSON)
        .bodyValue(userTransformers.transform(userList));
  }

  public Mono<ServerResponse> getUserById(ServerRequest request) {

    logger.info(REQUEST_PLACEHOLDER, request);

    final Long userId = getIdFromPath(request, USER_ID);
    final User user = userService.getUserById(userId);

    return ServerResponse.ok()
        .contentType(APPLICATION_JSON)
        .bodyValue(userTransformers.transform(user));
  }

  public Mono<ServerResponse> getUserByLastname(ServerRequest request) {

    logger.info(REQUEST_PLACEHOLDER, request);

    final String lastname = request.pathVariable(USER_LAST_NAME);
    final List<User> userList = userService.findByLastname(lastname);

    return ServerResponse.ok()
        .contentType(APPLICATION_JSON)
        .bodyValue(userTransformers.transform(userList));
  }

  public Mono<ServerResponse> updateUser(ServerRequest request) {

    logger.info(REQUEST_PLACEHOLDER, request);

    final Long userId = getIdFromPath(request, USER_ID);
    final Mono<UserRequestDTO> userRequestDTOMono = request.bodyToMono(UserRequestDTO.class);

    return userRequestDTOMono
        .defaultIfEmpty(new UserRequestDTO())
        .doOnNext(body -> validator(updateUserRequestValidator, body))
        .map(userTransformers::transform)
        .map(body -> userService.updateUser(userId, body))
        .map(userTransformers::transform)
        .flatMap(body -> ServerResponse.ok().contentType(APPLICATION_JSON).bodyValue(body));
  }

  public Mono<ServerResponse> deleteUser(ServerRequest request) {

    logger.info(REQUEST_PLACEHOLDER, request);

    final Long userId = getIdFromPath(request, USER_ID);
    userService.deleteUser(userId);

    return ServerResponse.noContent().build();
  }
}
