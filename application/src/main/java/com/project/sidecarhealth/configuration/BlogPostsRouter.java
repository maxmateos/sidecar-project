package com.project.sidecarhealth.configuration; // package com.project.sidecarhealth.configuration;

import static com.project.sidecarhealth.constant.ParamNameConstants.POST_ID;
import static com.project.sidecarhealth.constant.ParamNameConstants.USER_ID;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import com.project.sidecarhealth.controller.PostsController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class BlogPostsRouter {

  private static final String POSTS_ENDPOINT = "/v1/posts";
  private static final String USER_POSTS_ENDPOINT = "/v1/users/{" + USER_ID + "}/posts";
  private static final String POSTS_BY_ID_ENDPOINT = USER_POSTS_ENDPOINT + "/{" + POST_ID + "}";

  @Bean
  public RouterFunction<ServerResponse> postRouter(PostsController postsController) {

    return route(
            POST(USER_POSTS_ENDPOINT).and(accept(MediaType.APPLICATION_JSON)),
            postsController::createPost)
        .andRoute(
            GET(POSTS_ENDPOINT).and(accept(MediaType.APPLICATION_JSON)),
            postsController::getAllPosts)
        .andRoute(
            GET(USER_POSTS_ENDPOINT).and(accept(MediaType.APPLICATION_JSON)),
            postsController::getUserPosts)
        .andRoute(
            GET(POSTS_BY_ID_ENDPOINT).and(accept(MediaType.APPLICATION_JSON)),
            postsController::getPostByIdAndUserId)
        .andRoute(
            PATCH(POSTS_BY_ID_ENDPOINT).and(accept(MediaType.APPLICATION_JSON)),
            postsController::updatePost)
        .andRoute(
            DELETE(POSTS_BY_ID_ENDPOINT).and(accept(MediaType.APPLICATION_JSON)),
            postsController::deletePost);
  }
}
