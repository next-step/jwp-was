package webserver.mapper;

import enums.HttpMethod;
import webserver.handler.ActionHandler;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ActionRequestMapper implements RequestMapper {


    private final String mappedRequestUri;
    private final ActionHandler actionHandler;
    private final Set<HttpMethod> httpMethods;


    public ActionRequestMapper(String mappedRequestUri, ActionHandler actionHandler) {
        this(mappedRequestUri, actionHandler, new HashSet<>(Arrays.asList(HttpMethod.values())));
    }

    public ActionRequestMapper(String mappedRequestUri, ActionHandler actionHandler, Set<HttpMethod> httpMethods) {
        this.mappedRequestUri = mappedRequestUri;
        this.actionHandler = actionHandler;
        this.httpMethods = httpMethods;
    }

    @Override
    public boolean isMatchedRequest(HttpMethod method, String requestUri) {
        return this.httpMethods.contains(method) && mappedRequestUri.equalsIgnoreCase(requestUri);
    }

    @Override
    public Object handle(HttpRequest httpRequest, HttpResponse httpResponse) {
        return actionHandler.actionHandle(httpRequest, httpResponse);
    }
}
