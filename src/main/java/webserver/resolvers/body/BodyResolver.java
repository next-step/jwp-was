package webserver.resolvers.body;


import webserver.http.HttpRequest;

public interface BodyResolver {

    boolean isMatchedContentType(String contentType);

    HttpRequest resolve(HttpRequest httpRequest);
}
