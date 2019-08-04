package handler;

import request.HttpRequest;
import response.HttpResponse;
import controller.Controller;

import java.util.Arrays;
import java.util.List;

/**
 * Created by youngjae.havi on 2019-08-03
 */
public class RequestEngine {
    private List<RequestStrategy> requestStrategies = Arrays.asList(new RequestMappingHandler(new Controller()), new StaticMappingHandler());

    public HttpResponse run(HttpRequest httpRequest) {
        return requestStrategies.stream()
                .filter(requestStrategy -> requestStrategy.isSupport(httpRequest))
                .findAny()
                .orElseThrow(() -> new RuntimeException("It's not supported request."))
                .request(httpRequest);
    }
}
