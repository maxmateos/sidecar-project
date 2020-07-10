//package com.project.sidecarhealth.configuration;
//
//import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
//import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
//import static org.springframework.web.reactive.function.server.RouterFunctions.route;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.MediaType;
//import org.springframework.web.reactive.function.server.RouterFunction;
//import org.springframework.web.reactive.function.server.ServerResponse;
//
//@Configuration
//public class BlogsRouter {
//
//    @Bean
//    public RouterFunction<ServerResponse> numberOfPanelistsRouter(BlogsController numberOfPanelistsController) {
//
//        return route(
//                POST("/v1/{userId}/blogs").and(accept(MediaType.APPLICATION_JSON)),
//                numberOfPanelistsController::numberOfPanelists);
//    }
//}
