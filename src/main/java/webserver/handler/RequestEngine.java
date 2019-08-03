package webserver.handler;

import request.RequestHeader;
import response.Response;
import webserver.Controller;

import java.util.Arrays;
import java.util.List;

/**
 * Created by youngjae.havi on 2019-08-03
 */
public class RequestEngine {
    private List<RequestStrategy> requestStrategies = Arrays.asList(new RequestMappingHandler(new Controller()), new StaticMappingHandler());

    public Response run(RequestHeader requestHeader) {
        return requestStrategies.stream()
                .filter(requestStrategy -> requestStrategy.isSupport(requestHeader))
                .findAny()
                .orElseThrow(() -> new RuntimeException("It's not supported request."))
                .request(requestHeader);
    }
}
