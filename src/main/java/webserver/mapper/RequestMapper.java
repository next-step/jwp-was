package webserver.mapper;

import enums.HttpMethod;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public interface RequestMapper {

    boolean isMatchedRequest(HttpMethod method, String path);

    void handle(HttpRequest httpRequest, HttpResponse httpResponse);
}
