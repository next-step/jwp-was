package webserver.http;

import webserver.Ordered;

public interface HttpHandler extends Ordered {

    boolean supports(HttpRequest httpRequest);

    void handle(HttpRequest httpRequest, HttpResponse httpResponse);

}
