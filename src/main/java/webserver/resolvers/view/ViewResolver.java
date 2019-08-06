package webserver.resolvers.view;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public interface ViewResolver {
    void resolve(HttpRequest httpRequest, HttpResponse httpResponse) ;
}
