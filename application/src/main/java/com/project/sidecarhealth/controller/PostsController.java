package com.project.sidecarhealth.controller;

import static com.project.sidecarhealth.constant.ParamNameConstants.POST_ID;
import static com.project.sidecarhealth.constant.ParamNameConstants.USER_ID;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import com.project.sidecarhealth.dto.PostRequestDTO;
import com.project.sidecarhealth.entity.BlogPost;
import com.project.sidecarhealth.service.PostService;
import com.project.sidecarhealth.transformer.PostTransformers;
import com.project.sidecarhealth.validator.implementation.CreatePostRequestValidator;
import com.project.sidecarhealth.validator.implementation.UpdatePostRequestValidator;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class PostsController implements BaseController {

  private CreatePostRequestValidator createPostRequestValidator;
  private UpdatePostRequestValidator updatePostRequestValidator;
  private PostTransformers postTransformers;
  private PostService postService;

  @Autowired
  public PostsController(
      CreatePostRequestValidator createPostRequestValidator,
      UpdatePostRequestValidator updatePostRequestValidator,
      PostTransformers postTransformers,
      PostService postService) {
    this.createPostRequestValidator = createPostRequestValidator;
    this.updatePostRequestValidator = updatePostRequestValidator;
    this.postTransformers = postTransformers;
    this.postService = postService;
  }

  public Mono<ServerResponse> createPost(ServerRequest request) {

    logger.info(REQUEST_PLACEHOLDER, request);

    final Long userId = getIdFromPath(request, USER_ID);
    final Mono<PostRequestDTO> postRequestDTOMono = request.bodyToMono(PostRequestDTO.class);

    return postRequestDTOMono
        .defaultIfEmpty(new PostRequestDTO())
        .doOnNext(body -> validator(createPostRequestValidator, body))
        .map(body -> postTransformers.transform(userId, body))
        .map(postService::createPost)
        .map(postTransformers::transform)
        .flatMap(
            body ->
                ServerResponse.created(URI.create("/v1/users/" + userId + "/posts/" + body.getId()))
                    .contentType(APPLICATION_JSON)
                    .bodyValue(body));
  }

  public Mono<ServerResponse> getAllPosts(ServerRequest request) {

    logger.info(REQUEST_PLACEHOLDER, request);

    final List<BlogPost> postList = postService.getAllPosts();

    return ServerResponse.ok()
        .contentType(APPLICATION_JSON)
        .bodyValue(postTransformers.transform(postList));
  }

  public Mono<ServerResponse> getUserPosts(ServerRequest request) {

    logger.info(REQUEST_PLACEHOLDER, request);

    final Long userId = getIdFromPath(request, USER_ID);
    final List<BlogPost> postList = postService.getUserPosts(userId);

    return ServerResponse.ok()
        .contentType(APPLICATION_JSON)
        .bodyValue(postTransformers.transform(postList));
  }

  public Mono<ServerResponse> getPostByIdAndUserId(ServerRequest request) {

    logger.info(REQUEST_PLACEHOLDER, request);

    final Long userId = getIdFromPath(request, USER_ID);
    final Long postId = getIdFromPath(request, POST_ID);
    final BlogPost post = postService.getPostByIdAndUserId(postId, userId);

    return ServerResponse.ok()
        .contentType(APPLICATION_JSON)
        .bodyValue(postTransformers.transform(post));
  }

  public Mono<ServerResponse> updatePost(ServerRequest request) {

    logger.info(REQUEST_PLACEHOLDER, request);

    final Long userId = getIdFromPath(request, USER_ID);
    final Long postId = getIdFromPath(request, POST_ID);
    final Mono<PostRequestDTO> userRequestDTOMono = request.bodyToMono(PostRequestDTO.class);

    return userRequestDTOMono
        .defaultIfEmpty(new PostRequestDTO())
        .doOnNext(body -> validator(updatePostRequestValidator, body))
        .map(body -> postTransformers.transform(userId, body))
        .map(body -> postService.updatePost(postId, userId, body))
        .map(postTransformers::transform)
        .flatMap(body -> ServerResponse.ok().contentType(APPLICATION_JSON).bodyValue(body));
  }

  public Mono<ServerResponse> deletePost(ServerRequest request) {

    logger.info(REQUEST_PLACEHOLDER, request);

    final Long userId = getIdFromPath(request, USER_ID);
    final Long postId = getIdFromPath(request, POST_ID);
    postService.deletePost(postId, userId);

    return ServerResponse.noContent().build();
  }
}
