package webserver.mapper;

import exceptions.MappingNotFoundException;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.util.Arrays;
import java.util.List;

public class RequestMappers {

    List<RequestMapper> requestMappers;

    private RequestMappers(List<RequestMapper> requestMappers) {
        this.requestMappers = requestMappers;
    }

    public static RequestMappers of(RequestMapper... requestMappers) {
        return new RequestMappers(Arrays.asList(requestMappers));
    }

    public void matchHandle(HttpRequest httpRequest, HttpResponse httpResponse) {
        getMatchedRequestMapper(httpRequest).handle(httpRequest, httpResponse);
    }

    private RequestMapper getMatchedRequestMapper(HttpRequest httpRequest){
        return this.requestMappers.stream()
                .filter(mapper -> mapper.isMatchedRequest(httpRequest.getMethod(), httpRequest.getRequestURI()))
                .findFirst()
                .orElseThrow(() -> new MappingNotFoundException("Mapping된 RequestMapper를 찾을수 없습니다."));
    }
}
