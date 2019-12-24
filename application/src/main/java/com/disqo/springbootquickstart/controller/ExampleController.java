package com.disqo.springbootquickstart.controller;

import com.disqo.springbootquickstart.constant.Constants;
import com.disqo.springbootquickstart.service.ExampleService;
import com.disqo.springbootquickstart.transformer.ExampleTransformer;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Component
public class ExampleController {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ExampleController.class);

    private ExampleService exampleService;
    private ExampleTransformer exampleTransformer;

    @Autowired
    public ExampleController(ExampleService exampleService, ExampleTransformer exampleTransformer) {
        this.exampleService = exampleService;
        this.exampleTransformer = exampleTransformer;
    }

    public Mono<ServerResponse> getExampleEndpoint(ServerRequest request) {
        logger.info("REQUEST: '{}'", request);

        final String rawAppId = request.pathVariable(Constants.PARAM_1_KEY);
        final Long param1 = exampleService.castParam1ToValidLong(rawAppId);
        final String param2 = request.pathVariable(Constants.PARAM_2_KEY);

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromValue(
                  exampleTransformer.transform(exampleService.getExampleData(param1, param2))));
    }

}
