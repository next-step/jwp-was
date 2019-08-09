package webserver.mapper;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.exceptions.MappingNotFoundException;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class RequestMappers {

    private static final Logger logger = LoggerFactory.getLogger(RequestMappers.class);

    List<RequestMapper> requestMappers;

    private RequestMappers(List<RequestMapper> requestMappers) {
        this.requestMappers = requestMappers;
    }

    public static RequestMappers of(RequestMapper... requestMappers) {
        return new RequestMappers(Arrays.asList(requestMappers));
    }

    public void matchHandle(HttpRequest httpRequest, HttpResponse httpResponse) throws MappingNotFoundException {
        RequestMapper matchedRequestMapper = getMatchedRequestMapper(httpRequest);

        logger.debug("matchedRequestMapper : {}", matchedRequestMapper.getClass());
        matchedRequestMapper.handle(httpRequest, httpResponse);
    }

    private RequestMapper getMatchedRequestMapper(HttpRequest httpRequest) throws MappingNotFoundException {
        return this.requestMappers.stream()
                .filter(mapper -> mapper.isMatchedRequest(httpRequest.getMethod(), httpRequest.getRequestURI()))
                .findFirst()
                .orElseThrow(() -> new MappingNotFoundException("Mapping된 RequestMapper를 찾을수 없습니다."));
    }
}
